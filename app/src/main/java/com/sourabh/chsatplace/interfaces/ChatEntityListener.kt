package com.sourabh.chsatplace.interfaces

import com.sourabh.chsatplace.respository.ChatEntityModel

interface ChatEntityListener {
    fun onResponse(chatEntityModel: ChatEntityModel?)
}