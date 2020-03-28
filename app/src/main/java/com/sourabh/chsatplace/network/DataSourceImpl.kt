package com.sourabh.chsatplace.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sourabh.chsatplace.exception.NoConnectivityException
import com.sourabh.chsatplace.pojo.PresenceResponseModel

class DataSourceImpl(
    private  val apiInterface:ApiInterface) : DataSource {
    private val _presenceResponse=MutableLiveData<PresenceResponseModel>()
    override val presenceResponse: LiveData<PresenceResponseModel>
        get() =_presenceResponse

    override suspend fun getPresence(user: String) {
        try{
            val response=apiInterface.getUserPresence(user).await()
            _presenceResponse.postValue(response)
        }catch (e:NoConnectivityException){
            e.printStackTrace()
        }
    }
}