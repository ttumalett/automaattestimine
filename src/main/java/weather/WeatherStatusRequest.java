package weather;

/**
 * A request for an overview of the weather in the specified city.
 */
public class WeatherStatusRequest {

    private String cityName;

    private String units;

    public WeatherStatusRequest(String cityName, String units) {
        this.cityName = cityName;
        this.units = units;
    }

    public String getCityName() {
        return cityName;
    }

    public String getUnits() {
        return units;
    }
}
