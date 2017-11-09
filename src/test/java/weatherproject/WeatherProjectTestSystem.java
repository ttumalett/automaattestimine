package weatherproject;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import utility.HttpUtility;
import weather.CurrentWeatherReport;
import weather.ForecastReport;
import weather.WeatherStatusRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
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

    private static List<Double> exampleTemperatures;

    @BeforeClass
    public static void setUpAllTests() throws JSONException {
        request = new WeatherStatusRequest("Tallinn");
        currentWeatherRep = new CurrentWeatherReport(request);
        forecastReport = new ForecastReport(request);
        exampleTemperatures = new ArrayList<>();
        exampleTemperatures.add(0.0);
        exampleTemperatures.add(24.5);
        exampleTemperatures.add(-12.4);
        exampleTemperatures.add(5.2);
    }

    @Test
    public void testIfCoordinatesAreValid() throws JSONException {
        String[] coordinates = currentWeatherRep.getCoordinates().split(":");
        double lon = Double.valueOf(coordinates[1]);
        double lat = Double.valueOf(coordinates[0]);
        boolean areValid = (lon < LONGITUDE_MAX_LIMIT && lon > -1 * LONGITUDE_MAX_LIMIT
                && lat < LATITUDE_MAX_LIMIT && lat > -1 * LATITUDE_MAX_LIMIT);
        assertTrue(areValid);
    }

    @Test
    public void testIfTemperatureIsValid() throws JSONException {
        boolean isValid = (currentWeatherRep.getCurrentTemperature() < 70
                && currentWeatherRep.getCurrentTemperature() > -100);
        assertTrue(isValid);
    }

    @Test
    public void testForecastHighestTemperatureInADay() {
        assertEquals(24.5, forecastReport.getHighestTemperatureOfTheDay(exampleTemperatures), 0.0);
    }

    @Test
    public void testForecastLowestTemperatureInADay() {
        assertEquals(-12.4, forecastReport.getLowestTemperatureOfTheDay(exampleTemperatures), 0.0);
    }

    @Test
    public void testIfRequestedCityIsInResponse() throws JSONException {
        assertEquals(currentWeatherRep.getCity(), request.getCityName());
    }

    @Test
    public void testConnectionToAPI() {
        try {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn"
                    + "&APPID=2b7472354d73a60236402c0214cd02ce";
            HttpURLConnection connection = HttpUtility.makeURLConnection(url);
            assertEquals(connection.getResponseCode(), HTTP_CODE_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
