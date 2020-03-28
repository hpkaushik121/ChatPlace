package com.sourabh.chsatplace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.R
import com.sourabh.chsatplace.pojo.MessageModel
import com.sourabh.chsatplace.respository.ChatsView
import kotlinx.android.synthetic.main.chat_list_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {
    public var chatList=ArrayList<ChatsView>()
    private lateinit var clickListener: onItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item,parent,false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item=chatList[position]
        holder.item.from.text=item.username
        val model= Gson().fromJson(item.lastMsg,MessageModel::class.java)
        holder.item.last_msg.text=model.message
        var mDate = item.lastMsgTime
        val format = SimpleDateFormat("dd MMM, yyyy h:mm:ss a")
        val format2 = SimpleDateFormat("hh:mm a")
        try {

            format.timeZone = TimeZone.getTimeZone("UTC")
            val newDate = format.parse(mDate)
            format2.timeZone = TimeZone.getDefault()
            mDate = format2.format(newDate)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        holder.item.chat_time.text=mDate
        if(item.totalNewMsgCount .toInt()>0){
            holder.item.chat_count.text=item.totalNewMsgCount
            holder.item.chat_time.setTextColor(ChatApplication.getInstance().resources.getColor(R.color.green))
            holder.item.ll_chat_count.visibility=View.VISIBLE
        }else{
            holder.item.ll_chat_count.visibility=View.GONE
            holder.item.chat_time.setTextColor(ChatApplication.getInstance().resources.getColor(R.color.default_text))
        }
        holder.item.setOnClickListener(View.OnClickListener {
            clickListener.onClick(it,position)
        })


    }


    fun updateData(data: List<ChatsView>) {
        chatList=data as ArrayList<ChatsView>
        notifyDataSetChanged()
    }

    class ChatViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
        val item=itemView
    }
    public interface onItemClickListener{
        fun onClick(view:View,position: Int)
    }
    public fun setOnClickListener(onItemClickListener: onItemClickListener){
        this.clickListener=onItemClickListener
    }
}