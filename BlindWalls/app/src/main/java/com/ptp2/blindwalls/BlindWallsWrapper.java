package com.ptp2.blindwalls;

import com.ptp2.blindwalls.model.BlindWall;

import java.util.ArrayList;
import java.util.List;

public class BlindWallsWrapper {

    private static BlindWallsWrapper instance;
    private List<BlindWall> blindWallList;

    private BlindWallsWrapper()
    {
        this.blindWallList = new ArrayList<>();
    }

    public static BlindWallsWrapper getInstance()
    {
        if(instance == null)
        {
            instance = new BlindWallsWrapper();
        }
        return instance;
    }

    public List<BlindWall> getBlindWallList() {
        return blindWallList;
    }
}
