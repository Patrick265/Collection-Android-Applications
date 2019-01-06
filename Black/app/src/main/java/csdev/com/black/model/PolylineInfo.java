package csdev.com.black.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.UUID;

import static java.lang.Math.round;

public class PolylineInfo implements Serializable {

    private String dbId;
    private double length;
    private double time;
    private int identificationID;
    private double speed;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public PolylineInfo(double length, long time, int identificationID) {
        this.dbId = UUID.randomUUID().toString();
        this.length = Double.valueOf(df2.format(length));
        double cur = (((length/((double)time / 1000))) * 3.60);
        this.speed = Double.valueOf(df2.format(cur));
        this.time =  Double.valueOf(df2.format(time));
        this.time = time /1000.00;
        this.identificationID = identificationID;
    }

    public PolylineInfo(String dbId, double length, double time, int identificationID)
    {
        this.dbId = dbId;
        this.length = Double.valueOf(df2.format(length));
        double cur = (((length/(time))) * 3.6);
        this.speed = Double.valueOf(df2.format(cur));
        this.time =  Double.valueOf(df2.format(time));
        this.identificationID = identificationID;
    }


    public String getDbId()
    {
        return dbId;
    }

    public double getLength() {
        return length;
    }

    public double getTime() {
        return time;
    }

    public int getIdentificationID() {
        return identificationID;
    }

    public double getSpeed() {
        return speed;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public void setTime(double time)
    {
        this.time = time;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "PolylineInfo{" +
                "length=" + length +
                ", time=" + time +
                ", identificationID=" + identificationID +
                '}';
    }
}
