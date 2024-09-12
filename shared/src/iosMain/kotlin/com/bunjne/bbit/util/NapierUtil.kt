package com.bunjne.bbit.util

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun setUpNapier() {
    Napier.base(DebugAntilog())
}