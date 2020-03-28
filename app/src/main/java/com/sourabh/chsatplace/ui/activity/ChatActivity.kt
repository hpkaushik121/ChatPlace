package com.sourabh.chsatplace.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.R
import com.sourabh.chsatplace.adapter.ChatMessageAdapter
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.factory.ViewModelFactory
import com.sourabh.chsatplace.interfaces.KeyboardVisibilityListener
import com.sourabh.chsatplace.network.*
import com.sourabh.chsatplace.pojo.Data
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.ui.viewModels.MessageItemViewModel
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ChatActivity : AppCompatActivity(),View.OnClickListener,KeyboardVisibilityListener {
    private val adapter=ChatMessageAdapter()
    override fun onKeyboardVisibilityChanged(keyboardVisible: Boolean) {
        if(keyboardVisible){
            chatList.scrollToPosition(chatList.adapter!!.itemCount-1)
        }
    }

    override fun onClick(v: View?) {
        if(!message_box.text.toString().trim().isEmpty()){
            val df = SimpleDateFormat("dd MMM, yyyy h:mm:ss a")
            df.timeZone = TimeZone.getTimeZone("GMT")
            val gmtTime = df.format(Date())
            val message=MessageModel(message_box.text.toString(),"text",gmtTime)
            SendMessage(Gson().toJson(message),Constants.XMPP_NUMBER_2)
            send_btn.isEnabled=false
            val timer = object: CountDownTimer(500, 500) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    send_btn.isEnabled=true
                }
            }
            timer.start()
            message_box.setText("")

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val vm:MessageItemViewModel by lazy {
            ViewModelProviders.of(this, ViewModelFactory { MessageItemViewModel() }).get(MessageItemViewModel::class.java)
        }
        send_btn.setOnClickListener(this)
        chatList.adapter=adapter
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        Constants.setKeyboardVisibilityListener(this)
        username.text=Constants.XMPP_NUMBER_2
        fetchData(vm);
        back_btn.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun fetchData(vm:MessageItemViewModel) {
        ChatApplication.getInstance()
            .chatRepository
            .getUnReadChats(Constants.XMPP_NUMBER_2)
            .observe(this,
                androidx.lifecycle.Observer {
                    if (it.isNotEmpty()) {
                        MarkMessageRead(Constants.XMPP_NUMBER_2)
                    }
                })
        ChatApplication.getInstance().chatRepository
            .getChatList(Constants.XMPP_NUMBER_2).observe(this, androidx.lifecycle.Observer { s ->
                adapter.updateData(s)
                if (Constants.isScrollDueToAdd) {
                    chatList.layoutManager!!.scrollToPosition(s.size - 1)
                }
            })
        ChatApplication.getInstance().presenceRepository
            .getUserPresence("${Constants.XMPP_NUMBER_2.split("_")[0]}@${Constants.XMPP_DOMAIN_NAME}")
            .observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    if (it.isOnline) {
                        last_seen.text = "Online"
                    } else {
                        last_seen.text = it.lastOnline
                    }
                }

            })

        GlobalScope.launch {
            val apiInterface =
                ApiInterface(ConnectivityInterceptorImpl(ChatApplication.getInstance()))
            Network.request(
                call = apiInterface.getUserPresence(Constants.XMPP_NUMBER_2.split("_")[0]),
                success = {
                    if (it.status) {
                        try{
                        val data=Gson().fromJson(Gson().toJson(it.data),Data::class.java)
                         last_seen.text=data.offlineDate
                        }catch (e:Exception){
                            last_seen.text="Online"
                        }


                    }
                },
                error = {
                    last_seen.text=it.message
                }

            )

        }
    }
}
