package com.sourabh.chsatplace.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.common.XMPPConnectionSingletone

public class XMPPConnectionService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        XMPPConnectionSingletone.getInstance().setSecondayConnection(Constants.XMPP_NUMBER)
        if(ChatApplication.isInForeGround){
            XMPPConnectionSingletone.getInstance().setPrimaryConnection(Constants.XMPP_NUMBER)
        }
        return START_STICKY
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}