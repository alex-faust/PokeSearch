package com.example.pokesearch.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.navigation.NavDeepLinkBuilder
import com.example.pokesearch.R
import com.example.pokesearch.app.MainActivity
import com.example.pokesearch.ui.game.GameFragment

private const val NOTIFICATION_CHANNEL_ID = "Pokemon.channel"

fun NotificationManager.sendGeofenceEnteredNotification(context: Context, foundIndex: Int) {

    val contentIntent = Intent(context, GameFragment::class.java)
    contentIntent.putExtra(GeofencingConstants.EXTRA_GEOFENCE_INDEX, foundIndex)



    val contentPendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val pendingIntent = NavDeepLinkBuilder(context)
        .setComponentName(MainActivity::class.java)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.gameFragment)
        //.setArguments(bundle)
        .createPendingIntent()

    val mapImage = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.poke_ball
    )

    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(mapImage)
        .bigLargeIcon(null)

    //TODO figure out how to use picasso convert the string into an image

    // We use the name resource ID from the LANDMARK_DATA along with content_text to create
    // a custom message when a Geofence triggers.
    val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText(
            context.getString(
                R.string.content_text,
                context.getString(GeofencingConstants.SF_LANDMARK_DATA[foundIndex].name)
            )
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(contentPendingIntent)
        .setSmallIcon(R.drawable.poke_ball)
        .setStyle(bigPicStyle)
        .setLargeIcon(mapImage)

    notify(NOTIFICATION_ID, builder.build())
}

fun createChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setShowBadge(false)
        }

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(true)
        notificationChannel.description =
            context.getString(R.string.notification_channel_description)

        val notificationManager = context.getSystemService<NotificationManager>()
        /*
        val Context.notificationManager: NotificationManager?
         get() = getSystemService<NotificationManager>()
        And from now on, we can use notificationManager as if it is a property of Context:

        context.notificationManager?.sendNotification()


         */
        notificationManager?.createNotificationChannel(notificationChannel)
    }
}

private const val NOTIFICATION_ID = 33
