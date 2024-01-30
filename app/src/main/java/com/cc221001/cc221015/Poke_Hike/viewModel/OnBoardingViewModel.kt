package com.cc221001.cc221015.Poke_Hike.viewModel

import androidx.lifecycle.ViewModel
import com.cc221001.cc221015.Poke_Hike.data.PokeHikeDatabaseHandler
import com.cc221001.cc221015.Poke_Hike.domain.OnBoardingState
import com.cc221001.cc221015.Poke_Hike.stateModel.OnBoardingViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnBoardingViewModel(private val db: PokeHikeDatabaseHandler) : ViewModel() {
    private val _onboardingViewState = MutableStateFlow(OnBoardingViewState())
    val onboardingViewState: StateFlow<OnBoardingViewState> = _onboardingViewState.asStateFlow()

    fun createOnboardingViewState(name: String) {
        val onBoardingViewState = OnBoardingState(name = name, value = false)
        db.insertStates(onBoardingViewState)
    }

    fun getState(name: String) {
        _onboardingViewState.update {
            it.copy(
                currentState = db.getState(sName = name),
                allStates = db.getAllStates())
        }
    }

    fun setState(name: String, state: Boolean) {
        db.setState(name=name, newValue=state)
        getAllStates()
    }

    fun getAllStates() {
        _onboardingViewState.update {
            it.copy(allStates = db.getAllStates())
        }
    }

    fun resetAllStates(){
        db.setState(name="homePage", newValue=false)
        db.setState(name="weatherPage", newValue=false)
        db.setState(name="favPage", newValue=false)
        db.setState(name="listPage", newValue=false)
        db.setState(name="shopPage", newValue=false)
        db.setState(name="profilePage", newValue=false)
        getAllStates()
    }
}