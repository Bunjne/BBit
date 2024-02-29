package com.bunjne.bbit.extension

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun Screen.initNav(transition: @Composable (Navigator) -> Unit = { SlideTransition(it) }) {
    Navigator(this) {
        transition(it)
    }
}