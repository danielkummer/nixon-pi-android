package ch.webvantage.nixonpi.ui.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.R;
import ch.webvantage.nixonpi.communication.model.Lamp;
import hugo.weaving.DebugLog;


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


    @Override
    void setInputEnabled(boolean enabled) {
        toggleButtonLamp0.setEnabled(enabled);
        toggleButtonLamp1.setEnabled(enabled);
        toggleButtonLamp2.setEnabled(enabled);
        toggleButtonLamp3.setEnabled(enabled);
        toggleButtonLamp4.setEnabled(enabled);
    }

    @DebugLog
    @CheckedChange({R.id.toggleButtonLamp0, R.id.toggleButtonLamp1, R.id.toggleButtonLamp2, R.id.toggleButtonLamp3, R.id.toggleButtonLamp4})
    void onToggle(CompoundButton button,  boolean isChecked) {
        switch (button.getId()) {
            case R.id.toggleButtonLamp0:
                lampService.setLamp(new Lamp(isChecked), 0);
                break;
            case R.id.toggleButtonLamp1:
                lampService.setLamp(new Lamp(isChecked), 1);
                break;
            case R.id.toggleButtonLamp2:
                lampService.setLamp(new Lamp(isChecked), 2);
                break;
            case R.id.toggleButtonLamp3:
                lampService.setLamp(new Lamp(isChecked), 3);
                break;
            case R.id.toggleButtonLamp4:
                lampService.setLamp(new Lamp(isChecked), 4);
                break;
        }
    }
}