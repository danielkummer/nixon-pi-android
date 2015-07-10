package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Background;
import ch.webvantage.nixonpi.communication.model.Lamp;
import ch.webvantage.nixonpi.communication.model.Power;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by dkummer on 22/06/15.
 */
public interface LampService {

    //@GET("/information/lamps.json")
    //void getBackground(Callback<Background> callback);

    @POST("/lamp/{id}.json")
    Lamp setLamp(@Body Lamp lamp, int id);

}
