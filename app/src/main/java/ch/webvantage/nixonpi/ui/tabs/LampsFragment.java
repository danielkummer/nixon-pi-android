package ch.webvantage.nixonpi.ui.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.Constants;
import ch.webvantage.nixonpi.R;
import ch.webvantage.nixonpi.communication.DefaultCallback;
import ch.webvantage.nixonpi.communication.model.Lamp;
import hugo.weaving.DebugLog;
import retrofit.Callback;
import retrofit.client.Response;

import static ch.webvantage.nixonpi.Constants.States.FREE_VALUE;


@EFragment(R.layout.tab_lamps)
public class LampsFragment extends BaseFragment {

    @ViewById
    ToggleButton toggleButtonLamp0;

    @ViewById
    ToggleButton toggleButtonLamp1;

    @ViewById
    ToggleButton toggleButtonLamp2;

    @ViewById
    ToggleButton toggleButtonLamp3;

    @ViewById
    ToggleButton toggleButtonLamp4;

    private Callback<Lamp> callback;


    @Override
    void setInputEnabled(boolean enabled) {
        toggleButtonLamp0.setEnabled(enabled);
        toggleButtonLamp1.setEnabled(enabled);
        toggleButtonLamp2.setEnabled(enabled);
        toggleButtonLamp3.setEnabled(enabled);
        toggleButtonLamp4.setEnabled(enabled);
    }

    @AfterViews
    public void afterViews() {
        callback = new DefaultCallback<Lamp>(getActivity()) {
            @Override
            public void success(Lamp lamp, Response response) {
                Snackbar
                        .make(getView(), "Set lamp to " + lamp.toString(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        };
    }


    @DebugLog
    @CheckedChange({R.id.toggleButtonLamp0, R.id.toggleButtonLamp1, R.id.toggleButtonLamp2, R.id.toggleButtonLamp3, R.id.toggleButtonLamp4})
    void onToggle(CompoundButton button, boolean isChecked) {

        int value = isChecked ? 1 : 0;

        switch (button.getId()) {
            case R.id.toggleButtonLamp0:
                lampService.setLamp(0, value, FREE_VALUE, callback);
                break;
            case R.id.toggleButtonLamp1:
                lampService.setLamp(1, value, FREE_VALUE,callback);
                break;
            case R.id.toggleButtonLamp2:
                lampService.setLamp(2, value, FREE_VALUE,callback);
                break;
            case R.id.toggleButtonLamp3:
                lampService.setLamp(3, value, FREE_VALUE,callback);
                break;
            case R.id.toggleButtonLamp4:
                lampService.setLamp(4, value, FREE_VALUE,callback);
                break;
        }
    }
}