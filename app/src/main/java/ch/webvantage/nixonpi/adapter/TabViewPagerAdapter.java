package ch.webvantage.nixonpi.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ch.webvantage.nixonpi.ui.tabs.AmbientFragment_;
import ch.webvantage.nixonpi.ui.tabs.BarsFragment_;
import ch.webvantage.nixonpi.ui.tabs.LampsFragment_;
import ch.webvantage.nixonpi.ui.tabs.TubesFragment_;


/**
 * Created by dkummer on 10/07/15.
 */
public class TabViewPagerAdapter extends FragmentStatePagerAdapter {

    public enum TabFragments {
        TUBES_FRAGMENT("Tubes"),
        LAMPS_FRAGMENT("Lamps"),
        BARS_FRAGMENT("Bars"),
        AMBIENT_FRAGMENT("Other");

        private String title;

        TabFragments(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    int tabsCount; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.tabsCount = TabFragments.values().length;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        Fragment tab = null;

        TabFragments fragmentPosition = TabFragments.values()[position];

        switch (fragmentPosition) {
            case TUBES_FRAGMENT:
                tab  = new TubesFragment_();
                break;
            case LAMPS_FRAGMENT:
                tab = new LampsFragment_();
                break;
            case BARS_FRAGMENT:
                tab = new BarsFragment_();
                break;
            case AMBIENT_FRAGMENT:
                tab = new AmbientFragment_();
                break;
        }

        return tab;

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return TabFragments.values()[position].getTitle();
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return tabsCount;
    }
}