package weather;

/**
 * A request for an overview of the weather in the specified city.
 */
public class WeatherStatusRequest {

    private String cityName;

    public WeatherStatusRequest(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}
