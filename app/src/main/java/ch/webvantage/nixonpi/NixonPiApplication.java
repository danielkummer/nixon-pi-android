package ch.webvantage.nixonpi;

import android.app.Application;
import android.content.SharedPreferences;

import org.androidannotations.annotations.EApplication;

import static ch.webvantage.nixonpi.Constants.Preferences.*;

/**
 * Created by dkummer on 22/06/15.
 */

@EApplication
public class NixonPiApplication extends Application {


    private SharedPreferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();
        this.preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
    }



    public SharedPreferences getPreferences() {
        return preferences;
    }


}
