package com.bunjne.bbit.android.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.bunjne.bbit.android.data.CircleUIState
import com.bunjne.bbit.android.data.PlanetType
import com.bunjne.bbit.android.data.PlanetUIState
import com.example.compose.md_theme_dark_onPrimary
import com.example.compose.md_theme_dark_onSecondary
import com.example.compose.md_theme_dark_primary
import com.example.compose.md_theme_dark_secondary
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun Gesture() {
    val colorList = remember {
        mutableListOf(md_theme_dark_onPrimary, md_theme_dark_primary, md_theme_dark_secondary, md_theme_dark_onSecondary)
    }

    val planets = remember {
        mutableListOf(
            PlanetUIState(
                type = PlanetType.Mercury,
                colors = listOf(Color.Cyan, Color.White),
                rotationSpeedFactor = 1f,
                radius = 40f
            ),
            PlanetUIState(
                type = PlanetType.Venus,
                colors = listOf(Color.Cyan, Color.White),
                rotationSpeedFactor = 1.5f,
                radius = 80f
            ),
            PlanetUIState(
                type = PlanetType.Earth,
                colors = listOf(Color.Cyan, Color.White),
                rotationSpeedFactor = 2f,
                radius = 80f
            ),
            PlanetUIState(
                type = PlanetType.Jupiter,
                colors = listOf(Color.Cyan, Color.White),
                rotationSpeedFactor = 1.2f,
                radius = 200f
            ),
            PlanetUIState(
                type = PlanetType.Saturn,
                colors = listOf(Color.Cyan, Color.White),
                rotationSpeedFactor = 1.3f,
                radius = 160f
            ),
            PlanetUIState(
                type = PlanetType.Uranus,
                colors = listOf(Color.Cyan, Color.White),
                rotationSpeedFactor = 1.9f,
                radius = 120f
            ),
            PlanetUIState(
                type = PlanetType.Neptune,
                colors = listOf(Color.Cyan, Color.White),
                rotationSpeedFactor = 1.6f,
                radius = 120f
            )
        )
    }
    val selectedPlanets = remember {
        mutableStateListOf<PlanetUIState>()
    }

    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    val circleUIState = remember {
        mutableStateListOf<CircleUIState>()
    }

    val animation = rememberInfiniteTransition(label = "")
    val rotation by animation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(3000, easing = LinearEasing),
            RepeatMode.Restart,
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        // Detect a tap event and obtain its position.
                        awaitPointerEventScope {
                            val position = awaitFirstDown().position
                            launch {
                                planets
                                    .randomOrNull()
                                    ?.let {
                                        selectedPlanets.add(
                                            it.copy(offset = position)
                                        )
                                        planets.remove(it)
                                    }
                                // Animate to the tap position.
                                /*circleUIState.add(
                                    CircleUIState(
                                        color = listOf(colorList.random(), colorList.random()),
                                        offset = position
                                    )
                                )*/
                                /*offset.animateTo(position)*/
                            }
                        }
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .graphicsLayer {
                    scaleX = 0.5f
                    scaleY = 0.5f
                }
                .background(Color.Red)
                .align(Alignment.Center),
        )
        selectedPlanets.forEach {
            PlanetViewItem(
                uiState = it,
                degree = rotation
            )
        }
        /* TextButton(
             modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
             contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
             onClick = { circleUIState.clear() },
             shape = RoundedCornerShape(20.dp),
             colors = ButtonDefaults.textButtonColors(
                 containerColor = Blue100,
                 contentColor = Color.White
             )
         ) {
             Text(text = "Remove", fontSize = 20.sp)

         }
         CircleList(circleUIState = circleUIState, degree = rotation)*/

        /*Circle(offset = offset.value)*/
    }
}

@Composable
fun PlanetViewItem(uiState: PlanetUIState, degree: Float) {
    Box(modifier = Modifier
        .fillMaxSize()
        .rotate(degree)
        .drawBehind {
            with(uiState) {
                offset?.let {
                    rotate(degrees = degree * rotationSpeedFactor, pivot = offset) {
                        drawCircle(
                            brush = Brush.horizontalGradient(
                                colors = colors,
                                startX = offset.x,
                                endX = offset.x + radius
                            ),
                            radius = radius,
                            center = offset
                        )
                    }
                }
            }
        }
    )
}

@Composable
internal fun CircleList(circleUIState: List<CircleUIState>, degree: Float) {
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            circleUIState.forEach {
                rotate(degrees = degree, pivot = it.offset) {
                    drawCircle(
                        brush = Brush.horizontalGradient(
                            colors = it.color,
                            startX = it.offset.x,
                            endX = it.offset.x + 80f
                        ),
                        radius = 80f,
                        center = it.offset
                    )
                }
            }
        }
    )
}

@Composable
fun Circle(modifier: Modifier = Modifier, offset: Offset) {
    Box(modifier = modifier.drawBehind {
        drawCircle(
            color = Color.Blue,
            radius = 200f,
            center = offset
        )
    })
}