package com.ptp2.hueapp.model;

public class Light {

    private String name;
    private int saturation;
    private int brightness;
    private int hue;

    private int index;
    private boolean turnedOn;

    public Light(String name, boolean turnedOn, int saturation, int brightness, int hue) {
        this.name = name;
        this.turnedOn = turnedOn;
        this.saturation = saturation;
        this.brightness = brightness;
        this.hue = hue;
    }

    public void setColor(int saturation, int brightness, int value) {

        this.saturation = saturation;
        this.brightness = brightness;
        this.hue = value;
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

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
