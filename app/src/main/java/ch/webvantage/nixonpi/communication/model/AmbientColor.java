package ch.webvantage.nixonpi.communication.model;

import android.graphics.Color;

/**
 * Created by dkummer on 06/07/15.
 */
public class AmbientColor {

    String value;

    public AmbientColor(String value) {
        this.value = value;
    }

    public AmbientColor(int color) {
        //TODO convert color to hex string without alpha channel
        value = String.format("#%2d%2d%2d", Color.red(color), Color.green(color), Color.blue(color));
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}