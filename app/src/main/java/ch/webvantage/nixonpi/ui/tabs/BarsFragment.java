package ch.webvantage.nixonpi.ui.tabs;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.SeekBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.Constants;
import ch.webvantage.nixonpi.R;
import ch.webvantage.nixonpi.communication.DefaultCallback;
import ch.webvantage.nixonpi.communication.model.Bar;
import ch.webvantage.nixonpi.communication.model.Lamp;
import hugo.weaving.DebugLog;
import retrofit.Callback;
import retrofit.client.Response;

import static ch.webvantage.nixonpi.Constants.States.FREE_VALUE;

@EFragment(R.layout.tab_bars)
public class BarsFragment extends BaseFragment {

    private static final String TAG = BarsFragment.class.getName();


    @ViewById
    SeekBar seekBar0;

    @ViewById
    SeekBar seekBar1;

    @ViewById
    SeekBar seekBar2;

    @ViewById
    SeekBar seekBar3;

    private Callback<Bar> callback;


    @Override
    void setInputEnabled(boolean enabled) {
        seekBar0.setEnabled(enabled);
        seekBar1.setEnabled(enabled);
        seekBar2.setEnabled(enabled);
        seekBar3.setEnabled(enabled);
    }

    @AfterViews
    public void afterViews() {
        callback = new DefaultCallback<Bar>(getActivity()) {
            @Override
            public void success(Bar bar, Response response) {
                Snackbar
                        .make(getView(), "Set tubes to " + bar.toString(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        };
    }

    @DebugLog
    @SeekBarProgressChange({
            R.id.seekBar0,
            R.id.seekBar1,
            R.id.seekBar2,
            R.id.seekBar3})
    void onBarSeekBarChange(SeekBar seekBar, int progress) {
        switch (seekBar.getId()) {
            case R.id.seekBar0:
                barService.setBar(0, progress, FREE_VALUE, callback);
                break;
            case R.id.seekBar1:
                barService.setBar(1, progress,FREE_VALUE, callback);
                break;
            case R.id.seekBar2:
                barService.setBar(2, progress,FREE_VALUE, callback);
                break;
            case R.id.seekBar3:
                barService.setBar(3, progress,FREE_VALUE, callback);
                break;
            default:
                Log.e(TAG, "Seekbar not found!");
        }
    }
}