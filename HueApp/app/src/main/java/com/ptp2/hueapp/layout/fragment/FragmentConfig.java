package com.ptp2.hueapp.layout.fragment;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ptp2.hueapp.R;
import com.ptp2.hueapp.data.LightData;
import com.ptp2.hueapp.layout.activity.MainActivity;
import com.ptp2.hueapp.model.Light;

public class FragmentConfig {

    private static FragmentConfig manager;

    private allLights_fragment allFragment;
    private KitchenActivity kitchenFragment;
    private BedroomActivity bedroomFragment;
    private LivingRoomActivity livingRoomFragment;

    private LightData lightData;

    private FragmentConfig() {
        this.allFragment = new allLights_fragment();
        this.kitchenFragment = new KitchenActivity();
        this.bedroomFragment = new BedroomActivity();
        this.livingRoomFragment = new LivingRoomActivity();
        this.lightData = LightData.getInstance();
    }

    public static FragmentConfig getInstance() {
        if(manager == null)
        {
            manager = new FragmentConfig();
        }
        return manager;
    }

    public allLights_fragment getAllFragment() {
        return allFragment;
    }

    public KitchenActivity getKitchenFragment() {
        return kitchenFragment;
    }

    public BedroomActivity getBedroomFragment() {
        return bedroomFragment;
    }

    public LivingRoomActivity getLivingRoomFragment() {
        return livingRoomFragment;
    }

    public void insertDataAllFragment() {
        this.allFragment.getAdapter().notifyItemInserted(this.lightData.getUnAssignedLights().size() - 1);
    }

    public void insertDataKitchen() {
        this.kitchenFragment.getAdapter().notifyItemInserted(this.lightData.getKitchenLights().size() - 1);
    }

    public void insertDataBedroom() {
        this.bedroomFragment.getAdapter().notifyItemInserted(this.lightData.getBedroomLights().size() - 1);
    }

    public void insertDataLivingRoom() {
        this.livingRoomFragment.getAdapter().notifyItemInserted(this.lightData.getLivingroomLights().size() - 1);
    }

    public void update() {
//        Log.i("SIZE", "UNASSIGNED SIZE: " + this.lightData.getUnAssignedLights().size());
//        Log.i("SIZE", "KITCHEN SIZE: " + this.lightData.getKitchenLights().size());
//        Log.i("SIZE", "BEDROOM SIZE: " + this.lightData.getBedroomLights().size());
//        Log.i("SIZE", "LIVING ROOM SIZE: " + this.lightData.getLivingroomLights().size());

        this.allFragment.getAdapter().notifyDataSetChanged();
        this.kitchenFragment.getAdapter().notifyDataSetChanged();
        this.bedroomFragment.getAdapter().notifyDataSetChanged();
        this.livingRoomFragment.getAdapter().notifyDataSetChanged();

//        Log.i("SIZE", "2 - UNASSIGNED SIZE: " + this.lightData.getUnAssignedLights().size());
//        Log.i("SIZE", "2 - KITCHEN SIZE: " + this.lightData.getKitchenLights().size());
//        Log.i("SIZE", "2 - BEDROOM SIZE: " + this.lightData.getBedroomLights().size());
//        Log.i("SIZE", "2 - LIVING ROOM SIZE: " + this.lightData.getLivingroomLights().size());
    }
}
