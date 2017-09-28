package weatherproject;
import org.junit.BeforeClass;
import org.junit.Test;
import utility.HttpUtility;
import weather.CurrentWeatherReport;
import weather.ForecastReport;
import weather.WeatherStatusRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the weather project.
 */
public class WeatherProjectTestSystem {

    private static final int HTTP_CODE_SUCCESS = 200;

    private static final int LONGITUDE_MAX_LIMIT = 180;

    private static final int LATITUDE_MAX_LIMIT = 90;

    private static WeatherStatusRequest request;

    private static CurrentWeatherReport currentWeatherRep;

    private static ForecastReport forecastReport;

    @BeforeClass
    public static void setUpAllTests() {
        request = new WeatherStatusRequest("Tallinn", "metric");
        currentWeatherRep = new CurrentWeatherReport(request);
        forecastReport = new ForecastReport(request);
        forecastReport.sortTemperaturesByDay();
    }

    @Test
    public void testIfCoordinatesAreValid() {
        String[] coordinates = currentWeatherRep.getCoordinates().split(":");
        double lon = Double.valueOf(coordinates[0]);
        double lat = Double.valueOf(coordinates[1]);
        boolean areValid = (lon < LONGITUDE_MAX_LIMIT && lon > -1 * LONGITUDE_MAX_LIMIT
                && lat < LATITUDE_MAX_LIMIT && lat > -1 * LATITUDE_MAX_LIMIT);
        assertTrue(areValid);
    }

    @Test
    public void testIfTemperatureIsValid() {
        boolean isValid = (currentWeatherRep.getCurrentTemperature() < 70
                && currentWeatherRep.getCurrentTemperature() > -100);
        assertTrue(isValid);
    }

    @Test
    public void testForecastHighestTemperatureInADay() {
        List<Integer> temperatures = forecastReport.getTemperaturesOfTheDay();
        int highestTemp = 0;
        for (Integer temp : temperatures) {
            highestTemp = (temp > highestTemp) ? temp : highestTemp;
        }
        assertEquals(highestTemp, forecastReport.getHighestTemperatureOfTheDay());
    }

    @Test
    public void testForecastLowestTemperatureInADay() {
        List<Integer> temperatures = forecastReport.getTemperaturesOfTheDay();
        int lowestTemp = 100;
        for (Integer temp : temperatures) {
            lowestTemp = (temp < lowestTemp) ? temp : lowestTemp;
        }
        assertEquals(lowestTemp, forecastReport.getLowestTemperatureOfTheDay());
    }

    @Test
    public void testIfRequestedCityIsInResponse() {
        assertEquals(currentWeatherRep.getCity(), request.getCityName());
    }

    @Test
    public void testConnectionToAPI() {
        try {
            String url = "";
            HttpURLConnection connection = HttpUtility.makeURLConnection(url);
            assertEquals(connection.getResponseCode(), HTTP_CODE_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
