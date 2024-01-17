package com.cc221001.cc221015.Poke_Hike.viewModel

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StepCounterViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    // LiveData to observe changes in the step count
    private val _stepCountLiveData = MutableLiveData<Int>()
    val stepCountLiveData: LiveData<Int>
        get() = _stepCountLiveData

    // Method to update the step count
    fun updateStepCount(newStepCount: Int) {
        _stepCountLiveData.value = newStepCount
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Handle sensor changes here if needed
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes if needed
    }
}
