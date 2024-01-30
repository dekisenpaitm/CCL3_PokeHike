package com.cc221001.cc221015.Poke_Hike.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.data.PokeHikeDatabaseHandler

class StepCounterService : Service(), SensorEventListener {
    private lateinit var pokeHikeDatabaseHandler: PokeHikeDatabaseHandler
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("StepCounterService", "onCreate")
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
        startForeground(1, createNotification())
        pokeHikeDatabaseHandler = PokeHikeDatabaseHandler(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("StepCounterService", "onStartCommand")
        // Handle any additional logic when the service is started
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(pokeHikeDatabaseHandler.isDatabaseInitialized("StepCounter") && pokeHikeDatabaseHandler.retrieveSteps(0)?.id != null) {
            if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                val newStepCount = event.values[0].toInt()
                var previousStepCount =
                    pokeHikeDatabaseHandler.retrieveSteps(0)?.amount
                if (previousStepCount == 0) {
                    previousStepCount = event.values[0].toInt()
                }
                val difference = newStepCount - previousStepCount!!
                //CoinStashRepository.plusCoinStash(difference)
                pokeHikeDatabaseHandler.updatePokeCoin(
                    pokeHikeDatabaseHandler.getPokeCoinById(1),
                    pokeHikeDatabaseHandler.getPokeCoinById(1).amount + difference
                )
                StepCounterRepository.updateStepCount(newStepCount)
                pokeHikeDatabaseHandler.updateCurrentSteps(0, newStepCount)
                //println(stepCounterBaseHandler.retrieveSteps(0))
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Handle sensor accuracy changes if needed
    }

    private fun createNotification(): Notification {
        val channelId = "step_counter_channel"
        val channelName = "Step Counter Service"
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        return Notification.Builder(this, channelId)
            .setContentTitle("Step Counter Running")
            .setContentText("Counting your steps...")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's icon
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("StepCounterService", "onDestroy")
        sensorManager.unregisterListener(this)
    }
}

