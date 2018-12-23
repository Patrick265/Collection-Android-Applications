package csdev.com.black.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import csdev.com.black.model.Coordinate;
import csdev.com.black.model.SportActivity;

public class DBHandler extends SQLiteOpenHelper
{

    public DBHandler(Context context)
    {
        super(context, DBQuery.DB_NAME, null , DBQuery.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DBQuery.CREATEMAIN);
        db.execSQL(DBQuery.CREATECOORDINATES);
        onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS'" + DBQuery.HEADERMAIN + "'");
        db.execSQL("DROP TABLE IF EXISTS'" + DBQuery.HEADERCOORDINATES + "'");
    }

    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public ArrayList<SportActivity> retrieveAll() {
        ArrayList<SportActivity> activities = new ArrayList<>();
        Log.d("RETRIEVEALL", DBQuery.RETRIEVEALLACTIVITYS);

        Cursor cursor = getReadableDatabase().rawQuery(DBQuery.RETRIEVEALLACTIVITYS, null);
        if(cursor != null) {
            cursor.moveToFirst();
        } else {
            throw new Error("CURSOR IS NULL");
        }

        if(cursor.getCount() > 0 ) {
            do {
                SportActivity activity = new SportActivity(
                        cursor.getString(cursor.getColumnIndex(DBQuery.COL_MAIN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(DBQuery.COL_MAIN_ID)),
                        cursor.getString(cursor.getColumnIndex(DBQuery.COL_MAIN_STARTIME)),
                        cursor.getString(cursor.getColumnIndex(DBQuery.COL_MAIN_ENDTIME)),
                        cursor.getInt(cursor.getColumnIndex(DBQuery.COL_MAIN_RATING)),
                        cursor.getString(cursor.getColumnIndex(DBQuery.COL_MAIN_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(DBQuery.COL_MAIN_DISTANCE)),
                        cursor.getDouble(cursor.getColumnIndex(DBQuery.COL_MAIN_AVGSPEED)),
                        cursor.getString(cursor.getColumnIndex(DBQuery.COL_MAIN_TYPE)));

                activity.setCoordinates(retrieveLatLon(activity.getId()));
                activities.add(activity);
            } while(cursor.moveToNext());
            cursor.close();
            return activities;
        }else {
            throw new Error("Entries in Database is 0 or lower, so no data is in the database");
        }
    }

    private ArrayList<Coordinate> retrieveLatLon(String id)
    {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        String query = "SELECT * FROM " + DBQuery.HEADERCOORDINATES + " WHERE " + DBQuery.COL_COORDINATES_ID + " == \"" + id + "\";";
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if(cursor != null) {
            cursor.moveToFirst();
        } else {
            throw new Error("CURSOR IS NULL");
        }

        if(cursor.getCount() > 0 ) {
            do {
                Coordinate latLng = new Coordinate(cursor.getDouble(cursor.getColumnIndex(DBQuery.COL_COORDINATES_LATITUDE)),
                                            cursor.getDouble(cursor.getColumnIndex(DBQuery.COL_COORDINATES_LONGITUDE)));
                coordinates.add(latLng);
            } while(cursor.moveToNext());

            cursor.close();
            return coordinates;
        }else {
            cursor.close();
            return coordinates;
        }
    }

    public void Insert(SportActivity activity) {
        ContentValues values = new ContentValues();
        values.put(DBQuery.COL_MAIN_ID, activity.getId());
        values.put(DBQuery.COL_MAIN_TITLE, activity.getTitle());
        values.put(DBQuery.COL_MAIN_STARTIME, activity.getStartTime().toString());
        values.put(DBQuery.COL_MAIN_ENDTIME, activity.getEndTime().toString());
        values.put(DBQuery.COL_MAIN_RATING, activity.getRating());
        values.put(DBQuery.COL_MAIN_DESCRIPTION, activity.getDescription());
        values.put(DBQuery.COL_MAIN_DISTANCE, activity.getDistance());
        values.put(DBQuery.COL_MAIN_AVGSPEED, activity.getAverageSpeed());
        values.put(DBQuery.COL_MAIN_TYPE, activity.getType());

        getWritableDatabase().insert(DBQuery.HEADERMAIN, null, values);

        values.clear();

        for (Coordinate l : activity.getCoordinates()) {
                values.put(DBQuery.COL_COORDINATES_ID, activity.getId());
                values.put(DBQuery.COL_COORDINATES_LATITUDE, l.getLatitude());
                values.put(DBQuery.COL_COORDINATES_LONGITUDE, l.getLongitude());

            getWritableDatabase().insert(DBQuery.HEADERCOORDINATES, null, values);
            values.clear();
        }
    }

    public void update(SportActivity activity) {
        ContentValues values = new ContentValues();
        values.put(DBQuery.COL_MAIN_TITLE, activity.getTitle());
        values.put(DBQuery.COL_MAIN_RATING, activity.getRating());
        values.put(DBQuery.COL_MAIN_DESCRIPTION, activity.getDescription());
        values.put(DBQuery.COL_MAIN_DISTANCE, activity.getDistance());
        values.put(DBQuery.COL_MAIN_AVGSPEED, activity.getAverageSpeed());
        values.put(DBQuery.COL_MAIN_TYPE, activity.getType());

        getWritableDatabase().update(DBQuery.HEADERMAIN, values,"ID=" + activity.getId(),null);
    }
}
