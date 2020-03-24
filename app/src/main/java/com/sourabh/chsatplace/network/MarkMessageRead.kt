package com.sourabh.chsatplace.network

import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.interfaces.ChatEntitisListener
import com.sourabh.chsatplace.interfaces.ChatEntityListener
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.utilities.Utils

class MarkMessageRead constructor(username:String):ChatEntitisListener {
    val user=username
    override fun onResponse(chatEt: List<String>) {
        Utils.sendReadAcknowledge(chatEt,user)
    }

    init {
        ChatApplication.getInstance()
            .chatRepository
            .getUnReadMessage(username,this)
    }
}