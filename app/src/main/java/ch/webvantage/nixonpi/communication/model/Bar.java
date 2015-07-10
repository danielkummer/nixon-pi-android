package ch.webvantage.nixonpi.communication.model;

/**
 * Created by dkummer on 06/07/15.
 */
public class Bar {

    private int value;

    public Bar(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
