package ch.webvantage.nixonpi.ui.tabs;

import android.widget.EditText;
import android.widget.SeekBar;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.R;
import ch.webvantage.nixonpi.communication.model.Background;
import hugo.weaving.DebugLog;

@EFragment(R.layout.tab_tubes)
public class TubesFragment extends BaseFragment {

    @ViewById
    EditText tubeEditText;

    @ViewById
    SeekBar seekBarBackground;

    @Override
    void setInputEnabled(boolean enabled) {
        tubeEditText.setEnabled(enabled);
        seekBarBackground.setEnabled(enabled);
    }


    @DebugLog
    @SeekBarProgressChange(R.id.seekBarBackground)
    void onBarSeekBarChange(SeekBar seekBar, int progress) {
        backgroundService.setBackground(new Background(progress));
    }
}