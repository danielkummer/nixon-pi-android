package ch.webvantage.nixonpi.communication.model;

/**
 * Created by dkummer on 06/07/15.
 */
public class Bar {

    private int id;
    private int value;
    private String state = "free_value";

    public Bar(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
