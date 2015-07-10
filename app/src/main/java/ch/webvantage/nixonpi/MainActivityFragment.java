package ch.webvantage.nixonpi;

import android.app.Fragment;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.commonsware.cwac.colormixer.ColorMixer;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import ch.webvantage.nixonpi.communication.AmbientService;
import ch.webvantage.nixonpi.communication.BackgroundService;
import ch.webvantage.nixonpi.communication.BarsService;
import ch.webvantage.nixonpi.communication.LampService;
import ch.webvantage.nixonpi.communication.RestUtil;
import ch.webvantage.nixonpi.communication.PowerService;
import ch.webvantage.nixonpi.communication.TubesService;
import ch.webvantage.nixonpi.communication.model.AmbientColor;
import ch.webvantage.nixonpi.communication.model.Background;
import ch.webvantage.nixonpi.communication.model.Bar;
import ch.webvantage.nixonpi.communication.model.Lamp;
import ch.webvantage.nixonpi.communication.model.Power;
import ch.webvantage.nixonpi.communication.model.Tubes;
import ch.webvantage.nixonpi.event.DiscoveryEvent;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;


/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_main)
public class MainActivityFragment extends Fragment implements ColorMixer.OnColorChangedListener {

    private static final String TAG = MainActivityFragment.class.getName();

    @ViewById
    TextView textViewConnectedTo;

    @ViewById
    ToggleButton togglePower;

    @ViewById
    EditText tubeEditText;

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

    @ViewById
    SeekBar seekBarBackground;

    @ViewById
    SeekBar seekBar0;

    @ViewById
    SeekBar seekBar1;

    @ViewById
    SeekBar seekBar2;

    @ViewById
    SeekBar seekBar3;

    @ViewById
    ColorMixer mixerAmbientLight;


    @Bean
    RestUtil restUtil;

    private PowerService powerService;
    private BackgroundService backgroundService;
    private TubesService tubesService;
    private BarsService barService;
    private LampService lampService;
    private AmbientService ambientService;

    public MainActivityFragment() {
    }

    private void initServices() {
        powerService = restUtil.buildService(PowerService.class);
        backgroundService = restUtil.buildService(BackgroundService.class);
        tubesService = restUtil.buildService(TubesService.class);
        barService = restUtil.buildService(BarsService.class);
        lampService = restUtil.buildService(LampService.class);
        ambientService = restUtil.buildService(AmbientService.class);
    }

    @DebugLog
    @CheckedChange(R.id.togglePower)
    void onToggle(CompoundButton button,  boolean isChecked) {
        switch (button.getId()) {
            case R.id.togglePower:
                powerService.setPower(new Power(isChecked));
                break;
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

    @DebugLog
    @AfterTextChange(R.id.tubeEditText)
    void onTubeTextChange(CharSequence text) {
        tubesService.setTubes(new Tubes(text.toString()));
    }


    @DebugLog
    @SeekBarProgressChange({
            R.id.seekBarBackground,
            R.id.seekBar0,
            R.id.seekBar1,
            R.id.seekBar2,
            R.id.seekBar3})
    void onBarSeekBarChange(SeekBar seekBar, int progress) {
        switch (seekBar.getId()) {
            case R.id.seekBarBackground:
                backgroundService.setBackground(new Background(progress));
                break;
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

    @Override
    public void onColorChange(int color) {
        ambientService.setAmbientColor(new AmbientColor(color));
    }

    @DebugLog
    @AfterViews
    void calledAfterViewInjection() {
        EventBus.getDefault().register(this); // register EventBus
        setInputEnabled(false);
        mixerAmbientLight.setOnColorChangedListener(this);
    }

    @DebugLog
    public void onEventMainThread(DiscoveryEvent event) {
        if (event.hasServers()) {
            textViewConnectedTo.setText(event.getFirstServer().toString());
            setInputEnabled(true);
            initServices();
        } else {
            setInputEnabled(false);
        }

    }

    private void setInputEnabled(boolean enabled) {
        togglePower.setEnabled(enabled);
        tubeEditText.setEnabled(enabled);
        toggleButtonLamp0.setEnabled(enabled);
        toggleButtonLamp1.setEnabled(enabled);
        toggleButtonLamp2.setEnabled(enabled);
        toggleButtonLamp3.setEnabled(enabled);
        toggleButtonLamp4.setEnabled(enabled);
        seekBarBackground.setEnabled(enabled);
        seekBar0.setEnabled(enabled);
        seekBar1.setEnabled(enabled);
        seekBar2.setEnabled(enabled);
        seekBar3.setEnabled(enabled);
        mixerAmbientLight.setEnabled(enabled);
    }


}
