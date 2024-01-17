package com.cc221001.cc221015.Poke_Hike

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class for the Poke_Hike application.
 *
 * This class is annotated with @HiltAndroidApp, indicating that Hilt should
 * generate the necessary Dagger components for dependency injection in the application.
 * Hilt simplifies the process of setting up and using Dagger Android components.
 *
 * The use of @HiltAndroidApp eliminates the need to manually create Dagger components
 * and ensures that Dagger is integrated seamlessly with the Android application lifecycle.
 */
@HiltAndroidApp
class Application : Application()
