package com.sourabh.chsatplace.ui.viewModels

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sourabh.chsatplace.ChatApplication
import com.sourabh.chsatplace.adapter.ChatMessageAdapter
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.network.*
import com.sourabh.chsatplace.respository.ChatEntityModel
import com.sourabh.chsatplace.respository.ChatRepository
import com.sourabh.chsatplace.utilities.Acknowledgement
import com.sourabh.chsatplace.utilities.Logger
import com.sourabh.chsatplace.utilities.Utils
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.launch
import okhttp3.internal.Util
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList

class MessageItemViewModel: ViewModel() {

    init {

    }

}