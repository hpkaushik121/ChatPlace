package com.sourabh.chsatplace.dataBinding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.sourabh.chsatplace.adapter.ChatMessageAdapter
import com.sourabh.chsatplace.respository.ChatEntityModel

class RecyclerViewBindingAdapter {

    companion object{
        @BindingAdapter("app:adapter", "app:data")
        @JvmStatic
        fun bind(recyclerView: RecyclerView, adapter: ChatMessageAdapter, data: List<ChatEntityModel>) {
            recyclerView.adapter = adapter
            adapter.updateData(data)
        }
    }



}