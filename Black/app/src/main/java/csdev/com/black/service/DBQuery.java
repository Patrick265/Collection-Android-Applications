package csdev.com.black.service;

public class DBQuery
{

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "SPORTACTIVITY";

    public static final String CREATEMAIN = "CREATE TABLE IF NOT EXISTS MAIN (\n" +
                                            "\tID VARCHAR(500) PRIMARY KEY,\n" +
                                            "\tTitle VARCHAR(1000),\n" +
                                            "\tStartTime VARCHAR(200),\n" +
                                            "\tEndTime VARCHAR(200),\n" +
                                            "\tRating INTEGER,\n" +
                                            "\tDescription VARCHAR(2000),\n" +
                                            "\tDistance DOUBLE,\n" +
                                            "\tAvgSpeed DOUBLE,\n" +
                                            "\tType VARCHAR(100)\n" +
                                        ");";

    public static final String CREATECOORDINATES = "CREATE TABLE IF NOT EXISTS COORDINATES (\n" +
                                            "\tActivityID VARCHAR ( 500 ) NOT NULL,\n" +
                                            "\tLatitude DOUBLE,\n" +
                                            "\tLongitude DOUBLE,\n" +

                                            "\tCONSTRAINT ActivityID " +
                                            "FOREIGN KEY(ActivityID) " +
                                            "REFERENCES MAIN(ID)\n" +
                                        ");";

    public static final String HEADERMAIN = "MAIN";
    public static final String HEADERCOORDINATES = "COORDINATES";

    public static final String COL_MAIN_ID = "ID";
    public static final String COL_MAIN_TITLE = "Title";
    public static final String COL_MAIN_STARTIME = "StartTime";
    public static final String COL_MAIN_ENDTIME = "EndTime";
    public static final String COL_MAIN_DESCRIPTION = "Description";
    public static final String COL_MAIN_DISTANCE = "Distance";
    public static final String COL_MAIN_AVGSPEED = "AvgSpeed";
    public static final String COL_MAIN_TYPE = "Type";
    public static final String COL_MAIN_RATING = "Rating";

    public static final String COL_COORDINATES_ID = "ActivityID";
    public static final String COL_COORDINATES_LATITUDE = "Latitude";
    public static final String COL_COORDINATES_LONGITUDE = "Longitude";

    public static final String RETRIEVEALLACTIVITYS = "SELECT * FROM " + HEADERMAIN;
    public static final String RETRIEVEALLCOORDINATES = "SELECT * FROM " + HEADERCOORDINATES;

}
