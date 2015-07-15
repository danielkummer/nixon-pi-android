package ch.webvantage.nixonpi.communication.model;

/**
 * Created by dkummer on 22/06/15.
 */
public class Power {

    private int value;

    public Power(int value) {
        this.value = value;
    }

    public Power(boolean value) {
        this.value = value ? 1 : 0;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean hasPower() {
        return value == 1;
    }

    @Override
    public String toString() {
        return value == 1 ? "on" : "off";
    }
}
