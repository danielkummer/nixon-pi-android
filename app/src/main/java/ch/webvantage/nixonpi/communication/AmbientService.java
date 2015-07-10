package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.AmbientColor;
import ch.webvantage.nixonpi.communication.model.Lamp;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by dkummer on 22/06/15.
 */
public interface AmbientService {



    @POST("/rgb.json")
    AmbientColor setAmbientColor(@Body AmbientColor ambientColor);

}
