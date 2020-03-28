package com.sourabh.chsatplace.network

import android.provider.Contacts
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

object Network {
    fun <T> request(
        call: Deferred<T>,
        success: ((response: T)-> Unit)?,
        error: ((throwable: Throwable)-> Unit)?= null,
        doOnSubscribe: (()-> Unit)?= null,
        doOnTerminate: (()-> Unit)?= null) {
        GlobalScope.launch(Dispatchers.Main) {
            doOnSubscribe?.invoke()
            try {
                success?.invoke(call.await())
            } catch (t: Throwable) {
                error?.invoke(t)
            } finally {
                doOnTerminate?.invoke()
            }
        }
    }
}