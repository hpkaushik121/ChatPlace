package com.sourabh.chsatplace.services

import android.os.Message
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.interfaces.ChatEntityListener
import com.sourabh.chsatplace.pojo.FirebaseMessageModel
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.utilities.Logger
import android.R
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import com.sourabh.chsatplace.ui.MainActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.utilities.Acknowledgement
import com.sourabh.chsatplace.utilities.Utils
import kotlin.random.Random


class FirebaseService:FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.data.isNotEmpty().let {
            Logger.verbose( "Message data payload: " + remoteMessage.data)
            try{
                val model=Gson().fromJson(Gson().toJson(remoteMessage.data),FirebaseMessageModel::class.java)
                val id=model.stanzaId

                if(ChatApplication.getInstance().chatRepository.checkIfExists(id).isEmpty()){
                    XMPPConnectionSingletone.getInstance().setSecondayConnection(Constants.XMPP_NUMBER)

                }

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    companion object{

        fun createNotification(aMessage: String, context: Context,from:String) {
             var notifManager: NotificationManager? = null
            val NOTIFY_ID = Random.nextInt(5) // ID of notification
            val id = "some_channel" // default_channel_id
            val intent: Intent
            val pendingIntent: PendingIntent
            val builder: NotificationCompat.Builder
            if (notifManager == null) {
                notifManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                var mChannel = notifManager.getNotificationChannel(id)
                if (mChannel == null) {
                    mChannel = NotificationChannel(id, from, importance)
                    notifManager.createNotificationChannel(mChannel)
                }
                builder = NotificationCompat.Builder(context, id)
                intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                builder.setContentTitle(from)                            // required
                    .setSmallIcon(R.drawable.ic_popup_reminder)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setTicker(aMessage)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
            } else {
                builder = NotificationCompat.Builder(context, id)
                intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                builder.setContentTitle(from)                            // required
                    .setSmallIcon(R.drawable.ic_popup_reminder)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))

            }
            val notification = builder.build()
            notifManager.notify(NOTIFY_ID, notification)
        }

    }

}