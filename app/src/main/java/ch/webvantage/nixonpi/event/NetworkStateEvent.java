package ch.webvantage.nixonpi.event;

import ch.webvantage.nixonpi.util.EmulatorUtil;

/**
 * Created by dkummer on 22/06/15.
 */
public class NetworkStateEvent {

    private boolean isInternetConnected;
    private boolean isWifi;

    public NetworkStateEvent(boolean isInternetConnected, boolean isWifi) {
        this.isInternetConnected = isInternetConnected;
        this.isWifi = isWifi;
    }

    public boolean isUsable() {
        return (isInternetConnected && isWifi) || (isInternetConnected && EmulatorUtil.isEmulator());
    }
}