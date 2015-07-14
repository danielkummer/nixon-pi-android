package ch.webvantage.nixonpi.ui.tabs;

import android.support.v4.app.Fragment;

import com.commonsware.cwac.colormixer.ColorMixer;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.R;
import ch.webvantage.nixonpi.communication.model.AmbientColor;

@EFragment(R.layout.tab_ambient)
public class AmbientFragment extends BaseFragment implements ColorMixer.OnColorChangedListener {

    @ViewById
    ColorMixer mixerAmbientLight;

    @Override
    void setInputEnabled(boolean enabled) {
        mixerAmbientLight.setEnabled(enabled);
    }

    @Override
    public void onColorChange(int color) {
        ambientService.setAmbientColor(new AmbientColor(color));
    }
}