package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Background;
import ch.webvantage.nixonpi.communication.model.Lamp;
import ch.webvantage.nixonpi.communication.model.Power;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by dkummer on 22/06/15.
 */
public interface LampService {

    //@GET("/information/lamps.json")
    //void getBackground(Callback<Background> callback);

    @POST("/lamp")
    @FormUrlEncoded
    void setLamp(@Field("id") int id,@Field("value") int value, @Field("state") String state, Callback<Lamp> callback);
    //void setLamp(@Body Lamp lamp, Callback<Lamp> callback);

}
