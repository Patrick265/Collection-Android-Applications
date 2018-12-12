package csdev.com.black.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import csdev.com.black.model.SportActivity;
import csdev.com.black.model.SportType;

public class SportStorage
{

    private static SportStorage instance;
    private List<SportActivity> activityList;

    private SportStorage() {
        this.activityList = new ArrayList<>();
        testData();
    }

    public static SportStorage getInstance() {
        if(instance == null) {
            instance = new SportStorage();
        }
        return instance;
    }

    private void testData() {
        SportActivity cycleActivity1 = new SportActivity("Cycled to Breda",
                                                        LocalDateTime.now(),
                                                        LocalDateTime.now(),
                                                3,
                                                "Basic description of the cycling",
                                                5.0,
                                                SportType.CYCLING);

        SportActivity cycleActivity2 = new SportActivity("Cycled to Dongen",
                LocalDateTime.now(),
                LocalDateTime.now(),
                2,
                "Basic description of the cycling version 2",
                2.5,
                SportType.CYCLING);

        SportActivity cycleActivity3 = new SportActivity("Cycled to Made",
                LocalDateTime.now(),
                LocalDateTime.now(),
                5,
                "Basic description of the cycling version 3",
                10,
                SportType.CYCLING);


        SportActivity runActivitiy1 = new SportActivity("Run to Breda",
                LocalDateTime.now(),
                LocalDateTime.now(),
                3,
                "Basic description of the running",
                5.0,
                SportType.RUNNING);

        SportActivity runActivitiy2 = new SportActivity("Run to Teteringen",
                LocalDateTime.now(),
                LocalDateTime.now(),
                2,
                "Basic description of the running version 2",
                2.5,
                SportType.RUNNING);

        SportActivity runActivitiy3 = new SportActivity("Run to Oosterhout",
                LocalDateTime.now(),
                LocalDateTime.now(),
                5,
                "Basic description of the running version 3",
                10,
                SportType.RUNNING);

        this.activityList.add(cycleActivity1);
        this.activityList.add(cycleActivity2);
        this.activityList.add(cycleActivity3);
        this.activityList.add(runActivitiy1);
        this.activityList.add(runActivitiy2);
        this.activityList.add(runActivitiy3);
    }

    public List<SportActivity> getActivityList()
    {
        return activityList;
    }
}
