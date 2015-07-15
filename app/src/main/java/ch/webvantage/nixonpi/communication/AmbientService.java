package ch.webvantage.nixonpi.communication;

import ch.webvantage.nixonpi.communication.model.AmbientColor;
import ch.webvantage.nixonpi.communication.model.Lamp;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by dkummer on 22/06/15.
 */
public interface AmbientService {



    @POST("/rgb")
    @FormUrlEncoded
    void setAmbientColor(@Field("value") String value, Callback<AmbientColor> callback);

}
