package com.sourabh.chsatplace.respository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ChatEntityModel")
data class ChatEntityModel (


    @ColumnInfo(name = "message") var KEY_MESSAGE: String,
    @ColumnInfo(name = "username") var KEY_USERNAME : String,
    @ColumnInfo(name = "msgsend") var KEY_MSG_SEND : Boolean,
    @ColumnInfo(name = "timeStamp") var KEY_TIMESTAMP :String,
    @ColumnInfo(name = "status") var KEY_STATUS :Int,
    @ColumnInfo(name = "msgid") var KEY_MSG_ID :String,
    @ColumnInfo(name = "timestamp_delivered") var KEY_TIME_DELIVERED :String,
    @ColumnInfo(name = "timestamp_read") var KEY_TIME_READ :String,
    @ColumnInfo(name = "progress") var KEY_PROGRESS :String,
    @ColumnInfo(name = "type") var KEY_TYPE : String


){
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")var KEY_ID:Int=0
}
