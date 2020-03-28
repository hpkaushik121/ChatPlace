package com.sourabh.chsatplace.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.sourabh.chsatplace.databinding.MessageLayoutBinding
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.ui.viewModels.MessageItemViewModel
import kotlinx.android.synthetic.main.message_layout.view.*
import kotlinx.android.synthetic.main.message_receive_layout.view.*
import kotlinx.android.synthetic.main.message_send_layout.view.*

import android.text.format.DateFormat
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.R
import com.sourabh.chsatplace.pojo.MessageModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatMessageAdapter  :
    RecyclerView.Adapter<ChatMessageAdapter.ViewHolder>() {
    private  var chatData= ArrayList<ChatEntityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.message_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = chatData[position]
        var mDate = item.KEY_TIMESTAMP
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
        val messageModel = Gson().fromJson(item.KEY_MESSAGE, MessageModel::class.java)

        if (item.KEY_MSG_SEND) {
            holder.item.message_rcv.visibility = View.GONE
            holder.item.message_send.visibility = View.VISIBLE
            holder.item.msgSnd.text = messageModel.message
            holder.item.timeStamp.text = mDate
            when (item.KEY_STATUS) {
                0-> holder.item.msg_status.setImageDrawable(ChatApplication.getInstance().resources.getDrawable(R.drawable.ic_message_waiting))
                1 -> holder.item.msg_status.setImageDrawable(ChatApplication.getInstance().resources.getDrawable(R.drawable.ic_message_sent))
                2 -> holder.item.msg_status.setImageDrawable(ChatApplication.getInstance().resources.getDrawable(R.drawable.ic_message_delivered))
                3 -> holder.item.msg_status.setImageDrawable(ChatApplication.getInstance().resources.getDrawable(R.drawable.ic_message_read))
            }

        } else {
            holder.item.message_rcv.visibility = View.VISIBLE
            holder.item.message_send.visibility = View.GONE
            holder.item.msgrc3v.text = messageModel.message
            holder.item.timeStampRcv.text = mDate
        }
    }

    fun updateData(data: List<ChatEntityModel>) {
        chatData = data as ArrayList<ChatEntityModel>
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item = itemView
    }


}