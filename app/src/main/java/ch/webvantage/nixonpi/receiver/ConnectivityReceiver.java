package ch.webvantage.nixonpi.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ch.webvantage.nixonpi.event.NetworkStateChangedEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by dkummer on 22/06/15.
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    // post event if there is no Internet connection
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        EventBus.getDefault().post(new NetworkStateChangedEvent(netInfo.isConnected(), netInfo.getType() == ConnectivityManager.TYPE_WIFI));
    }
}