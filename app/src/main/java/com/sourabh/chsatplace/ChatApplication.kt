package com.sourabh.chsatplace

import android.app.Application
import android.content.Intent
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sourabh.chsatplace.common.ASLifeCycle
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.interfaces.ChatSyncWorker
import com.sourabh.chsatplace.respository.ChatRepository
import com.sourabh.chsatplace.services.XMPPConnectionService
import org.jivesoftware.smack.packet.Presence
import java.util.concurrent.TimeUnit
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.work.Logger
import com.google.firebase.messaging.FirebaseMessaging
import com.sourabh.chsatplace.common.Constants
import java.lang.Exception
import java.text.DateFormat
import java.util.*


public class ChatApplication : Application(), ASLifeCycle.AppLifeCycle {
    lateinit var chatRepository: ChatRepository

    companion object {
        private lateinit var instance: ChatApplication
        var isInForeGround = false
        fun getInstance(): ChatApplication {
            return instance
        }
    }

    override fun onAppForeGround() {
        isInForeGround = true
        try {
            startService(Intent(this,XMPPConnectionService::class.java))
            if(XMPPConnectionSingletone.getInstance().mConnection.isConnected){
                val p = Presence(Presence.Type.available, "available", 42, Presence.Mode.available)
                XMPPConnectionSingletone.getInstance().mConnection.sendStanza(p)
                XMPPConnectionSingletone.getInstance().reconnectionManagerPrimary.enableAutomaticReconnection()
            }

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun onAppBackGround() {
        try {
            if (XMPPConnectionSingletone.getInstance().mConnection.isConnected) {
                val p = Presence(Presence.Type.unavailable, "Unavailable", 42, Presence.Mode.away)
                XMPPConnectionSingletone.getInstance().mConnection.sendStanza(p)
                XMPPConnectionSingletone.getInstance().reconnectionManagerPrimary.disableAutomaticReconnection()
                XMPPConnectionSingletone.getInstance().mConnection.disconnect()
                isInForeGround = false
            }

        }catch (e:Exception){

        }

    }

    override fun onCreate() {
        super.onCreate()
        val lifeCycle = ASLifeCycle()
        com.sourabh.chsatplace.utilities.Logger.verbose("App Started")
        registerActivityLifecycleCallbacks(lifeCycle)
        chatRepository = ChatRepository(applicationContext)
        instance = this
        lifeCycle.register(this)
        val conf=Configuration.Builder().build()

        WorkManager.initialize(this,conf)
        val task=PeriodicWorkRequestBuilder<ChatSyncWorker>(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this)
            .enqueue(task)

        FirebaseMessaging.getInstance().subscribeToTopic("${Constants.XMPP_NUMBER}_a")

    }


}