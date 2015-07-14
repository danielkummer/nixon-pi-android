package ch.webvantage.nixonpi.ui.tabs;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.SeekBar;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.R;
import ch.webvantage.nixonpi.communication.model.Bar;
import hugo.weaving.DebugLog;

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


    @Override
    void setInputEnabled(boolean enabled) {
        seekBar0.setEnabled(enabled);
        seekBar1.setEnabled(enabled);
        seekBar2.setEnabled(enabled);
        seekBar3.setEnabled(enabled);
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
                barService.setBar(new Bar(progress), 0);
                break;
            case R.id.seekBar1:
                barService.setBar(new Bar(progress), 1);
                break;
            case R.id.seekBar2:
                barService.setBar(new Bar(progress), 2);
                break;
            case R.id.seekBar3:
                barService.setBar(new Bar(progress), 3);
                break;
            default:
                Log.e(TAG, "Seekbar not found!");
        }
    }
}