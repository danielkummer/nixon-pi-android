package ch.webvantage.nixonpi.communication.model;

/**
 * Created by dkummer on 06/07/15.
 */
public class Background {

    private int value;

    public Background(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String brightness;

        if (value == 0) {
            brightness = "off";
        } else if (value <= 64) {
            brightness = "very dim";
        } else if (value <= 128) {
            brightness = "dim";
        } else if (value <= 192) {
            brightness = "bright";
        } else {
            brightness = "very bright";
        }
        return brightness;
    }
}
