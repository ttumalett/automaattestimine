package weather;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A forecast report that gives an overview of the weather of 3 following days.
 */
public class ForecastReport {

    public ForecastReport(WeatherStatusRequest request) { }

    private String getRawForecastData() {
        return null;
    }

    public HashMap<Date, Integer> sortTemperaturesByDay() {
        return null;
    }

    public List<Integer> getTemperaturesOfTheDay() {
        return null;
    }

    public int getHighestTemperatureOfTheDay() {
        return 0;
    }

    public int getLowestTemperatureOfTheDay() {
        return 0;
    }

    public String getForecastReport() {
        return null;
    }
}
