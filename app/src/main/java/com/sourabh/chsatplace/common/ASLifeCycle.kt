package com.sourabh.chsatplace.common

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import java.lang.ref.WeakReference
import java.util.*

public class ASLifeCycle : Application.ActivityLifecycleCallbacks{

    companion object{
        final val LOG_TAG="APP"
        fun getLifeCycleLogTag(Objects:Any):String{
            return "LIFECYCLE::"+Objects.javaClass.simpleName +hashCode()
        }
    }
    enum  class AppState{
        NONE,
        FOREGROUND,
        BACKGROUND
    }
    private var mListener :WeakReference<AppLifeCycle>?=null
    private var mStartedActivityCount:Int =0
    private var mState = AppState.NONE

    override fun onActivityPaused(activity: Activity) {
        Log.v(getLifeCycleLogTag(activity), "onPause")
    }
    fun register(listener: AppLifeCycle) {
        mListener = WeakReference<AppLifeCycle>(listener)
    }

    override fun onActivityStarted(activity: Activity) {
        Log.v(getLifeCycleLogTag(activity), "onStart")
        mStartedActivityCount++
        if (mStartedActivityCount > 0)
            changeState(AppState.FOREGROUND)
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {
        Log.v(getLifeCycleLogTag(activity), "onStop")
        mStartedActivityCount--
        if (mStartedActivityCount <= 0)
            changeState(AppState.BACKGROUND)
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        Log.v(
            getLifeCycleLogTag(activity),
            "onCreate" + if (bundle == null) " [first launch]" else " [relaunch]"
        )
    }

    override fun onActivityResumed(activity: Activity) {
        Log.v(getLifeCycleLogTag(activity), "onResume")
    }
    private fun changeState(newState: AppState) {
        if (mState == newState)
            return

        mState = newState

        if (mListener!!.get() == null)
            return

        when (mState) {
            ASLifeCycle.AppState.NONE -> {
            }
            ASLifeCycle.AppState.FOREGROUND -> {
                Log.v(LOG_TAG, "onAppForegrounded")
                mListener!!.get()!!.onAppForeGround()
            }
            ASLifeCycle.AppState.BACKGROUND -> {
                Log.v(LOG_TAG, "onAppBackgrounded")
                mListener!!.get()!!.onAppBackGround()
            }
        }
    }
    public interface AppLifeCycle{
        fun onAppForeGround()
        fun onAppBackGround()
    }
}