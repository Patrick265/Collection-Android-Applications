package com.ptp2.hueapp.data;

import com.ptp2.hueapp.model.Light;

import java.util.ArrayList;
import java.util.List;

public class LightData {

    private static LightData lightData;
    private List<Light> unAssignedLights;
    private List<Light> kitchenLights;
    private List<Light> bedroomLights;
    private List<Light> livingroomLights;

    private LightData() {
        this.unAssignedLights= new ArrayList<>();
        this.kitchenLights= new ArrayList<>();
        this.bedroomLights = new ArrayList<>();
        this.livingroomLights = new ArrayList<>();
    }

    public static LightData getInstance() {
        if(lightData == null){
            lightData = new LightData();
        }
        return lightData;
    }

    public List<Light> getUnAssignedLights() {
        return unAssignedLights;
    }

    public List<Light> getKitchenLights() {
        return kitchenLights;
    }

    public List<Light> getBedroomLights() {
        return bedroomLights;
    }

    public List<Light> getLivingroomLights() {
        return livingroomLights;
    }
}


