package com.sourabh.chsatplace.interfaces

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.respository.ChatRepository
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener
import org.jxmpp.jid.Jid
import java.text.DateFormat
import java.util.*

class DeliveryListener  : ReceiptReceivedListener {



    override fun onReceiptReceived(
        fromJid: Jid?,
        toJid: Jid?,
        receiptId: String?,
        receipt: Stanza?
    ) {
        try{
            val msg=receipt as Message
            val messageM= Gson().fromJson(msg.body, MessageModel::class.java)
            if(msg.body!=null&&messageM.mimeType.equals("ACK_READ",ignoreCase = true)){
                try{
                    val turnsType = object : TypeToken<List<String>>() {}.type
                    val chat=Gson().fromJson<List<String>>(messageM.message,turnsType)
                        ChatApplication.getInstance().chatRepository.setChatRead(messageM.timeStamp,chat,fromJid.toString())


                }catch (e:java.lang.Exception){}


            }else if(msg.body!=null&&messageM.mimeType.equals("ACK_DEL",ignoreCase = true)){
                ChatApplication.getInstance().chatRepository.setChatDelivered(messageM.timeStamp,receiptId,fromJid!!.split("/")[0].toString())
            }else{
                val df = DateFormat.getDateTimeInstance()
                df.timeZone = TimeZone.getTimeZone("GMT")
                val gmtTime = df.format(Date())
                ChatApplication.getInstance().chatRepository.setChatDelivered(gmtTime,receiptId,fromJid!!.split("/")[0].toString())
            }

        }catch (e:Exception){

        }


    }

}