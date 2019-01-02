package csdev.com.black.model;

public class Weather
{
    private double temperature;
    private double temperatureMin;
    private double temperatureMax;

    private double humidity;
    private double pressure;

    private double windSpeed;
    private double windDir;

    private double rainMM;
    private double cloudPercentage;
    private String cityName;

    public Weather(double temperature, double temperatureMin, double temperatureMax, double humidity, double pressure, double windSpeed, double windDir, double rainMM, double cloudPercentage, String cityName)
    {
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
        this.rainMM = rainMM;
        this.cloudPercentage = cloudPercentage;
        this.cityName = cityName;
    }

    public Weather(double temperature, double temperatureMin, double temperatureMax, double humidity, double pressure, double windSpeed, double windDir, double cloudPercentage, String cityName)
    {
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
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

    public double getWindDir()
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
}
