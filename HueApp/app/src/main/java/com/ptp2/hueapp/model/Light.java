package com.ptp2.hueapp.model;

import java.io.Serializable;

public class Light implements Serializable {

    private String name;
    private int saturation;
    private int brightness;
    private int hue;

    private int index;
    private int trueIndex;

    private boolean turnedOn;
    private String category;
    public Light(String name, boolean turnedOn, int saturation, int brightness, int hue, int index, int trueIndex) {
        this.name = name;
        this.turnedOn = turnedOn;
        this.saturation = saturation;
        this.brightness = brightness;
        this.hue = hue;
        this.index = index;
        this.trueIndex = trueIndex;
        this.category = "Unassigned";
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

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTrueIndex() {
        return trueIndex;
    }
}
