package com.sourabh.chsatplace.network

import android.os.CountDownTimer
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.interfaces.ChatEntityListener
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.utilities.Logger
import com.sourabh.chsatplace.utilities.Utils
import kotlinx.android.synthetic.main.activity_chat.*
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.packet.Stanza
import org.jxmpp.jid.impl.JidCreate
import java.lang.Exception

class CheckUnsentMessages : ChatEntityListener, StanzaListener {
    override fun processStanza(packet: Stanza?) {
        ChatApplication.getInstance().chatRepository.setMessageSent(packet!!.stanzaId)
        CheckUnsentMessages()
    }

    override fun onResponse(chatEntityModel: ChatEntityModel?) {
        try {
            if(Utils.verifyAvailableNetwork()&&XMPPConnectionSingletone.getInstance().connection.isConnected){
                val jid = JidCreate.entityBareFrom(chatEntityModel!!.KEY_USERNAME)
                val chatManager =
                    ChatManager.getInstanceFor(XMPPConnectionSingletone.getInstance().connection)

                val chat = chatManager.chatWith(jid)
                val p = Presence(Presence.Type.subscribe)
                p.to = chat.xmppAddressOfChatPartner
                val newMessage = Message()
                newMessage.body = chatEntityModel.KEY_MESSAGE
                newMessage.stanzaId = chatEntityModel.KEY_MSG_ID
                newMessage.type = Message.Type.normal
                XMPPConnectionSingletone.getInstance().connection.sendStanza(p)
                XMPPConnectionSingletone.getInstance().connection.addStanzaIdAcknowledgedListener(chatEntityModel.KEY_MSG_ID,this)
                chat.send(newMessage)

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {

        ChatApplication.getInstance().chatRepository.getMessageToBeSent(this)


    }
}