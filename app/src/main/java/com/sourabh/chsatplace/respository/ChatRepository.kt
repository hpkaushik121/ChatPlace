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

class ChatRepository constructor(val mContext: Context) {
    private val dbAccess=Room.databaseBuilder(mContext,DaoDatabaseAccess::class.java,"chatDb").build()
    fun insertChats(model: ChatEntityModel){
        Thread{
            dbAccess.ChattingDao().inserChat(model)
        }.start()

    }
    fun insertChats(model: ChatEntityModel,listener:ChatEntityListener){
        Thread{
            dbAccess.ChattingDao().inserChat(model)
            listener.onResponse(null)
        }.start()

    }

    fun insetChatList(list:List<ChatEntityModel>){
        Thread{
            dbAccess.ChattingDao().insertChatList(list)
        }.start()

    }
    fun getChatList(Jid:String?):LiveData<List<ChatEntityModel>>{
        return  dbAccess.ChattingDao().getChatList(Jid)
    }
    fun setChatDelivered(timeDelivered: String?,msgId:String?,Jid: String?){
        Thread{

            dbAccess.ChattingDao().setChatDelivered(timeDelivered,msgId,Jid)
        }.start()

    }
    fun getMessageToBeSent(listener:ChatEntityListener){
        Thread{
            val chatEt=dbAccess.ChattingDao().getUnsentMessage()
            try{
                listener.onResponse(chatEt[0])
            }catch (e:Exception){
                e.printStackTrace()
            }

        }.start()
    }
    fun getUnReadMessage(username:String,listener: ChatEntitisListener){
        Thread{
            val chatEt=dbAccess.ChattingDao().getUnReadMessage(username)
            try{
                listener.onResponse(chatEt)
            }catch (e:Exception){
                e.printStackTrace()
            }

        }.start()
    }
    fun setMessageSent(msgId: String?){
        Thread{
            dbAccess.ChattingDao().setMessageSent(msgId )
        }.start()

    }
    fun checkIfExists(msgId:String?):List<ChatEntityModel>{
        return dbAccess.ChattingDao().checkIfExists(msgId)
    }

    fun setChatRead(currentTimeMillis:  String?,  receiptId: List<String>,Jid: String?) {
        Thread{
            dbAccess.ChattingDao().setChatRead(currentTimeMillis!!,receiptId,Jid)
        }.start()

    }
    fun getUnReadChats(Jid: String?):LiveData<List<ChatEntityModel>>{
        return dbAccess.ChattingDao().getUnReadChats(Jid)
    }
    fun setChatReceivedRead(chat:List<String>){
        Thread{
            dbAccess.ChattingDao().setChatReceivedRead(chat)
        }.start()
    }
}