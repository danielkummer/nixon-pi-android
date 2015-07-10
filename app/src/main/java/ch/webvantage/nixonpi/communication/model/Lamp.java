package ch.webvantage.nixonpi.communication.model;

/**
 * Created by dkummer on 06/07/15.
 */
public class Lamp {

    private boolean value;

    public Lamp(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
