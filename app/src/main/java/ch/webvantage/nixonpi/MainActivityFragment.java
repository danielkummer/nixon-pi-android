package ch.webvantage.nixonpi;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;


/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_main)
@OptionsMenu(R.menu.menu_main)
public class  MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }


    @OptionsItem(R.id.action_refresh)
    void actionRefresh() {
        //TODO refresh stats
    }

}
