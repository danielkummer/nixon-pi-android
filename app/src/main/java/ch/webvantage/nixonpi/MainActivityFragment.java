package ch.webvantage.nixonpi;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import hugo.weaving.DebugLog;


/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_main)
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @DebugLog
    @Click(R.id.togglePower)
    void onPowerToggle() {
        // post in background
    }

}
