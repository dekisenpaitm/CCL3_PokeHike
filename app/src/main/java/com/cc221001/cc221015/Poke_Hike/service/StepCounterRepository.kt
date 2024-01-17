package com.cc221001.cc221015.Poke_Hike.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object StepCounterRepository {
    private val _stepCountLiveData = MutableLiveData<Int>()
    val stepCountLiveData: LiveData<Int> = _stepCountLiveData

    fun updateStepCount(newStepCount: Int) {
        _stepCountLiveData.postValue(newStepCount)
    }
}
