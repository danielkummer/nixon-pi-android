package ch.webvantage.nixonpi;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by dkummer on 23/06/15.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface MainPreferences {

    //No prefs at the moment
    @DefaultInt(value = 500)
    int timeout();

    @DefaultInt(value = 1234)
    int discoveryPort();

    @DefaultInt(value = 1234)
    int replyPort();

}
