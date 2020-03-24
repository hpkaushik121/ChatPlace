package com.sourabh.chsatplace.interfaces

import android.content.Context
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.respository.ChatRepository
import com.sourabh.chsatplace.services.FirebaseService
import com.sourabh.chsatplace.utilities.Acknowledgement
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Stanza
import org.jxmpp.jid.EntityBareJid

class MessageListener: IncomingChatMessageListener {


    override fun newIncomingMessage(from: EntityBareJid?, message: Message?, chat: Chat?) {
        val messageM= Gson().fromJson(message!!.body,MessageModel::class.java)
        if(messageM.mimeType.equals("ACK_READ",ignoreCase = true)||messageM.mimeType.equals("ACK_DEL",ignoreCase = true)){

            DeliveryListener().onReceiptReceived(from,message.to,message.stanzaId,message)
        }else{
            val id=message.stanzaId
            val messageString=message.body
            val messagefrom=from.toString()
            val status=4
            val type=messageM.mimeType
            val timeSent=messageM.timeStamp
            val timeDeliverd=""
            val timeRead=""
            val chatEntityModel=ChatEntityModel(messageString,messagefrom,false,timeSent,status,
                id,timeDeliverd,timeRead,"0",type)
            if(ChatApplication.getInstance().chatRepository.checkIfExists(id).isEmpty()){
                ChatApplication.getInstance().chatRepository.insertChats(chatEntityModel)
                if(!ChatApplication.isInForeGround){
                   FirebaseService.createNotification(messageString,ChatApplication.getInstance(),messagefrom)
                }
            }

        }



    }



}