package com.sourabh.chsatplace.interfaces

import com.sourabh.chsatplace.utilities.Logger
import org.jivesoftware.smack.chat.Chat
import org.jivesoftware.smack.chat.ChatManagerListener
import org.jivesoftware.smack.chat.ChatMessageListener
import org.jivesoftware.smack.packet.ExtensionElement
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smackx.chatstates.packet.ChatStateExtension

class TypingListener constructor(): ChatManagerListener,
ChatMessageListener {

    override fun chatCreated(chat: Chat?, createdLocally: Boolean) {
        Logger.verbose("typing...")
    }

    override fun processMessage(chat: Chat?, message: Message?) {
        val element = message!!.getExtension(ChatStateExtension.NAMESPACE)
    }
}