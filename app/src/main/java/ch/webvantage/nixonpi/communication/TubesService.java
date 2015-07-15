package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Power;
import ch.webvantage.nixonpi.communication.model.Tubes;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by dkummer on 22/06/15.
 */
public interface TubesService {

    @GET("/information/tubes.json")
    void getTubes(Callback<Tubes> callback);

    @POST("/tubes")
    @FormUrlEncoded
    void setTubes(@Field("value") String value, @Field("state") String state, Callback<Tubes> callback);
    //void setTubes(@Body Tubes tubes, Callback<Tubes> callback);

}
