package com.sourabh.chsatplace.respository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.interfaces.ChatEntitisListener
import com.sourabh.chsatplace.interfaces.ChatEntityListener
import com.sourabh.chsatplace.pojo.msgIds
import com.sourabh.chsatplace.utilities.Logger
import org.jivesoftware.smack.chat2.Chat

class ChatRepository constructor(val chatDao: ChattingDao) {
    fun insertChats(model: ChatEntityModel){
        Thread{
            chatDao.inserChat(model)
        }.start()

    }
    fun insertChats(model: ChatEntityModel,listener:ChatEntityListener){
        Thread{
            chatDao.inserChat(model)
            listener.onResponse(null)
        }.start()

    }

    fun insetChatList(list:List<ChatEntityModel>){
        Thread{
            chatDao.insertChatList(list)
        }.start()

    }
    fun getChatList(Jid:String?):LiveData<List<ChatEntityModel>>{
        return  chatDao.getChatList(Jid)
    }
    fun setChatDelivered(timeDelivered: String?,msgId:String?,Jid: String?){
        Thread{

            chatDao.setChatDelivered(timeDelivered,msgId,Jid)
        }.start()

    }
    fun getMessageToBeSent(listener:ChatEntityListener){
        Thread{
            val chatEt=chatDao.getUnsentMessage()
            try{
                listener.onResponse(chatEt[0])
            }catch (e:Exception){
                e.printStackTrace()
            }

        }.start()
    }
    fun getUnReadMessage(username:String,listener: ChatEntitisListener){
        Thread{
            val chatEt=chatDao.getUnReadMessage(username)
            try{
                listener.onResponse(chatEt)
            }catch (e:Exception){
                e.printStackTrace()
            }

        }.start()
    }
    fun setMessageSent(msgId: String?){
        Thread{
            chatDao.setMessageSent(msgId )
        }.start()

    }
    fun checkIfExists(msgId:String?):List<ChatEntityModel>{
        return chatDao.checkIfExists(msgId)
    }

    fun setChatRead(currentTimeMillis:  String?,  receiptId: List<String>,Jid: String?) {
        Thread{
            chatDao.setChatRead(currentTimeMillis!!,receiptId,Jid)
        }.start()

    }
    fun getUnReadChats(Jid: String?):LiveData<List<ChatEntityModel>>{
        return chatDao.getUnReadChats(Jid)
    }
    fun setChatReceivedRead(chat:List<String>){
        Thread{
            chatDao.setChatReceivedRead(chat)
        }.start()
    }

    fun getMessageViewLIst():LiveData<List<ChatsView>>{
        return chatDao.getMessageViewLIst()
    }
}