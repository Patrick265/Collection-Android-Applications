package csdev.com.black.service;

public class DBQuery
{

    protected static final int DB_VERSION = 1;
    protected static final String DB_NAME = "SPORTACTIVITY";

    protected static final String CREATEMAIN = "CREATE TABLE IF NOT EXISTS MAIN (\n" +
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

    protected static final String CREATECOORDINATES = "CREATE TABLE IF NOT EXISTS COORDINATES (\n" +
                                            "\tActivityID VARCHAR ( 500 ) NOT NULL,\n" +
                                            "\tLatitude DOUBLE,\n" +
                                            "\tLongitude DOUBLE,\n" +

                                            "\tCONSTRAINT ActivityID " +
                                            "FOREIGN KEY(ActivityID) " +
                                            "REFERENCES MAIN(ID)\n" +
                                        ");";

    protected static final String CREATEPOLYLINEINFO = "CREATE TABLE IF NOT EXISTS POLYLINEINFO\n" +
                                                        "(\n" +
                                                        "\tID VARCHAR(500) PRIMARY KEY,\n" +
                                                        "\tActivityID VARCHAR(500),\n" +
                                                        "\tPIdentification INTEGER,\n" +
                                                        "\tPLength DOUBLE,\n" +
                                                        "\tPTime DOUBLE,\n" +
                                                        "\tPspeed DOUBLE,\n" +
                                                        "\t\n" +
                                                        "CONSTRAINT ActivityID \n" +
                                                        "FOREIGN KEY(ActivityID)" + "\n" +
                                                        "REFERENCES MAIN(ID)" +
                                                        ");";

    protected static final String HEADERMAIN = "MAIN";
    protected static final String HEADERCOORDINATES = "COORDINATES";
    protected static final String HEADERPOLYLINE = "POLYLINEINFO";

    protected static final String COL_MAIN_ID = "ID";
    protected static final String COL_MAIN_TITLE = "Title";
    protected static final String COL_MAIN_STARTIME = "StartTime";
    protected static final String COL_MAIN_ENDTIME = "EndTime";
    protected static final String COL_MAIN_DESCRIPTION = "Description";
    protected static final String COL_MAIN_DISTANCE = "Distance";
    protected static final String COL_MAIN_AVGSPEED = "AvgSpeed";
    protected static final String COL_MAIN_TYPE = "Type";
    protected static final String COL_MAIN_RATING = "Rating";

    protected static final String COL_COORDINATES_ID = "ActivityID";
    protected static final String COL_COORDINATES_LATITUDE = "Latitude";
    protected static final String COL_COORDINATES_LONGITUDE = "Longitude";

    protected static final String COL_POLYLINEINFO_ID = "ID";
    protected static final String COL_POLYLINEINFO_PAID = "ActivityID";
    protected static final String COL_POLYLINEINFO_PIdentification = "PIdentification";
    protected static final String COL_POLYLINEINFO_PLength = "PLength";
    protected static final String COL_POLYLINEINFO_PTime = "PTime";
    protected static final String COL_POLYLINEINFO_PSpeed = "Pspeed";


    protected static final String RETRIEVEALLACTIVITYS = "SELECT * FROM " + HEADERMAIN;

}
