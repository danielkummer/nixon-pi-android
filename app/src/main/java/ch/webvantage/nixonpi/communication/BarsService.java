package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Bar;
import ch.webvantage.nixonpi.communication.model.Lamp;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by dkummer on 22/06/15.
 */
public interface BarsService {

    //TODO
    //@GET("/information/bars.json")
    //void getBars(Callback<Background> callback);

    @POST("/bar")
    @FormUrlEncoded
    void setBar(@Field("id") int id, @Field("value") int value, @Field("state") String state, Callback<Bar> callback);
    //void setBar(@Body Bar bar, Callback<Bar> callback);

}
