package com.ptp2.hueapp.util;

import com.ptp2.hueapp.model.Light;

import java.util.Iterator;
import java.util.List;

public class ListUtil {

    public static void RemoveItem(List<Light> list, String category) {
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            Light light = (Light) iterator.next();
            if(light.getCategory().equals(category)) {
                iterator.remove();
                break;
            }
        }
    }
}
