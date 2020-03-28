package com.sourabh.chsatplace.network

import androidx.lifecycle.LiveData
import com.sourabh.chsatplace.pojo.PresenceResponseModel

interface DataSource {
    val presenceResponse:LiveData<PresenceResponseModel>
    suspend fun getPresence(user:String)
}