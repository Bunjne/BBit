package com.bunjne.bbit.braodcaster

import kotlinx.coroutines.flow.SharedFlow

interface Broadcaster<T> {

    suspend fun broadcast(value: T)

    fun observable(): SharedFlow<T>
}
