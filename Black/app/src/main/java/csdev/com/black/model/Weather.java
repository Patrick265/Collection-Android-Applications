package csdev.com.black.model;

public class Weather
{
    private double temperature;
    private double temperatureMin;
    private double temperatureMax;

    private double humidity;
    private double pressure;

    private double windSpeed;
    private String windDir;

    private double rainMM;
    private double cloudPercentage;
    private String cityName;

    public Weather(double temperature, double temperatureMin, double temperatureMax, double humidity, double pressure, double windSpeed, double windDir, double rainMM, double cloudPercentage, String cityName)
    {
        this.temperature = temperature - 273.15;
        this.temperatureMin = temperatureMin - 273.15;
        this.temperatureMax = temperatureMax - 273.15;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDir = setWindDir(windDir);
        this.rainMM = rainMM;
        this.cloudPercentage = cloudPercentage;
        this.cityName = cityName;
    }

    public Weather(double temperature, double temperatureMin, double temperatureMax, double humidity, double pressure, double windSpeed, double windDir, double cloudPercentage, String cityName)
    {
        this.temperature = temperature - 273.15;
        this.temperatureMin = temperatureMin - 273.15;
        this.temperatureMax = temperatureMax - 273.15;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDir = setWindDir(windDir);
        this.cloudPercentage = cloudPercentage;
        this.cityName = cityName;
    }

    public double getTemperature()
    {
        return temperature;
    }

    public double getTemperatureMin()
    {
        return temperatureMin;
    }

    public double getTemperatureMax()
    {
        return temperatureMax;
    }

    public double getHumidity()
    {
        return humidity;
    }

    public double getPressure()
    {
        return pressure;
    }

    public double getWindSpeed()
    {
        return windSpeed;
    }

    public String getWindDir()
    {
        return windDir;
    }

    public double getRainMM()
    {
        return rainMM;
    }

    public double getCloudPercentage()
    {
        return cloudPercentage;
    }

    public String getCityName()
    {
        return cityName;
    }

    @Override
    public String toString()
    {
        return "Weather{" +
                "temperature=" + temperature +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", windDir=" + windDir +
                ", rainMM=" + rainMM +
                ", cloudPercentage=" + cloudPercentage +
                ", cityName='" + cityName + '\'' +
                '}';
    }

    private String setWindDir(double degrees)
    {
        if (degrees == 0 || degrees == 360)
        {
            return "North";
        } else if (degrees > 0 && degrees < 90)
        {
            return "North-East";
        } else if (degrees == 90)
        {
            return "East";
        } else if (degrees > 90 && degrees < 180)
        {
            return "South-East";
        } else if (degrees == 180)
        {
            return "South";
        } else if (degrees > 180 && degrees < 270)
        {
            return "South-West";
        } else if (degrees == 270)
        {
            return "West";
        } else if (degrees > 270 && degrees < 360)
        {
            return "North-West";
        }
        else {
            return "-";
        }
    }
}
