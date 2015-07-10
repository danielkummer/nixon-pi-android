package ch.webvantage.nixonpi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import ch.webvantage.nixonpi.communication.DiscoverService_;
import ch.webvantage.nixonpi.event.DiscoveryEvent;
import ch.webvantage.nixonpi.event.NetworkStateEvent;
import ch.webvantage.nixonpi.util.ConnectivityUtil;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;


@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    @App
    NixonPiApplication app;

    private Dialog noNetworkDialog;
    private ProgressDialog discoverProgressDialog;
    private AlertDialog retryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        discoverProgressDialog = createDiscoverDialog();
        noNetworkDialog = createNoNetworkDialog();
        retryDialog = createRetryDialog();

        EventBus.getDefault().register(this);
    }

    @AfterViews
    @DebugLog
    protected void afterViews() {
        EventBus.getDefault().post(ConnectivityUtil.getNetworkState(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); // unregister EventBus
    }

    @OptionsItem(R.id.action_discover)
    void actionDiscover() {
        startDiscovery();
    }

    @OptionsItem(R.id.action_refresh)
    void actionRefresh() {
        //TODO
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    @OptionsItem(R.id.action_settings)
    void actionSettings() {
        final Intent intent = new Intent(this, SettingsActivity_.class);
        startActivity(intent);
    }


    @DebugLog
    public void onEventMainThread(NetworkStateEvent event) {
        if (event.isUsable()) {
            if (noNetworkDialog.isShowing()) {
                noNetworkDialog.cancel();
            }
        } else {
            noNetworkDialog.show();
            if (discoverProgressDialog.isShowing()) {
                discoverProgressDialog.dismiss();
            }
        }
    }

    @DebugLog
    public void onEventMainThread(DiscoveryEvent event) {
        if (discoverProgressDialog.isShowing()) {
            discoverProgressDialog.dismiss();
        }
        if (event.getServers().isEmpty()) {
            retryDialog.show();
        } else {
            app.setServer(event.getServers().get(0));
        }
    }

    private void startDiscovery() {
        if (ConnectivityUtil.getNetworkState(this).isUsable()) {
            DiscoverService_.intent(this).discover().start();
            discoverProgressDialog.show();
        } else {
            noNetworkDialog.show();
        }
    }

    private ProgressDialog createDiscoverDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.setTitle("Searching Nixon-\u03C0");
        return dialog;
    }

    private AlertDialog createNoNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Oops")
                .setMessage("No WIFI connection, please check...")
                .setCancelable(true)
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                        startActivity(intent);
                    }
                });
        return builder.create();
    }

    private AlertDialog createRetryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Discovery failed")
                .setMessage("Nixon-\u03C0 not found - retry?")
                .setCancelable(true)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startDiscovery();
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
