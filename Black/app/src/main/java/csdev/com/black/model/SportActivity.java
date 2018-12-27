package csdev.com.black.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class SportActivity implements Serializable
{
    private String title;
    private String id;
    private String startTime;
    private String endTime;
    private int rating;
    private String description;
    private double distance;
    private double averageSpeed;
    private String type;
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

    public SportActivity()
    {
        this.id = UUID.randomUUID().toString();
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:MM");
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates)
    {
        this.coordinates = coordinates;
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

    public double getAverageSpeed()
    {
        return averageSpeed;
    }

    public String getType()
    {
        return type;
    }

    public ArrayList<Coordinate> getCoordinates()
    {
        return coordinates;
    }

    public DateTimeFormatter getFormatter()
    {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter)
    {
        this.formatter = formatter;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public void setAverageSpeed(double averageSpeed)
    {
        this.averageSpeed = averageSpeed;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "SportActivity{" +
                "title='" + title + '\'' + "\n" +
                ", id='" + id + '\'' +  "\n" +
                ", startTime='" + startTime + '\'' +  "\n" +
                ", endTime='" + endTime + '\'' +  "\n" +
                ", rating=" + rating +  "\n" +
                ", description='" + description + '\'' +  "\n" +
                ", distance=" + distance +  "\n" +
                ", averageSpeed=" + averageSpeed +  "\n" +
                ", type='" + type + '\'' +  "\n" +
                ", coordinates=" + coordinates +  "\n" +
                '}';
    }
}
