package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.Bar;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by dkummer on 22/06/15.
 */
public interface BarsService {

    //TODO
    //@GET("/information/bars.json")
    //void getBars(Callback<Background> callback);

    @POST("/bar/{id}.json")
    Bar setBar(@Body Bar bar, int id);

}
