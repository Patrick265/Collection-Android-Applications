package csdev.com.black.model;

public class Settings
{
    private boolean api;
    private MapType map;
    private String sort;

    public Settings(boolean api, MapType map, String sort)
    {
        this.api = api;
        this.map = map;
        this.sort = sort;
    }

    public Settings()
    {
    }

    public boolean isApi()
    {
        return api;
    }

    public void setApi(boolean api)
    {
        this.api = api;
    }

    public MapType getMap()
    {
        return map;
    }

    public void setMap(MapType map)
    {
        this.map = map;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    @Override
    public String toString()
    {
        return "Settings{" +
                "api=" + api +
                ", map=" + map +
                ", sort='" + sort + '\'' +
                '}';
    }

    public void clear() {
        this.api = false;
        this.map = null;
        this.sort = "";
    }
}
