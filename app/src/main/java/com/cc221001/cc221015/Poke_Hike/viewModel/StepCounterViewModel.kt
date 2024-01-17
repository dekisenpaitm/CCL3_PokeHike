package com.cc221001.cc221015.Poke_Hike.viewModel

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cc221001.cc221015.Poke_Hike.service.StepCounterRepository

class StepCounterViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {
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
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes if needed
    }
}
