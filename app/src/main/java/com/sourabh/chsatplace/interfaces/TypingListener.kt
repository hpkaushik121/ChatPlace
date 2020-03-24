package com.sourabh.chsatplace.interfaces

import org.jivesoftware.smack.chat.Chat
import org.jivesoftware.smack.chat.ChatManagerListener
import org.jivesoftware.smack.chat.ChatMessageListener
import org.jivesoftware.smack.packet.Message

class TypingListener constructor(): ChatManagerListener,
    ChatMessageListener {

    override fun chatCreated(chat: Chat?, createdLocally: Boolean) {

    }

    override fun processMessage(chat: Chat?, message: Message?) {

    }
}