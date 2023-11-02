package com.bunjne.bbit.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @param containerRadius Size of view
 * @param circleRadius Size of circle rotated in a container. By default, circle radius is divided by 6
 * @param color: Color of loading indicator. Primary color used by default
* */
@Composable
fun ProgressLoader(
    containerRadius: Dp = 50.dp,
    circleRadius: Dp = containerRadius / 6,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val circleNum = 9

        MetaEntity(
            modifier = Modifier.fillMaxSize(),
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
                    modifier = Modifier.size(containerRadius),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(modifier = Modifier
                        .rotate(rotation)
                        .drawBehind {
                            drawArc(
                                color = color,
                                startAngle = 0f,
                                sweepAngle = 215f,
                                useCenter = false,
                                style = Stroke(
                                    width = circleRadius.toPx(),
                                    cap = StrokeCap.Round
                                )
                            )
                        }
                        .size(containerRadius - circleRadius)
                    )
                    for (i in 0..circleNum) {
                        Circle(
                            offset = i * (360f / circleNum),
                            containerRadius = containerRadius,
                            circleRadius = circleRadius,
                            color = color
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun Circle(
    offset: Float,
    containerRadius: Dp,
    circleRadius: Dp,
    color: Color
) {
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
            .width(containerRadius)
    ) {
        Box(
            modifier = Modifier
                .background(color, CircleShape)
                .size(circleRadius)
        )
    }
}

@Composable
fun MetaEntity(
    modifier: Modifier = Modifier,
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
            modifier = Modifier,
            content = metaContent,
        )
        content()
    }
}