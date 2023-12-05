package ua.good.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ua.good.model.Notification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NotificationsUtil @Inject constructor(private val context: Context) : INotificationsUtil {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(item: Notification) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mainIntent = Intent().apply { component = ComponentName(context, TARGET_ACTIVITY_NAME) }
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, flags)
        val notificationBuilder = NotificationCompat.Builder(context, DEFAULT_CHANEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(item.title)
            .setContentText(item.text)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(mainPendingIntent)

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DEFAULT_CHANEL_ID,
                DEFAULT_CHANEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionsInfo = ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            if (permissionsInfo == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(1, notificationBuilder.build())
            }
        } else {
            notificationManager.notify(1, notificationBuilder.build())
        }
    }

    private companion object {
        const val DEFAULT_CHANEL_ID = "DEFAULT_CHANEL_ID"
        const val DEFAULT_CHANEL_NAME = "DEFAULT_CHANEL_NAME"
        private const val TARGET_ACTIVITY_NAME = "ua.artem.template.ui.screens.MainActivity"
    }
}
