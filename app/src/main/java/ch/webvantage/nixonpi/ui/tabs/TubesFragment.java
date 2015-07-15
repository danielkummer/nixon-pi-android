package ch.webvantage.nixonpi.ui.tabs;

import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.Constants;
import ch.webvantage.nixonpi.R;
import ch.webvantage.nixonpi.communication.DefaultCallback;
import ch.webvantage.nixonpi.communication.model.Background;
import ch.webvantage.nixonpi.communication.model.Tubes;
import hugo.weaving.DebugLog;
import retrofit.client.Response;

import static ch.webvantage.nixonpi.Constants.States.FREE_VALUE;

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
    void onBarSeekBarChange(final SeekBar seekBar, int progress) {
        backgroundService.setBackground(progress, new DefaultCallback<Background>(getActivity()) {
            @Override
            public void success(Background background, Response response) {
                Snackbar
                        .make(seekBar.getRootView(), "Set background to " + background.toString(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @AfterTextChange(R.id.tubeEditText)
    void onTubeTextChange(final TextView view, Editable text) {
        //TODO unclean - why is this null?
        if (tubesService != null) {
            tubesService.setTubes(text.toString(), FREE_VALUE, new DefaultCallback<Tubes>(getActivity()) {
                @Override
                public void success(Tubes tubes, Response response) {
                    Snackbar
                            .make(view, "Set tubes to " + tubes.toString(), Snackbar.LENGTH_SHORT)
                            .show();
                }
            });

        }
    }

}