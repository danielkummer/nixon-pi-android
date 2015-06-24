package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Power;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dkummer on 22/06/15.
 */
public class CommunicationService {


    void getPower() {
        //TODO this is an example!

        //TODO read api url from settings
        PowerService powerService = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://192.168.1.1").build()
                .create(PowerService.class);

        powerService.getPower(new Callback<Power>() {
            @Override
            public void success(Power power, Response response) {

            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }

        });
    }


}
