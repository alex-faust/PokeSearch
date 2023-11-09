package com.example.pokesearch.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.pokesearch.BuildConfig
import com.example.pokesearch.R
import com.example.pokesearch.app.MainActivity

private const val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + ".channel"

fun sendNotification(context: Context, foundIndex: Int) {

    val contentIntent = Intent(context, MainActivity::class.java)
    contentIntent.putExtra(GeofenceUtils.EXTRA_GEOFENCE_INDEX, foundIndex)
    val notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // We need to create a NotificationChannel associated with our CHANNEL_ID before sending a notification.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null
    ) {
        val name = context.getString(R.string.app_name)
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val pendingIntent = NavDeepLinkBuilder(context.applicationContext)
        .setComponentName(MainActivity::class.java)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.gameFragment)
        .createPendingIntent()


    //create a pending intent that opens MainActivity when the user clicks on the notification
    //val stackBuilder = TaskStackBuilder.create(context)
       // .addParentStack(MainActivity::class.java)
       // .addNextIntent(pendingIntent)
    //val notificationPendingIntent = stackBuilder
       // .getPendingIntent(getUniqueId(), PendingIntent.FLAG_UPDATE_CURRENT)

//    build the notification object with the data to be shown
    val notification = NotificationCompat.Builder(context.applicationContext, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("pokemon.name")
        .setContentText("pokemon.name")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(getUniqueId(), notification)
}
fun createChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.channel_name),

            NotificationManager.IMPORTANCE_HIGH
        )
            .apply {
                setShowBadge(false)
            }

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(true)
        notificationChannel.description = context.getString(R.string.notification_channel_description)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}


private fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())

private const val CHANNEL_ID = "Pokemon"