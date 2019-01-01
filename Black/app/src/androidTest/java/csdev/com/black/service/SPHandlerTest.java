package csdev.com.black.service;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import csdev.com.black.model.MapType;
import csdev.com.black.model.Settings;

import static org.junit.Assert.*;

public class SPHandlerTest
{

    private SPHandler handler = SPHandler.getInstance(InstrumentationRegistry.getTargetContext());
    private Settings settings = new Settings(true, MapType.Normal, "Distance");

    @Test
    public void write()
    {
        handler.setSettings(settings);
        handler.write();
    }

    @Test
    public void read()
    {
        handler.getSettings().clear();
        handler.read();

        assertEquals(settings.getMap() ,handler.getSettings().getMap());
        assertEquals(settings.getSort() ,handler.getSettings().getSort());
        assertEquals(settings.isApi() ,handler.getSettings().isApi());
    }
}