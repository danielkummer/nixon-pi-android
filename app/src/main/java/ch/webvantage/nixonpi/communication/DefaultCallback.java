package ch.webvantage.nixonpi.communication;

import android.content.Context;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dkummer on 15/07/15.
 */
public abstract class DefaultCallback<T> implements Callback<T> {

    private Context context;

    public DefaultCallback(Context context) {
        this.context = context;
    }

    @Override
    public abstract void success(T t, Response response);

    @Override
    public void failure(RetrofitError error) {
        //TODO change to snackbar error!
        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
