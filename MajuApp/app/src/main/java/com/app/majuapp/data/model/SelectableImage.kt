package com.app.majuapp.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class SelectableImage(
    var title: String,
    var desc: String,
    var imageUrl: String
) : Selectable {

    override var isSelected: Boolean = false
        set(value) {
            field = value
        }

    var isSelectedState by mutableStateOf(isSelected)

    override fun toggle() {
        isSelected = !isSelected
        isSelectedState = isSelected
    }

}
