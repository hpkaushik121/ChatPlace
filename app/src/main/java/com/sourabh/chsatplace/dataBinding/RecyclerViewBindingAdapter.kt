package com.sourabh.chsatplace.dataBinding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.sourabh.chsatplace.adapter.ChatListAdapter
import com.sourabh.chsatplace.adapter.ChatMessageAdapter
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.respository.ChatsView

class RecyclerViewBindingAdapter {

    companion object{
        @BindingAdapter("app:adapter", "app:data")
        @JvmStatic
        fun bind(recyclerView: RecyclerView, adapter: ChatMessageAdapter, data: List<ChatEntityModel>) {
            recyclerView.adapter = adapter
            adapter.updateData(data)
        }
        @BindingAdapter("app:chatAdapter", "app:chatData")
        @JvmStatic
        fun bind(recyclerView: RecyclerView, adapter: ChatListAdapter, data: List<ChatsView>) {
            recyclerView.adapter = adapter
            adapter.updateData(data)
        }
    }



}