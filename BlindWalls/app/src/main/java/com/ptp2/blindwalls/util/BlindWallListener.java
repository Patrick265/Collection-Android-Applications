package com.ptp2.blindwalls.util;

import com.ptp2.blindwalls.model.BlindWall;

public interface BlindWallListener {

    public void onBlindWallAvailable(BlindWall wall);
    public void onBlindWallError(Error error);
}
