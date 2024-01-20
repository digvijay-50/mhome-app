package com.propertybuysell.mhome.Activities

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.legacy.content.WakefulBroadcastReceiver
import com.google.firebase.messaging.RemoteMessage

class FirebaseBroadcastReceiver : WakefulBroadcastReceiver() {

    val TAG: String = FirebaseBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {

        val dataBundle = intent.extras
        if (dataBundle != null)
            for (key in dataBundle.keySet()) {
                Log.d(TAG, "get_property: " + key + " : " + dataBundle.get(key))
            }
        val remoteMessage = RemoteMessage(dataBundle)
        }
    }