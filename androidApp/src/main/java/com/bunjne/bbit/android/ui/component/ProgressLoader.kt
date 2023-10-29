package com.bunjne.bbit.android.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import com.bunjne.bbit.android.extension.customBlur
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ProgressLoader() {
    MetaContainer(
        color = Color(0xFF0951E2)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {

            val x = 9

            MetaEntity(
                modifier = Modifier.fillMaxSize(),
                blur = 40f,
                metaContent = {
                    val animation = rememberInfiniteTransition(label = "")
                    val rotation by animation.animateFloat(
                        initialValue = 0f,
                        targetValue = -360f,
                        animationSpec = infiniteRepeatable(
                            tween(8000, easing = LinearEasing),
                            RepeatMode.Restart,
                        ), label = ""
                    )

                    Box(
                        modifier = Modifier.size(300.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(modifier = Modifier
                            .rotate(rotation)
                            .drawBehind {
                                drawArc(
                                    color = Color.Black,
                                    startAngle = 0f,
                                    sweepAngle = 215f,
                                    useCenter = false,

                                    style = Stroke(
                                        width = size.width * .195f,
                                        cap = StrokeCap.Round
                                    )
                                )
                            }
                            .size(250.dp)
                        )
                        for (i in 0..x) {
                            Circle(i * (360f / x))
                        }
                    }
                }) {
                Text(text = "Balloon", fontSize = 20.sp)
            }

        }
    }
}

@Composable
fun Circle(offset: Float) {
    val animation = rememberInfiniteTransition(label = "")
    val rotation by animation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(8000, easing = LinearEasing),
            RepeatMode.Restart,
        ), label = ""
    )

    Box(
        modifier = Modifier
            .rotate(offset)
            .rotate(rotation)
            .width(300.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black, CircleShape)
                .size(50.dp)
        )
    }
}

@Composable
fun MetaEntity(
    modifier: Modifier = Modifier,
    blur: Float = 30f,
    metaContent: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit = {},
) {

    Box(
        modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .customBlur(blur),
            content = metaContent,
        )
        content()
    }

}