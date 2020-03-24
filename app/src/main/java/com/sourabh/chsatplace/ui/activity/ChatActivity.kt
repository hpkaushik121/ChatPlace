package com.sourabh.chsatplace.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.R
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.databinding.ActivityChatBinding
import com.sourabh.chsatplace.factory.ViewModelFactory
import com.sourabh.chsatplace.interfaces.KeyboardVisibilityListener
import com.sourabh.chsatplace.network.SendMessage
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.ui.viewModels.MessageItemViewModel
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.DateFormat
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.text.SimpleDateFormat


class ChatActivity : AppCompatActivity(),View.OnClickListener,KeyboardVisibilityListener {
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
            SendMessage(Gson().toJson(message),"${Constants.XMPP_NUMBER_2}_a")
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

    private lateinit var binding:ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_chat)
        val vm:MessageItemViewModel by lazy {
            ViewModelProviders.of(this, ViewModelFactory { MessageItemViewModel(this) }).get(MessageItemViewModel::class.java)
        }
        send_btn.setOnClickListener(this)
        Constants.setKeyboardVisibilityListener(this)
        binding.viewModel=vm
    }
}
