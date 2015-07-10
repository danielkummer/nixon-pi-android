package ch.webvantage.nixonpi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ch.webvantage.nixonpi.event.NetworkStateEvent;
import hugo.weaving.DebugLog;

/**
 * Created by dkummer on 02/07/15.
 */
public class ConnectivityUtil {

    @DebugLog
    public static NetworkStateEvent getNetworkState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return new NetworkStateEvent(netInfo.isConnected(), netInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

}
