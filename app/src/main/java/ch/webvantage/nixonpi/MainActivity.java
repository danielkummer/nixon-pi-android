package ch.webvantage.nixonpi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.adapter.TabViewPagerAdapter;
import ch.webvantage.nixonpi.communication.DefaultCallback;
import ch.webvantage.nixonpi.communication.DiscoverService_;
import ch.webvantage.nixonpi.communication.PowerService;
import ch.webvantage.nixonpi.communication.RestUtil;
import ch.webvantage.nixonpi.communication.model.Power;
import ch.webvantage.nixonpi.event.DiscoveryEvent;
import ch.webvantage.nixonpi.event.NetworkStateEvent;
import ch.webvantage.nixonpi.ui.widget.SlidingTabLayout;
import ch.webvantage.nixonpi.util.ConnectivityUtil;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.widget.CompoundButton.OnCheckedChangeListener;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @App
    NixonPiApplication app;


    @ViewById(R.id.tabPager)
    ViewPager pager;

    @ViewById
    SlidingTabLayout tabs;

    @Bean
    RestUtil restUtil;


    Switch powerToggle;


    TabViewPagerAdapter adapter;

    private PowerService powerService;

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

    private void initServices() {
        powerService = restUtil.buildService(PowerService.class);
    }

    @AfterViews
    @DebugLog
    protected void afterViews() {

        adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setDistributeEvenly(true);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);
        EventBus.getDefault().post(ConnectivityUtil.getNetworkState(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(ch.webvantage.nixonpi.R.menu.menu_main, menu);
        final MenuItem powerToggleMenuItem = menu.findItem(R.id.action_switch_power);

        final Context context = this;

        powerToggle = (Switch) powerToggleMenuItem.getActionView().findViewById(R.id.togglePower);
        powerToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            @DebugLog
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {

                int value = isChecked ? 1 : 0;

                powerService.setPower(value, new DefaultCallback<Power>(context) {
                    @Override
                    public void success(Power power, Response response) {
                        Snackbar
                                .make(pager, "Set power to " + power.toString(), Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });

        setInputEnabled(false);
        return super.onCreateOptionsMenu(menu);
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
        if (event.hasServers()) {
            app.setServer(event.getFirstServer());
            setInputEnabled(true);
            initServices();
        } else {
            retryDialog.show();
            setInputEnabled(false);
        }
    }

    private void setInputEnabled(boolean enabled) {
        if (powerToggle != null) {
            powerToggle.setEnabled(enabled);
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
