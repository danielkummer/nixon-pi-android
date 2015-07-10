package ch.webvantage.nixonpi.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ch.webvantage.nixonpi.event.NetworkStateEvent;
import ch.webvantage.nixonpi.util.ConnectivityUtil;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by dkummer on 22/06/15.
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    // post event if there is no Internet connection
    @DebugLog
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(ConnectivityUtil.getNetworkState(context));
    }


}