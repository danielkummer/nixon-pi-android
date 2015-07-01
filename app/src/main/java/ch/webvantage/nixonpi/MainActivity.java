package ch.webvantage.nixonpi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import ch.webvantage.nixonpi.communication.Discoverer;
import ch.webvantage.nixonpi.event.DiscoveryEvent;
import ch.webvantage.nixonpi.event.NetworkStateChangedEvent;
import de.greenrobot.event.EventBus;


@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();


    Dialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this); // register EventBus

        new Discoverer((WifiManager) getSystemService(Context.WIFI_SERVICE)).start();



        /*Log.d(TAG, "Fetching power value");
        PowerService powerService = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://10.0.2.2:8080").build()
                .create(PowerService.class);

        powerService.getPower(new Callback<Power>() {
            @Override
            public void success(Power power, Response response) {
                    Log.d(TAG, response.toString());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "error", retrofitError);
            }

        });*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); // unregister EventBus
    }

    @OptionsItem(R.id.action_refresh)
    void actionRefresh() {
        //TODO refresh stats
    }

    @OptionsItem(R.id.action_settings)
    void actionSettings() {
        final Intent intent = new Intent(this, SettingsActivity_.class);
        startActivity(intent);
    }

    // This method will be called when a MessageEvent is posted
    public void onEventMainThread(DiscoveryEvent event) {
        Toast.makeText(this, event.getServers().toString(), Toast.LENGTH_SHORT).show();
    }

    // method that will be called when someone posts an event NetworkStateChanged
    public void onEventMainThread(NetworkStateChangedEvent event) {
        if (!event.isInternetConnected()) {
            //Toast.makeText(this, "No Internet connection!", Toast.LENGTH_SHORT).show();
            alertDialog = createDialog("No Internet connection, please check...");
            alertDialog.show();
        } else if (!event.isWifi()) {
            //Toast.makeText(this, "No WIFI connection!", Toast.LENGTH_SHORT).show();
            alertDialog = createDialog("No WIFI connection, please enable WIFI...");
            alertDialog.show();
        } else {
            alertDialog.cancel();
        }
    }


    private AlertDialog createDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Oops");

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
                        startActivity(intent);
                    }
                });/*
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });*/

        return alertDialogBuilder.create();
    }


}
