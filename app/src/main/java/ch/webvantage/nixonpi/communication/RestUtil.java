package ch.webvantage.nixonpi.communication;

import android.util.Log;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import ch.webvantage.nixonpi.NixonPiApplication;
import ch.webvantage.nixonpi.communication.model.Power;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dkummer on 22/06/15.
 */
@EBean
public class RestUtil {

    @App
    NixonPiApplication app;

    private static final String TAG = DiscoverService.class.getName();

    public <T> T buildService(Class<T> serviceInterface) throws RuntimeException {
        if(app.getServer() == null) {
           throw new RuntimeException("Remote server is null");
        }

        T service = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://" + app.getServer().toString())
                .build()
                .create(serviceInterface);
        return service;
    }

/*
    void test() {
        PowerService service = buildService(PowerService.class);
    }

    void getPower() {
        //TODO this is an example!

        PowerService powerService = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://10.0.2.2:8080").build()
                .create(PowerService.class);

        powerService.getPower(new Callback<Power>() {
            @Override
            public void success(Power power, Response response) {
                    Log.d(TAG, response.toString());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "error", retrofitError);
            }

        });
    }
*/

}
