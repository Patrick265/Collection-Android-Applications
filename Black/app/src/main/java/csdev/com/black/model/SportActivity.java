package csdev.com.black.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class SportActivity implements Serializable
{
    private final String title;
    private final String id;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private final int rating;
    private final String description;
    private final double distance;
    private final SportType type;

    public SportActivity(String title, LocalDateTime startTime, LocalDateTime endTime, int rating, String description, double distance, SportType type)
    {
        this.title = title;
        this.id = UUID.randomUUID().toString();
        this.startTime = startTime;
        this.endTime = endTime;
        this.rating = rating;
        this.description = description;
        this.distance = distance;
        this.type = type;
    }

    public void setEndTime(LocalDateTime endTime)
    {
        this.endTime = endTime;
    }

    public String getTitle()
    {
        return title;
    }

    public String getId()
    {
        return id;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
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

    public SportType getType()
    {
        return type;
    }
}
