package csdev.com.black.model;

import java.io.Serializable;

public class PolylineInfo implements Serializable {

    private int length;
    private int time;
    private int id;

    public PolylineInfo(int length, int time, int id) {
        this.length = length;
        this.time = time;
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public int getTime() {
        return time;
    }

    public int getId() {
        return id;
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
