package com.cc221001.cc221015.Poke_Hike.viewModel

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cc221001.cc221015.Poke_Hike.data.PokeCoinBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.StepCounterBaseHandler
import com.cc221001.cc221015.Poke_Hike.service.StepCounterRepository

class StepCounterViewModel(private val db:StepCounterBaseHandler, private val pcdb:PokeCoinBaseHandler) : ViewModel(), SensorEventListener {
    private val _stepCountLiveData = MutableLiveData<Int>()
    val stepCountLiveData: LiveData<Int> = _stepCountLiveData
    init {
        // Observe the repository's LiveData and update the ViewModel's LiveData
        StepCounterRepository.stepCountLiveData.observeForever { newStepCount ->
            _stepCountLiveData.value = newStepCount
            println("This is stepcount in ViewModel: $newStepCount")
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            db.updateCurrentSteps(0,event.values[0].toInt())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes if needed
    }
}
