package com.ekodex.manpowerhrms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random
import android.app.PendingIntent
import android.content.Intent
import com.ekodex.manpowerhrms.Others.SharedPrefManager

class OnlineMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.data["title"] ?: message.notification?.title ?: "New Message"
        val body = message.data["body"] ?: message.notification?.body ?: "You have a new notification"
        val type = message.data["type"] ?: "default"


        Log.i("11111", "title - $title")
        Log.i("11111", "body - $body")
        Log.i("11111", "type - $type")

        showNotification(title, body,type)
    }

    private fun showNotification(title: String, body: String, type: String) {

        val notificationId = Random.nextInt()

        val (channelId, soundUri, channelName) =
            if (type.equals("birthday", ignoreCase = true)) {
                Triple(
                    "birthday_notifications",
                    Uri.parse("android.resource://$packageName/${R.raw.birthday_alert}"),
                    "Birthday Notifications"
                )
            } else {
                Triple(
                    "crm_notifications",
                    Uri.parse("android.resource://$packageName/${R.raw.notification_beep}"),
                    "HRMS Notifications"
                )
            }

        // Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = channelName
                setSound(soundUri, attributes)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // ✅ ADDED: PendingIntent based on type & login
        val pendingIntent = if (type.equals("voucher", ignoreCase = true) && SharedPrefManager.getInstance(applicationContext).isLoggedIn) {

            androidx.navigation.NavDeepLinkBuilder(this)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.hrms_nav_graph)
                .setDestination(R.id.voucherManagementFragment)
                .createPendingIntent()

        } else {

            val intent = Intent(this, MainActivity::class.java)
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // ✅ ADDED

        // ❗ Only works pre-Android 8
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setSound(soundUri)
        }

        with(NotificationManagerCompat.from(this)) {
            if (androidx.core.app.ActivityCompat.checkSelfPermission(
                    this@OnlineMessagingService,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                notify(
                    "birthday_${System.currentTimeMillis()}_${Random.nextInt()}",
                    notificationId,
                    builder.build()
                )
            }
        }
    }


    /*    private fun showNotification(title: String, body: String, type: String) {

            val notificationId = Random.nextInt()

            val (channelId, soundUri, channelName) =
                if (type.equals("birthday", ignoreCase = true)) {
                    Triple(
                        "birthday_notifications",
                        Uri.parse("android.resource://$packageName/${R.raw.birthday_alert}"),
                        "Birthday Notifications"
                    )
                } else {
                    Triple(
                        "crm_notifications",
                        Uri.parse("android.resource://$packageName/${R.raw.notification_beep}"),
                        "HRMS Notifications"
                    )
                }

            // Android 8+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val attributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = channelName
                    setSound(soundUri, attributes)
                }

                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }

            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            // ❗ Only works pre-Android 8
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                builder.setSound(soundUri)
            }

            with(NotificationManagerCompat.from(this)) {
                if (androidx.core.app.ActivityCompat.checkSelfPermission(
                        this@OnlineMessagingService,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
    //                notify(notificationId, builder.build())
                    notify(
                        "birthday_${System.currentTimeMillis()}_${Random.nextInt()}",
                        notificationId,
                        builder.build()
                    )

                }
            }
        }*/


}
