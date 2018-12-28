package csdev.com.black.model;

import java.io.Serializable;

public class PolylineInfo implements Serializable {

    private int length;
    private long time;
    private int id;
    private double speed;

    public PolylineInfo(int length, long time, int id) {
        this.length = length;
        this.time = time;
        this.id = id;
        this.speed = (((double)length/(double)time)) * 3.6;
    }

    public int getLength() {
        return length;
    }

    public long getTime() {
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
