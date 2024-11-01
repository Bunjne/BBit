package com.bunjne.bbit.ui.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunjne.bbit.resources.Res
import com.bunjne.bbit.resources.more_sign_out
import com.bunjne.bbit.ui.util.ObserveAsEvent
import org.jetbrains.compose.resources.stringResource

@Composable
fun MoreRoute(
    viewModel: MoreViewModel,
    onSignOutSuccess: () -> Unit
) {
    MoreScreen(
        onSignOut = { viewModel.onAction(MoreUiAction.onSignOut) }
    )

    ObserveAsEvent(
        flow = viewModel.uiEvent,
        eventMap = mapOf(MoreUiEvent.SingOutSuccessFully to onSignOutSuccess)
    )
}

@Composable
fun MoreScreen(
    onSignOut: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = onSignOut,
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.more_sign_out),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}