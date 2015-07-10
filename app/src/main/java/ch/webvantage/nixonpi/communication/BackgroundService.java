package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Background;
import ch.webvantage.nixonpi.communication.model.Power;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by dkummer on 22/06/15.
 */
public interface BackgroundService {

    @GET("/information/background.json")
    void getBackground(Callback<Background> callback);

    @POST("/background.json")
    Power setBackground(@Body Background background);

}
