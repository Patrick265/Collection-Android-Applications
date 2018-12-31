package csdev.com.black.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import csdev.com.black.model.MapType;
import csdev.com.black.model.Settings;

public class SPHandler
{
    private final String PREFS = "csdev.com.black";
    private final String SETTINGS = "Settings";
    private final String MAPKEY = "MAP";
    private final String SORTKEY = "SORT";
    private final String APIKEY = "API";
    private static SPHandler instance;
    private Settings settings;
    private Context context;
    private SharedPreferences preferences;

    private SPHandler(Context context) {
        this.settings = new Settings();
        this.context = context;
        this.preferences = this.context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static SPHandler getInstance(Context context)
    {
        if(instance == null){
            instance = new SPHandler(context);
        }
        return instance;
    }

    public void write() {
        if(this.settings != null) {
            if(this.settings.getMap() != null && this.settings.getSort() != null && this.settings.getSort().length() > 0) {
                this.preferences.edit().putString(this.MAPKEY, this.settings.getMap().toString()).apply();
                this.preferences.edit().putString(this.SORTKEY, this.settings.getSort()).apply();
                this.preferences.edit().putBoolean(this.APIKEY, this.settings.isApi()).apply();
            } else {
                Log.e("SPHANDLER", "COULD NOT WRITE TO SHARED PREFERENCES BECAUSE MAP/SORT WAS EITHER NULL OR THE LENGTH WAS 0");
            }
        } else {
            throw new Error("SETTINGS IS NULL");
        }
    }

    public void read() {
        if(this.settings != null) {
            this.settings.setMap(MapType.valueOf(this.preferences.getString(this.MAPKEY, MapType.SATELLITE.toString())));
            this.settings.setApi(this.preferences.getBoolean(APIKEY, true ));
            this.settings.setSort(this.preferences.getString(SORTKEY, "DISTANCE"));

        } else {
            throw new Error("SETTINGS IS NULL");
        }
    }

    public String getPREFS()
    {
        return PREFS;
    }

    public String getSETTINGS()
    {
        return SETTINGS;
    }

    public String getMAPKEY()
    {
        return MAPKEY;
    }

    public String getSORTKEY()
    {
        return SORTKEY;
    }

    public String getAPIKEY()
    {
        return APIKEY;
    }

    public Settings getSettings()
    {
        return settings;
    }

    public Context getContext()
    {
        return context;
    }

    public SharedPreferences getPreferences()
    {
        return preferences;
    }

    public static void setInstance(SPHandler instance)
    {
        SPHandler.instance = instance;
    }

    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setPreferences(SharedPreferences preferences)
    {
        this.preferences = preferences;
    }
}
