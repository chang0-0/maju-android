package com.app.majuapp.screen.walk

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WalkViewModel @Inject constructor() : ViewModel() {

    // InformDialog
    private val _showInformDialog = MutableStateFlow<Boolean>(false)
    val showInfromDialog = _showInformDialog.asStateFlow()

    fun setShowInfromDialog() {
        _showInformDialog.value = !_showInformDialog.value
    } // End of setShowInfromDialog()
} // End of WalkViewModel