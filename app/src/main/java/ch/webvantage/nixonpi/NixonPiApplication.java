package ch.webvantage.nixonpi;

import android.app.Application;
import android.content.SharedPreferences;

import org.androidannotations.annotations.EApplication;

import ch.webvantage.nixonpi.communication.model.NixonpiServer;

import static ch.webvantage.nixonpi.Constants.Preferences.*;

/**
 * Created by dkummer on 22/06/15.
 */

@EApplication
public class NixonPiApplication extends Application {

    NixonpiServer server;

    @Override
    public void onCreate() {
        super.onCreate();
        server = NixonpiServer.createDummy();
    }

    public NixonpiServer getServer() {
        return server;
    }

    public void setServer(NixonpiServer server) {
        this.server = server;
    }
}
