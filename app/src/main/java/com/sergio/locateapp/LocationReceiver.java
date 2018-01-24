package com.sergio.locateapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Sergio on 23/01/18.
 */

public class LocationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {

            Log.d("LocationReceiver", "Sí");
            Log.d("Latitude", ":" +extras.getString("Latitude"));
            Log.d("Longitude", ":" +extras.getString("Longitude"));
            Log.d("Provider", ":" +extras.getString("Provider"));

        }

    }

}
