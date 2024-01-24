package com.cc221001.cc221015.Poke_Hike.stateModel

import com.cc221001.cc221015.Poke_Hike.domain.OnBoardingState

data class OnBoardingViewState(
    val currentState: OnBoardingState? = null,
    val allStates: List<OnBoardingState?> = emptyList()
)
