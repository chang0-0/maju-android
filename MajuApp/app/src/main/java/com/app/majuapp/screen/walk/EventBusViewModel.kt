package com.app.majuapp.screen.walk

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.app.majuapp.domain.model.walk.eventbus.EventBusEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


private const val TAG = "EventBusViewModel_창영"

@HiltViewModel
class EventBusViewModel @Inject constructor() : ViewModel() {



} // End of EventBusViewModel class