package com.bunjne.bbit.braodcaster

import com.bunjne.bbit.util.getReachabilityFlags
import io.github.aakira.napier.Napier
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.alignOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.SystemConfiguration.SCNetworkReachabilityCallBack
import platform.SystemConfiguration.SCNetworkReachabilityContext
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithAddress
import platform.SystemConfiguration.SCNetworkReachabilityFlags
import platform.SystemConfiguration.SCNetworkReachabilityRef
import platform.SystemConfiguration.SCNetworkReachabilitySetCallback
import platform.SystemConfiguration.SCNetworkReachabilitySetDispatchQueue
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsConnectionAutomatic
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsConnectionOnDemand
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsConnectionOnTraffic
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsConnectionRequired
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsInterventionRequired
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsIsDirect
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsIsLocalAddress
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsIsWWAN
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsReachable
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsTransientConnection
import platform.darwin.dispatch_queue_attr_make_with_qos_class
import platform.darwin.dispatch_queue_create
import platform.posix.AF_INET
import platform.posix.QOS_CLASS_DEFAULT
import platform.posix.sockaddr_in

@OptIn(ExperimentalForeignApi::class)
class IOSNetworkManager : NetworkManager {

    private val _networkStatusFlow: MutableSharedFlow<Boolean> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val networkStatusFlow: SharedFlow<Boolean> = _networkStatusFlow.asSharedFlow()

    private val sizeSockaddr = sizeOf<sockaddr_in>()
    private val alignSockaddr = alignOf<sockaddr_in>()
    private val zeroAddress =
        nativeHeap.alloc(sizeSockaddr, alignSockaddr).reinterpret<sockaddr_in>().apply {
            sin_len = sizeOf<sockaddr_in>().toUByte()
            sin_family = AF_INET.convert()
        }

    private val reachabilityRef: SCNetworkReachabilityRef =
        SCNetworkReachabilityCreateWithAddress(null, zeroAddress.ptr.reinterpret())
            ?: throw IllegalStateException()

    private val reachabilityFlags = getReachabilityFlags(reachabilityRef)

    override suspend fun isNetworkAvailable(): Boolean = hasNetworkConnection()

    override fun start() {
        val dispatchQueueAttr = dispatch_queue_attr_make_with_qos_class(null, QOS_CLASS_DEFAULT, 0)

        val reachabilitySerialQueue =
            dispatch_queue_create("com.bunjne.bbit.braodcaster", dispatchQueueAttr)

        val notificationObserver = NSNotificationCenter.defaultCenter.addObserverForName(
            name = "ReachabilityChangedNotification",
            `object` = null,
            queue = NSOperationQueue.mainQueue,
            usingBlock = {
                _networkStatusFlow.tryEmit(hasNetworkConnection())
                Napier.d("Network connection is available: ${hasNetworkConnection()}")
            }
        )

        val selfPtr = StableRef.create("Connectivity Ref")

        val sizeSCNetReachCxt = sizeOf<SCNetworkReachabilityContext>()
        val alignSCNetReachCxt = alignOf<SCNetworkReachabilityContext>()
        val context = nativeHeap.alloc(sizeSCNetReachCxt, alignSCNetReachCxt)
            .reinterpret<SCNetworkReachabilityContext>().apply {
                version = 0
                info = selfPtr.asCPointer()
                retain = null
                release = null
                copyDescription = null
            }

        val callback: SCNetworkReachabilityCallBack =
            staticCFunction { _: SCNetworkReachabilityRef?, _: SCNetworkReachabilityFlags, info: COpaquePointer? ->
                if (info == null) {
                    return@staticCFunction
                }
                try {
                    NSNotificationCenter.defaultCenter.postNotificationName(
                        "ReachabilityChangedNotification",
                        null
                    )
                } catch (error: Throwable) {
                    Napier.e("SCNetworkReachabilityCallBack error: ${error.message}")
                }
            }

        if (!SCNetworkReachabilitySetCallback(reachabilityRef, callback, context.ptr)) {
            throw IllegalStateException("Failed on SCNetworkReachabilitySetCallback")
        }
        if (!SCNetworkReachabilitySetDispatchQueue(reachabilityRef, reachabilitySerialQueue)) {
            throw IllegalStateException("Failed on SCNetworkReachabilitySetDispatchQueue")
        }
    }

    override fun stop() {

    }

    private fun hasNetworkConnection(): Boolean {
        val flags = getReachabilityFlags()
        val isReachable = flags.contains(kSCNetworkReachabilityFlagsReachable)
        val needsConnection = flags.contains(kSCNetworkReachabilityFlagsConnectionRequired)

        return when {
            !isReachable || needsConnection -> false
            else -> true
        }
    }

    private fun getReachabilityFlags(): Array<SCNetworkReachabilityFlags> {
        reachabilityFlags ?: return emptyArray()

        val result = arrayOf(
            kSCNetworkReachabilityFlagsTransientConnection,
            kSCNetworkReachabilityFlagsReachable,
            kSCNetworkReachabilityFlagsConnectionRequired,
            kSCNetworkReachabilityFlagsConnectionOnTraffic,
            kSCNetworkReachabilityFlagsInterventionRequired,
            kSCNetworkReachabilityFlagsConnectionOnDemand,
            kSCNetworkReachabilityFlagsIsLocalAddress,
            kSCNetworkReachabilityFlagsIsDirect,
            kSCNetworkReachabilityFlagsIsWWAN,
            kSCNetworkReachabilityFlagsConnectionAutomatic
        ).filter {
            (reachabilityFlags and it) > 0u
        }
            .toTypedArray()
        result.forEach { Napier.d(it.toString() + "$kSCNetworkReachabilityFlagsTransientConnection")}
        Napier.d("SCNetworkReachabilityFlags: ${result.contentDeepToString()}")
        return result
    }
}