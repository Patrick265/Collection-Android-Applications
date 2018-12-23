package csdev.com.black.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class SportActivity implements Serializable
{
    private final String title;
    private final String id;
    private final String startTime;
    private String endTime;
    private final int rating;
    private final String description;
    private final double distance;
    private final double averageSpeed;
    private final String type;
    private ArrayList<Coordinate> coordinates;
    private DateTimeFormatter formatter;

    public SportActivity(String title, LocalDateTime startTime, LocalDateTime endTime, int rating, String description, double distance, double averageSpeed, String type, ArrayList<Coordinate> coordinates)
    {
        this.title = title;
        this.averageSpeed = averageSpeed;
        this.coordinates = coordinates;
        this.id = UUID.randomUUID().toString();
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:MM");
        this.startTime = this.formatter.format(LocalDateTime.now());
        this.endTime = this.formatter.format(LocalDateTime.now());
        this.rating = rating;
        this.description = description;
        this.distance = distance;
        this.type = type;
    }

    public SportActivity(String title, String id, String startTime, String endTime, int rating, String description, double distance, double averageSpeed, String type)
    {
        this.title = title;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rating = rating;
        this.description = description;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.type = type;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates)
    {
        this.coordinates = coordinates;
    }

    public double getAverageSpeed()
    {
        return averageSpeed;
    }

    public ArrayList<Coordinate> getCoordinates()
    {
        return coordinates;
    }

    public String getTitle()
    {
        return title;
    }

    public String getId()
    {
        return id;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public DateTimeFormatter getFormatter()
    {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter)
    {
        this.formatter = formatter;
    }

    public int getRating()
    {
        return rating;
    }

    public String getDescription()
    {
        return description;
    }

    public double getDistance()
    {
        return distance;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        return "SportActivity{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", averageSpeed=" + averageSpeed +
                ", type='" + type + '\'' +
                ", coordinates=" + coordinates +
                ", formatter=" + formatter +
                '}';
    }
}
