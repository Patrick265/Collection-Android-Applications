package com.ptp2.hueapp.model;

public class Light {


    private int saturation;
    private int brightness;
    private int value;

    private int index;
    private boolean turnedOn;

    public Light(boolean turnedOn, int saturation, int brightness, int value) {
        this.turnedOn = turnedOn;
        this.saturation = saturation;
        this.brightness = brightness;
        this.value = value;
    }

    public void setColor(int saturation, int brightness, int value) {

        this.saturation = saturation;
        this.brightness = brightness;
        this.value = value;
    }

    public void setTurnedOn(boolean turnedOn) {
        this.turnedOn = turnedOn;
    }

    public boolean isTurnedOn() {
        return turnedOn;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }
}
