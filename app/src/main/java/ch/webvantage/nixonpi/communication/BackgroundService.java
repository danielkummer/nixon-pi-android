package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Background;
import ch.webvantage.nixonpi.communication.model.Power;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by dkummer on 22/06/15.
 */
public interface BackgroundService {

    @GET("/information/background.json")
    void getBackground(Callback<Background> callback);

    @POST("/background")
    @FormUrlEncoded
    void setBackground(@Field("value") int value, Callback<Background> callback);
    //void setBackground(@Body Background background, Callback<Background> callback);

}
