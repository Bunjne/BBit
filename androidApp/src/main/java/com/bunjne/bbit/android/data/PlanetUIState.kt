package com.bunjne.bbit.android.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


data class PlanetUIState(
    val type: PlanetType,
    val colors: List<Color>,
    val rotationSpeedFactor: Float,
    val radius: Float,
    val offset: Offset? = null
)

enum class PlanetType {
    Mercury,
    Venus,
    Earth,
    Mars,
    Jupiter,
    Saturn,
    Uranus,
    Neptune
}