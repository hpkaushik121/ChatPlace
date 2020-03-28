package com.sourabh.chsatplace.common

import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.network.ApiInterface
import com.sourabh.chsatplace.network.ConnectivityInterceptorImpl
import com.sourabh.chsatplace.network.Network
import com.sourabh.chsatplace.pojo.Data
import com.sourabh.chsatplace.respository.PresenceEntityModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.packet.Stanza

class PresencePacketListener : StanzaListener {
    override fun processStanza(packet: Stanza?) {
        if(packet is Presence){
            val presence=packet as Presence
            if(presence.from.toString().contains("_a")){
                return
            }
            if(packet.isAvailable){
                val presenceEntityModel=PresenceEntityModel(packet.from.toString().split("/")[0],true,"")
                ChatApplication.getInstance().presenceRepository.setUserPresence(presenceEntityModel)
            }else{
                val apiInterface=ApiInterface(ConnectivityInterceptorImpl(ChatApplication.getInstance()))
                Network.request(
                    call = apiInterface.getUserPresence(packet.from.toString().split("@")[0]),
                    success = {
                        val data=Gson().fromJson(Gson().toJson(it.data),Data::class.java)
                        val presenceEntityModel=PresenceEntityModel(packet.from.toString().split("/")[0],false,data.offlineDate)
                        GlobalScope.launch {
                            ChatApplication.getInstance().presenceRepository.setUserPresence(presenceEntityModel)
                        }

                    },
                    error = {

                    }
                )
            }

        }
    }

}
