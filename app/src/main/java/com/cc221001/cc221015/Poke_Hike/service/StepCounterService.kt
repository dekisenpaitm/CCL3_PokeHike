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
import com.cc221001.cc221015.Poke_Hike.R

class StepCounterService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    // TODO: You need to set up a way to communicate with the ViewModel, maybe through a repository or shared data.
    // private lateinit var stepCounterViewModel: StepCounterViewModel

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
        startForeground(1, createNotification())
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
            val newStepCount = event.values[0].toInt()
            StepCounterRepository.updateStepCount(newStepCount)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Handle sensor accuracy changes if needed
    }

    private fun createNotification(): Notification {
        val channelId = "step_counter_channel"
        val channelName = "Step Counter Service"
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
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
        sensorManager.unregisterListener(this)
    }
}
