package com.sourabh.chsatplace.network

import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.interfaces.ChatEntityListener
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.respository.ChatEntityModel
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jxmpp.jid.impl.JidCreate
import java.lang.Exception

class SendMessage constructor(message: String?, to: String?):ChatEntityListener {
    override fun onResponse(chatEntityModel: ChatEntityModel?) {
        CheckUnsentMessages()
    }

    init {
        try {
            val newMessage = Message()
            newMessage.body = message
            newMessage.type = Message.Type.normal
            val id = newMessage.stanzaId
            val messageString = newMessage.body
            val status = 0
            val type = "text"
            val msg=Gson().fromJson(message,MessageModel::class.java)
            val timeSent = msg.timeStamp
            val timeDeliverd = ""
            val timeRead = ""
            val chatEntityModel = ChatEntityModel(
                messageString, to!!, true, timeSent, status,
                id, timeDeliverd, timeRead, "0", type
            )
            ChatApplication.getInstance().chatRepository.insertChats(chatEntityModel,this)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}