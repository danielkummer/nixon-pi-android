package ch.webvantage.nixonpi.communication.model;

/**
 * Created by dkummer on 06/07/15.
 */
public class Lamp {

    private int value;
    private String state = "free_value";
    private int id;


    public Lamp(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public Lamp(int id,  boolean value) {
        this.id = id;
        this.value = value ? 1 : 0;
    }

    public boolean isOn() {
        return value == 1;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return value == 1 ? "on" : "off";
    }
}
