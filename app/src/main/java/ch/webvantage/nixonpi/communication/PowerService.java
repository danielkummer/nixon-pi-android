package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Power;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by dkummer on 22/06/15.
 */
public interface PowerService {

    @GET("/information/power.json")
    void getPower(Callback<Power> callback);

    @POST("/power.json")
    Power setPower(@Body Power power);

}
