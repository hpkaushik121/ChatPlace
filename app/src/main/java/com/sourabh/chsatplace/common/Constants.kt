package com.sourabh.chsatplace.common

import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.app.Activity
import android.view.View
import com.sourabh.chsatplace.interfaces.KeyboardVisibilityListener


class Constants {
    companion object{
        const val XMPP_COMMON_PASSWORD="1234"
        const val XMPP_SERVER_IP="103.248.34.62"
        const val XMPP_DOMAIN_NAME="hostingengine.tech"
        const val XMPP_NUMBER="1234567890" // from number
        const val XMPP_NUMBER_2="7838941249" //to number
        var isScrollDueToAdd=true

        fun setKeyboardVisibilityListener(
            activity: Activity
        ) {
            val contentView = activity.findViewById<View>(android.R.id.content)

            contentView.viewTreeObserver
                .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    private var mPreviousHeight: Int = 0

                    override fun onGlobalLayout() {
                        try {
                            val newHeight = contentView.height
                            if (mPreviousHeight != 0) {
                                if (mPreviousHeight > newHeight) {
                                    // Height decreased: keyboard was shown
                                    (activity as KeyboardVisibilityListener).onKeyboardVisibilityChanged(true)
                                } else if (mPreviousHeight < newHeight) {
                                    // Height increased: keyboard was hidden
                                    (activity as KeyboardVisibilityListener).onKeyboardVisibilityChanged(false)
                                } else {
                                    // No change
                                }
                            }
                            mPreviousHeight = newHeight
                        }catch (e:Exception){
                            e.printStackTrace()
                        }

                    }
                })
        }
    }
}