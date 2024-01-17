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
    val stepCountLiveData: LiveData<Int> = StepCounterRepository.stepCountLiveData
    override fun onSensorChanged(event: SensorEvent?) {
        // Handle sensor changes here if needed
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes if needed
    }
}
