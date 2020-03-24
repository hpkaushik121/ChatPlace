package com.sourabh.chsatplace.utilities

import android.util.Log

class Logger {
    companion object{
        fun error(message:String){
            Log.e("XMPPConnection",message)
        }
        fun verbose(message: String){
            Log.v("XMPPConnection",message)
        }
        fun warngin(message: String){
            Log.w("XMPPConnection",message)
        }
    }
}