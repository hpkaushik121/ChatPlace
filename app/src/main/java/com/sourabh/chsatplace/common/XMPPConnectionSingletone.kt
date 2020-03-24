package com.sourabh.chsatplace.common

import android.content.Context
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.interfaces.DeliveryListener
import com.sourabh.chsatplace.interfaces.MessageListener
import com.sourabh.chsatplace.interfaces.TypingListener
import com.sourabh.chsatplace.network.CheckUnsentMessages
import com.sourabh.chsatplace.pojo.FirebaseMessageModel
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.respository.ChatRepository
import com.sourabh.chsatplace.utilities.Acknowledgement
import com.sourabh.chsatplace.utilities.Logger
import org.jivesoftware.smack.*
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.ping.PingFailedListener
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager
import org.jxmpp.jid.impl.JidCreate
import org.jxmpp.jid.parts.Resourcepart
import java.io.IOException
import java.lang.Exception
import java.net.InetAddress

class XMPPConnectionSingletone private constructor() :
    org.jivesoftware.smack.ConnectionListener {
    override fun connected(conn: XMPPConnection?) {
        if (mConnection.isConnected) {
            mConnection.login()
            val p = Presence(Presence.Type.available, "available", 42, Presence.Mode.available)
            mConnection.sendStanza(p)
        } else {
            connectionClosed()
        }

    }

    override fun connectionClosed() {
        if (ChatApplication.isInForeGround && !mConnection.isConnected) {
            setPrimaryConnection(Constants.XMPP_NUMBER)
        }

    }

    override fun connectionClosedOnError(e: Exception?) {
        connectionClosed()
    }

    override fun reconnectionSuccessful() {
        connectionClosed()
    }

    override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {
    }

    override fun reconnectionFailed(e: Exception?) {

    }

    override fun reconnectingIn(seconds: Int) {

    }

    public lateinit var mConnection: XMPPTCPConnection
    public lateinit var connection: XMPPTCPConnection
    public lateinit var reconnectionManagerPrimary: ReconnectionManager

    private object HOLDER {
        val INSTANCE = XMPPConnectionSingletone()
    }

    companion object {
        fun getInstance(): XMPPConnectionSingletone {
            val instance: XMPPConnectionSingletone by lazy { HOLDER.INSTANCE }
            return instance
        }

    }

    fun setPrimaryConnection(username: String) {

        try {
            Thread {
                try {
                    if (!::mConnection.isInitialized || !mConnection.isConnected) {
                        val address = InetAddress.getByName(Constants.XMPP_SERVER_IP)
                        val serviceName = JidCreate.domainBareFrom(Constants.XMPP_SERVER_IP)
                        val config = XMPPTCPConnectionConfiguration
                            .builder()
                            .setUsernameAndPassword(username, Constants.XMPP_COMMON_PASSWORD)
                            .setPort(5222)
                            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                            .setXmppDomain(serviceName)
                            .setHostAddress(address)
                            .setConnectTimeout(5000)
                            .setDebuggerEnabled(true)
                            .setResource(Resourcepart.from("Mobile"))
                        mConnection = XMPPTCPConnection(config.build())
                        reconnectionManagerPrimary = ReconnectionManager.getInstanceFor(mConnection)
                        reconnectionManagerPrimary.setFixedDelay(1)
                        reconnectionManagerPrimary.enableAutomaticReconnection()
                        mConnection.addConnectionListener(this)
                        if (ChatApplication.isInForeGround) {
                            mConnection.connect()
                        }

                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSecondayConnection(username: String,isClosed:Boolean=false) {
        try {
            Thread {
                try {
                    if(!::connection.isInitialized ||!connection.isConnected||isClosed) {


                        val address = InetAddress.getByName(Constants.XMPP_SERVER_IP)
                        val serviceName = JidCreate.domainBareFrom(Constants.XMPP_SERVER_IP)
                        val config = XMPPTCPConnectionConfiguration
                            .builder()
                            .setUsernameAndPassword(username + "_a", "1234567890")
                            .setPort(5222)
                            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                            .setXmppDomain(serviceName)
                            .setHostAddress(address)
                            .setConnectTimeout(5000)
                            .setDebuggerEnabled(true)
                            .setResource(Resourcepart.from("Mobile"))
                        connection = XMPPTCPConnection(config.build())
                        connection.addConnectionListener(ConnectionListener())
                        connection = XMPPTCPConnection(config.build())
                        connection.addConnectionListener(ConnectionListener())
                        connection.setReplyToUnknownIq(true)
                        connection.replyTimeout = 5000
                        connection.packetReplyTimeout = 5000
                        val dm = DeliveryReceiptManager.getInstanceFor(connection)
                        dm.autoReceiptMode = DeliveryReceiptManager.AutoReceiptMode.always
                        dm.autoAddDeliveryReceiptRequests()
                        dm.addReceiptReceivedListener(DeliveryListener())
                        connection.setUseStreamManagementResumption(true)
                        connection.setUseStreamManagement(true)
                        val reconnectionManagerSeconday =
                            ReconnectionManager.getInstanceFor(connection)
                        reconnectionManagerSeconday.setFixedDelay(1)
                        reconnectionManagerSeconday.enableAutomaticReconnection()
                        val chatManager = ChatManager.getInstanceFor(connection)
                        chatManager.addIncomingListener(MessageListener())
                        val manager =
                            org.jivesoftware.smack.chat.ChatManager.getInstanceFor(connection)
                        manager.addChatListener(TypingListener())
                        connection.connect()
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}