package com.bunjne.bbit.android.extension

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.bunjne.bbit.android.MainActivity

@Composable
fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is MainActivity) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}