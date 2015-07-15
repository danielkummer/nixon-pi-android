package ch.webvantage.nixonpi.ui.tabs;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.commonsware.cwac.colormixer.ColorMixer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.R;
import ch.webvantage.nixonpi.communication.DefaultCallback;
import ch.webvantage.nixonpi.communication.model.AmbientColor;
import hugo.weaving.DebugLog;
import retrofit.client.Response;

@EFragment(R.layout.tab_ambient)
public class AmbientFragment extends BaseFragment implements ColorMixer.OnColorChangedListener {

    @ViewById
    ColorMixer mixerAmbientLight;

    @Override
    void setInputEnabled(boolean enabled) {
        mixerAmbientLight.setEnabled(enabled);
    }


    @AfterViews
    void afterViews() {
        mixerAmbientLight.setOnColorChangedListener(this);
    }

    @Override
    @DebugLog
    public void onColorChange(int color) {
        ambientService.setAmbientColor(new AmbientColor(color).toString(), new DefaultCallback<AmbientColor>(getActivity()) {
            @Override
            public void success(AmbientColor ambientColor, Response response) {
                Snackbar
                        .make(getView(), "Set ambient color to " + ambientColor.toString(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }
}