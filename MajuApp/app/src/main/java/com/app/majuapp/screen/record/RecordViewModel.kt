package com.app.majuapp.screen.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RecordViewModel_창영"

@HiltViewModel
class RecordViewModel @Inject constructor() : ViewModel() {
    val snackbarFlow = MutableSharedFlow<String>()

    suspend fun showSnackbar(text: String) {
        viewModelScope.launch {
            snackbarFlow.emit(text)
        }
    }

} // End of RecordViewModel class