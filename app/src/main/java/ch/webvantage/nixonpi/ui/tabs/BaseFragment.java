package ch.webvantage.nixonpi.ui.tabs;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import ch.webvantage.nixonpi.communication.AmbientService;
import ch.webvantage.nixonpi.communication.BackgroundService;
import ch.webvantage.nixonpi.communication.BarsService;
import ch.webvantage.nixonpi.communication.LampService;
import ch.webvantage.nixonpi.communication.RestUtil;
import ch.webvantage.nixonpi.communication.TubesService;
import ch.webvantage.nixonpi.event.DiscoveryEvent;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by dkummer on 14/07/15.
 */
@EFragment
public abstract class BaseFragment extends Fragment {

    @Bean
    RestUtil restUtil;

    protected BackgroundService backgroundService;
    protected TubesService tubesService;
    protected BarsService barService;
    protected LampService lampService;
    protected AmbientService ambientService;

    protected void initServices() {
        backgroundService = restUtil.buildService(BackgroundService.class);
        tubesService = restUtil.buildService(TubesService.class);
        barService = restUtil.buildService(BarsService.class);
        lampService = restUtil.buildService(LampService.class);
        ambientService = restUtil.buildService(AmbientService.class);
    }

    abstract void setInputEnabled(boolean enabled);

    @DebugLog
    public void onEventMainThread(DiscoveryEvent event) {
        if (event.hasServers()) {
            setInputEnabled(true);
            initServices();
        } else {
            setInputEnabled(false);
        }

    }

    @AfterViews
    @DebugLog
    void calledAfterViewInjection() {
        EventBus.getDefault().register(this); // register EventBus
        setInputEnabled(false);
    }
}
