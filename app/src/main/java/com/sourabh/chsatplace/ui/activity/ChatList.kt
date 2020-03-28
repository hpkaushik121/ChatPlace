package com.sourabh.chsatplace.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.R
import com.sourabh.chsatplace.adapter.ChatListAdapter
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.factory.ViewModelFactory
import com.sourabh.chsatplace.respository.ChatsView
import com.sourabh.chsatplace.ui.viewModels.ChatListViewModel
import kotlinx.android.synthetic.main.chat_list_layout.*

class ChatList :AppCompatActivity(){

    private val adapter=ChatListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.chat_list_layout)
        val vm: ChatListViewModel by lazy {
            ViewModelProviders.of(this, ViewModelFactory { ChatListViewModel() }).get(
                ChatListViewModel::class.java)
        }
        chat_list.adapter=adapter
        ChatApplication.getInstance().chatRepository
            .getMessageViewLIst()
            .observe(this, Observer {
                adapter.updateData(it)
                if(it.isNotEmpty()){
                    error.visibility= View.GONE
                }
            })
        adapter.setOnClickListener(object :ChatListAdapter.onItemClickListener{
            override fun onClick(view: View, position: Int) {
                Constants.XMPP_NUMBER_2=adapter.chatList[position].username
                startActivity(Intent(this@ChatList,ChatActivity::class.java))
            }

        })

    }
}