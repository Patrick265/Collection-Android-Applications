package csdev.com.black.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

import csdev.com.black.model.Coordinate;
import csdev.com.black.model.PolylineInfo;
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
        db.execSQL(DBQuery.CREATEPOLYLINEINFO);
        onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS'" + DBQuery.HEADERPOLYLINE + "'");
        db.execSQL("DROP TABLE IF EXISTS'" + DBQuery.HEADERCOORDINATES + "'");
        db.execSQL("DROP TABLE IF EXISTS'" + DBQuery.HEADERMAIN + "'");
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
            //throw new Error("Entries in Database is 0 or lower, so no data is in the database");
            return activities;
        }
    }

    private ArrayList<Coordinate> retrieveLatLon(@NonNull String id)
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

    public void insert(@NonNull SportActivity activity, @NonNull ArrayList<PolylineInfo> polylineInfos) {
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

        if(activity.getCoordinates() != null ) {
            for (Coordinate l : activity.getCoordinates()) {
                values.put(DBQuery.COL_COORDINATES_ID, activity.getId());
                values.put(DBQuery.COL_COORDINATES_LATITUDE, l.getLatitude());
                values.put(DBQuery.COL_COORDINATES_LONGITUDE, l.getLongitude());

                getWritableDatabase().insert(DBQuery.HEADERCOORDINATES, null, values);
                values.clear();
            }
        }

        values.clear();

        if(polylineInfos != null && polylineInfos.size() > 0) {
            for(PolylineInfo p : polylineInfos) {
                Pinsert(p, activity.getId());
            }
        } else {
            Log.e("INSERTINGDB", "COULD NOT INSERT POYLINE EITHER BECAUSE IT's NULL OR THE SIZE IS 0");
        }
    }

    public void update(@NonNull SportActivity activity) {
        ContentValues values = new ContentValues();

        values.put(DBQuery.COL_MAIN_STARTIME, activity.getStartTime());
        values.put(DBQuery.COL_MAIN_ENDTIME, activity.getEndTime());
        values.put(DBQuery.COL_MAIN_TITLE, activity.getTitle());
        values.put(DBQuery.COL_MAIN_RATING, activity.getRating());
        values.put(DBQuery.COL_MAIN_DESCRIPTION, activity.getDescription());
        values.put(DBQuery.COL_MAIN_DISTANCE, activity.getDistance());
        values.put(DBQuery.COL_MAIN_AVGSPEED, activity.getAverageSpeed());
        values.put(DBQuery.COL_MAIN_TYPE, activity.getType());

        getWritableDatabase().update(DBQuery.HEADERMAIN, values,"ID = \"" + activity.getId() + "\"",null);
    }

    public void delete(SportActivity activity) {
        this.getWritableDatabase().delete(DBQuery.HEADERPOLYLINE, DBQuery.COL_POLYLINEINFO_PAID + " = ?", new String[]{activity.getId()});
        this.getWritableDatabase().delete(DBQuery.HEADERCOORDINATES, DBQuery.COL_COORDINATES_ID + " = ?", new String[]{activity.getId()});
        this.getWritableDatabase().delete(DBQuery.HEADERMAIN, DBQuery.COL_MAIN_ID + " = ?", new String[]{activity.getId()});
    }

     public ArrayList<PolylineInfo> PRetrieveByActivity(@NonNull SportActivity activity) {
        ArrayList<PolylineInfo> polylineInfos = new ArrayList<>();
        String query = "SELECT * FROM " + DBQuery.HEADERPOLYLINE + " WHERE " + DBQuery.COL_POLYLINEINFO_PAID + " = \"" + activity.getId() + "\"";
        Log.d("RETRIEVEALL", query);

        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if(cursor != null) {
            cursor.moveToFirst();
        } else {
            throw new Error("CURSOR IS NULL");
        }

        if(cursor.getCount() > 0 ) {
            do {
                PolylineInfo polylineInfo = new PolylineInfo(
                        cursor.getString(cursor.getColumnIndex(DBQuery.COL_POLYLINEINFO_ID)),
                        cursor.getDouble(cursor.getColumnIndex(DBQuery.COL_POLYLINEINFO_PSpeed)),
                        cursor.getDouble(cursor.getColumnIndex(DBQuery.COL_POLYLINEINFO_PTime)),
                        cursor.getInt(cursor.getColumnIndex(DBQuery.COL_POLYLINEINFO_PIdentification))
                );
                polylineInfos.add(polylineInfo);
            } while(cursor.moveToNext());
            cursor.close();
            return polylineInfos;
        } else {
            return polylineInfos;
        }
    }


    public void Pupdate(@NonNull PolylineInfo polylineInfo, @NonNull String id) {
        ContentValues values = new ContentValues();
        values.put(DBQuery.COL_POLYLINEINFO_PLength, polylineInfo.getLength());
        values.put(DBQuery.COL_POLYLINEINFO_PTime, polylineInfo.getTime());
        values.put(DBQuery.COL_POLYLINEINFO_PSpeed, polylineInfo.getSpeed());

        getWritableDatabase().update(DBQuery.HEADERPOLYLINE, values,"ActivityID = \"" + id + "\"" + "AND PIdentification = "  + polylineInfo.getIdentificationID(),null);
    }


    private void Pinsert(@NonNull PolylineInfo polylineInfo, @NonNull String id) {
        ContentValues values = new ContentValues();
        values.put(DBQuery.COL_POLYLINEINFO_ID, polylineInfo.getDbId());
        values.put(DBQuery.COL_POLYLINEINFO_PAID, id);
        values.put(DBQuery.COL_POLYLINEINFO_PIdentification, polylineInfo.getIdentificationID());
        values.put(DBQuery.COL_POLYLINEINFO_PLength, polylineInfo.getLength());
        values.put(DBQuery.COL_POLYLINEINFO_PTime, polylineInfo.getTime());
        values.put(DBQuery.COL_POLYLINEINFO_PSpeed, polylineInfo.getSpeed());

        getWritableDatabase().insert(DBQuery.HEADERPOLYLINE, null, values);
    }

    public void PDelete(@NonNull PolylineInfo polylineInfo) {
        this.getWritableDatabase().delete(DBQuery.HEADERPOLYLINE, DBQuery.COL_POLYLINEINFO_PIdentification + " = ?", new String[]{String.valueOf(polylineInfo.getIdentificationID())});
    }
}