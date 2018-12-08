package csdev.com.black.model.sportactivities;

import java.time.LocalDateTime;

import csdev.com.black.model.SportActivity;
import csdev.com.black.model.SportType;

public class WalkActivity extends SportActivity
{
    public WalkActivity(String title, LocalDateTime startTime, LocalDateTime endTime, int rating, String description, double distance, SportType type)
    {
        super(title, startTime, endTime, rating, description, distance, type);
    }
}
