package com.bunjne.bbit.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunjne.bbit.App
import com.bunjne.bbit.android.ui.component.Gesture
import com.bunjne.bbit.ui.theme.BBitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
                topSafeArea = 0f,
                bottomSafeArea = 0f
            )
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Gesture()
    val list = listOf(text, "Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    LazyColumn(
        modifier = Modifier
            .wrapContentSize(),
        contentPadding = PaddingValues(28.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
//        item {
//            CircleFlowAnimation()
//        }
//        item {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                ProgressLoader()
//            }
//        }
//        items(
//            count = list.size
//        ) {
//            Text(
//                text = text,
//                fontSize = 40.sp
//            )
//        }
    }
}

@Composable
fun CircleFlowAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val radius = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(1000)
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.drawBehind {
            drawCircle(Color.Black, radius = radius.value)
        })
//        Box(modifier = Modifier.size(radius.value.dp).clip(CircleShape).background(Color.Blue))
    }
}

@Preview
@Composable
fun DefaultPreview() {
    BBitTheme(
        darkTheme = true,
        dynamicColor = true
    ) {
    }
}
