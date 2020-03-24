package com.sourabh.chsatplace.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.sourabh.chsatplace.pojo.msgIds
import org.jivesoftware.smack.chat.Chat


@Dao
interface ChattingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertChatList(chats: List<ChatEntityModel>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun inserChat(chats: ChatEntityModel)

    @Update
    fun updateMovie(chats: ChatEntityModel)

    @Query("SELECT * FROM ChatEntityModel WHERE id = :id_")
    fun getMovie(id_: Int): ChatEntityModel

    @Query("SELECT * FROM ChatEntityModel WHERE username=:Jid ORDER BY id ASC")
    fun getChatList(Jid:String?): LiveData<List<ChatEntityModel>>

    @Query("Update  ChatEntityModel SET status=2 , timestamp_delivered=:timeDelivered WHERE msgid=:msg_id and status <> 3  and username=:Jid ")
    fun setChatDelivered(timeDelivered: String?,msg_id:String?,Jid: String?)

    @Query("SELECT * FROM ChatEntityModel WHERE status=0 ORDER BY id LIMIT 1" )
    fun getUnsentMessage(): List<ChatEntityModel>

    @Query("SELECT msgid FROM ChatEntityModel WHERE status=4 and username=:username ORDER BY id" )
    fun getUnReadMessage(username:String): List<String>

    @Query("UPDATE ChatEntityModel SET status=1 WHERE msgid= :msgId and status=0")
    fun setMessageSent(msgId: String?)

    @Query("SELECT * FROM ChatEntityModel WHERE msgid =:msgId")
    fun checkIfExists(msgId:String?): List<ChatEntityModel>

    @Query("Update  ChatEntityModel SET status=3 , timestamp_read=:currentTimeMillis WHERE msgid in (:receiptId) and username=:Jid ")
    fun setChatRead(currentTimeMillis: String?, receiptId:List<String>,Jid: String?)

    @Query("SELECT * FROM ChatEntityModel WHERE  status=4 and username=:Jid ")
    fun getUnReadChats(Jid: String?):LiveData<List<ChatEntityModel>>

    @Query("UPDATE ChatEntityModel set status=5 WHERE  status=4 and  msgid in (:chat) ")
    fun setChatReceivedRead(chat:List<String>)
}