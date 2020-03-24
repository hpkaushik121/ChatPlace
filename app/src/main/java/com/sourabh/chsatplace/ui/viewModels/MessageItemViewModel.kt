package com.sourabh.chsatplace.ui.viewModels

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.adapter.ChatMessageAdapter
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.network.MarkMessageRead
import com.sourabh.chsatplace.network.SendMessage
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.respository.ChatRepository
import com.sourabh.chsatplace.utilities.Acknowledgement
import com.sourabh.chsatplace.utilities.Logger
import com.sourabh.chsatplace.utilities.Utils
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList

class MessageItemViewModel constructor(actvity:AppCompatActivity): ViewModel(),Observable {
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
    @Bindable
    var messageAdapter= ChatMessageAdapter(this,actvity)
    private val act=actvity

    @Bindable
    var listData= ArrayList<ChatEntityModel>()

    init {
        ChatApplication.getInstance()
            .chatRepository
            .getUnReadChats("${Constants.XMPP_NUMBER_2}_a@${Constants.XMPP_DOMAIN_NAME}")
            .observe(act,
            Observer {
                if(it.isNotEmpty()){
                  MarkMessageRead("${Constants.XMPP_NUMBER_2}_a@${Constants.XMPP_DOMAIN_NAME}")

                }


            })

       ChatApplication.getInstance().chatRepository
            .getChatList("${Constants.XMPP_NUMBER_2}_a@${Constants.XMPP_DOMAIN_NAME}").observe(act, Observer { s ->
                messageAdapter.updateData(s)
               if(Constants.isScrollDueToAdd){
                   actvity.chatList.layoutManager!!.scrollToPosition(s.size-1)
               }

            })
    }

    fun onSendBtnClick(view: View) {
    }
}