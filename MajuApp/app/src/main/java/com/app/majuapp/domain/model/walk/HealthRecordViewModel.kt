package com.app.majuapp.domain.model.walk

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HealthRecordViewModel @Inject constructor() : ViewModel() {

    val _stepCount = MutableStateFlow(0)
    val stepCount = _stepCount.asStateFlow()

    fun updateStepCount(newStep: Int) {
        _stepCount.value = newStep
    } // End of updateStepCount()


//    fun accessGoogleFit(context: Context) {
//        val client = Fitness.getSensorsClient(context, GoogleSignIn.getLastSignedInAccount(this)!!)
//        val request = SensorRequest.Builder()
//            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
//            .setSamplingRate(1, TimeUnit.SECONDS)
//            .build()
//
//        client.add(request, object : OnDataPointListener {
//            override fun onDataPoint(dataPoint: DataPoint) {
//                for (field in dataPoint.dataType.fields) {
//                    val value = dataPoint.getValue(field).asInt()
//                    Log.i("GoogleFit", "Field: $field Value: $value")
//                    updateStepCount(value)
//                }
//            }
//        }).addOnSuccessListener {
//            Log.i("GoogleFit", "Listener registered!")
//        }.addOnFailureListener { e ->
//            Log.e("GoogleFit", "Listener not registered.", e)
//        }
//    }

} // End of HealthRecordViewModel class