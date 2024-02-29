package com.bunjne.bbit.domain

sealed class UIComponent{

    data class FllScreenDialog(
        val title: String,
        val description: String,
    ): UIComponent()

    data class Dialog(
        val title: String,
        val description: String,
        val positiveText: String,
        val negativeText: String,
        val onSuccess: () -> Unit,
        val onDismiss: () -> Unit
    ): UIComponent()

    data class None(
        val message: String,
    ): UIComponent()
}