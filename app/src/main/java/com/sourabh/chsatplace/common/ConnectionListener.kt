package com.sourabh.chsatplace.common

import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.network.CheckUnsentMessages
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.packet.Presence
import java.lang.Exception
import java.text.DateFormat
import java.util.*

class ConnectionListener : ConnectionListener {
    override fun reconnectionFailed(e: Exception?) {

    }

    override fun reconnectingIn(seconds: Int) {

    }

    override fun connected(connection: XMPPConnection?) {
        if (XMPPConnectionSingletone.getInstance().connection.isConnected) {
            XMPPConnectionSingletone.getInstance().connection.login()
            val p = Presence(Presence.Type.available, "available", 42, Presence.Mode.available)
            XMPPConnectionSingletone.getInstance().connection.sendStanza(p)
            CheckUnsentMessages()
        } else {
            connectionClosed()
        }


    }

    override fun connectionClosed() {
        XMPPConnectionSingletone.getInstance().setSecondayConnection(Constants.XMPP_NUMBER,true)

    }

    override fun connectionClosedOnError(e: Exception?) {
        connectionClosed()
    }

    override fun reconnectionSuccessful() {
        connectionClosed()
    }

    override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {

    }
}