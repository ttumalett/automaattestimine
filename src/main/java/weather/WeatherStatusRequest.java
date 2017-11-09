package weather;
import utility.SystemUtility;
import java.nio.file.Paths;

/**
 * A request for an overview of the weather in the specified city.
 */
public class WeatherStatusRequest {

    private String cityName;

    private String units;

    public WeatherStatusRequest() {
        units = "metric";
        cityName = SystemUtility.readCityFromFile();
        if (cityName == null) {
            cityName = SystemUtility.askForCityName();
        }
    }

    public WeatherStatusRequest(String cityName) {
        this.cityName = cityName;
        this.units = "metric";
    }

    public String getCityName() {
        return cityName;
    }

    public String getUnits() {
        return units;
    }

    public String getHi() {
        return "hi";
    }
}
