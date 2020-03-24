package com.sourabh.chsatplace.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.factory.ViewModelFactory
import com.sourabh.chsatplace.pojo.FirebaseMessageModel
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.respository.ChatEntityModel
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.packet.Stanza
import org.jxmpp.jid.impl.JidCreate

class Utils  {

    inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(noinline creator: (() -> T)? = null): T {
        return if (creator == null)
            ViewModelProviders.of(this).get(T::class.java)
        else
            ViewModelProviders.of(this, ViewModelFactory(creator)).get(T::class.java)
    }

    companion object {
        fun showToast(message: String) {
            Toast.makeText(ChatApplication.getInstance(), message, Toast.LENGTH_LONG).show()
        }

        fun verifyAvailableNetwork(): Boolean {
            val connectivityManager =
                ChatApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun sendReadAcknowledge(msg:List<String>,  to_JID: String) {

            val msgModel = MessageModel(Gson().toJson(msg), "ACK_READ", "")
            try {
                if (verifyAvailableNetwork() && XMPPConnectionSingletone.getInstance().connection.isConnected) {
                    val jid = JidCreate.entityBareFrom(to_JID)
                    val chatManager =
                        ChatManager.getInstanceFor(XMPPConnectionSingletone.getInstance().connection)
                    val chat = chatManager.chatWith(jid)
                    val newMessage = Message()
                    newMessage.body = Gson().toJson(msgModel)
                    newMessage.type = Message.Type.normal
                    chat.send(newMessage)
                    ChatApplication.getInstance().chatRepository.setChatReceivedRead(msg)
                }

            } catch (e: Exception) {

            }
        }

    }


}