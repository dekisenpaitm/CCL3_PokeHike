package com.cc221001.cc221015.Poke_Hike.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CoinStashRepository {
    private val _coinStashLiveData = MutableLiveData<Int>(0)
    val coinStashLiveData: LiveData<Int> = _coinStashLiveData

    init{
        _coinStashLiveData.postValue(0)
    }
    fun plusCoinStash(addCoins: Int) {
        println("This is the amount of coins u earned: $addCoins")
        _coinStashLiveData.postValue(_coinStashLiveData.value?.plus(addCoins))
        println("This is your current Stash: ${_coinStashLiveData.value}")
    }

    fun updateCoinStash(newValue:Int){
        println("this is the newValue: $newValue")
        _coinStashLiveData.postValue(newValue)
    }

    fun minusCoinStash(removeCoins: Int) {
        val updatedCoins = _coinStashLiveData.value?.minus(removeCoins)
        _coinStashLiveData.postValue(updatedCoins)
    }
}