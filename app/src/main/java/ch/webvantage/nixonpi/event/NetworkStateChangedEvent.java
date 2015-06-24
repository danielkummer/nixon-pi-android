package ch.webvantage.nixonpi.event;

/**
 * Created by dkummer on 22/06/15.
 */
public class NetworkStateChangedEvent {

    private boolean isInternetConnected;
    private boolean isWifi;

    public NetworkStateChangedEvent(boolean isInternetConnected, boolean isWifi) {
        this.isInternetConnected = isInternetConnected;
        this.isWifi = isWifi;
    }

    public boolean isInternetConnected() {
        return this.isInternetConnected;
    }

    public boolean isWifi() {
        return isWifi;
    }
}