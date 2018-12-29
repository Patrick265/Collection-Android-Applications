package csdev.com.black.model;

import java.io.Serializable;
import java.text.DecimalFormat;

import static java.lang.Math.round;

public class PolylineInfo implements Serializable {

    private double length;
    private double time;
    private int id;
    private double speed;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public PolylineInfo(double length, long time, int id) {
        this.length = Double.valueOf(df2.format(length));
        double cur = (((length/((double)time / 1000))) * 3.60);
        this.speed = Double.valueOf(df2.format(cur));
        this.time =  Double.valueOf(df2.format(time));
        this.time = time /1000.00;
        this.id = id;
    }

    public double getLength() {
        return length;
    }

    public double getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "PolylineInfo{" +
                "length=" + length +
                ", time=" + time +
                ", id=" + id +
                '}';
    }
}
