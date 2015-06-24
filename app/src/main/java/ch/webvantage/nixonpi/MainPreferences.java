package ch.webvantage.nixonpi;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by dkummer on 23/06/15.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface MainPreferences {

    @DefaultString("10.0.2.2:8080")
    String apiEndpoint();

}
