package csdev.com.black.util;

import csdev.com.black.model.Weather;

public interface OnWeatherAvailable
{
    public void OnWeatherAvailable(Weather weather);
    public void OnWeatherError(Error error);
}
