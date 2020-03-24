package com.sourabh.chsatplace.interfaces

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sourabh.chsatplace.common.Constants
import com.sourabh.chsatplace.common.XMPPConnectionSingletone
import com.sourabh.chsatplace.utilities.Logger

class ChatSyncWorker constructor(appContext: Context,workerParams:WorkerParameters):Worker(appContext,workerParams) {
    override fun doWork(): Result {
        Logger.verbose("work started")
            XMPPConnectionSingletone.getInstance().setSecondayConnection(Constants.XMPP_NUMBER)


        return Result.success()
    }
}