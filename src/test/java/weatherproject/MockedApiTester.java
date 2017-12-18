package weatherproject;
import controller.Controller;
import controller.Writer;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import weather.CurrentWeatherReport;
import weather.ForecastReport;
import weatherproject.mock.MockForecastApi;
import weatherproject.mock.MockWeatherApi;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for the weather project using mocked APIs.
 */
public class MockedApiTester {

    private static final int LONGITUDE_MAX_LIMIT = 180;

    private static final int LATITUDE_MAX_LIMIT = 90;

    private CurrentWeatherReport currentWeatherRep;

    private ForecastReport forecastReport;

    private Controller controller;

    private Writer writer;

    @Before
    public void setUpAllTests() throws JSONException, IOException {
        writer = mock(Writer.class);
        controller = new Controller(writer);
        controller.readCitiesFromFile(Paths.get("src", "test", "java", "weatherproject", "mock", "inputTest.txt"));
        controller.generateRequests();
        controller.generateReports();
        currentWeatherRep = controller.getCurrentWeatherReports().get(0);
        forecastReport = controller.getForecastReports().get(0);
        currentWeatherRep.setRawWeatherData(new MockWeatherApi().getJSON());
        forecastReport.setRawForecastData(new MockForecastApi().getJSON());
    }

    @Test
    public void testIfGenerateRequestsWorks() {
        assertEquals(1, controller.getRequests().size());
    }

    @Test
    public void testIfGenerateCurrentWeatherReportsWorks() {
        assertEquals(1, controller.getCurrentWeatherReports().size());
    }

    @Test
    public void testIfGenerateForecastReportsWorks() {
        assertEquals(1, controller.getForecastReports().size());
    }

    @Test
    public void testIfReadingCitiesWorks() {
        assertEquals("Tallinn", controller.getCities().get(0));
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
    public void testForecastHighestTemperatureInADay() throws JSONException {
        assertEquals(-0.41, forecastReport.getHighestTemperatureOfTheDay(forecastReport.getTemperaturesOfTheDay("2017-12-17")), 0.0);
    }

    @Test
    public void testForecastLowestTemperatureInADay() throws JSONException {
        assertEquals(-1.12, forecastReport.getLowestTemperatureOfTheDay(forecastReport.getTemperaturesOfTheDay("2017-12-17")), 0.0);
    }

    @Test
    public void testIfRequestedCityIsInResponse() throws JSONException {
        assertEquals(currentWeatherRep.getCity(), currentWeatherRep.getRequest().getCityName());
    }

    @Test
    public void testWritingToFile() throws JSONException, IOException, ParseException {
        controller.writeReports();
        verify(writer).writeFullReportToFile("Tallinn\nCoordinates: 59.44:24.75\nCurrent temperature: 0.0\n"
                + "17/12/2017 |Maximum temperature: -0.41; minimum temperature: -1.12|\n"
                + "18/12/2017 |Maximum temperature: 0.61; minimum temperature: -1.13|\n"
                + "19/12/2017 |Maximum temperature: 1.77; minimum temperature: -0.07|\n", "Tallinn");
    }

    @Test
    public void testFinding3FollowingDaysForForecast() throws JSONException, ParseException {
        forecastReport.get3DaysDates();
        assertEquals("2017-12-17", forecastReport.getDay1());
        assertEquals("2017-12-18", forecastReport.getDay2());
        assertEquals("2017-12-19", forecastReport.getDay3());
    }

    @Test
    public void testGettingTemperaturesOfADay() throws JSONException {
        List<Double> actual = forecastReport.getTemperaturesOfTheDay("2017-12-17");
        List<Double> expected = Arrays.asList(-0.41, -1.12);
        assertEquals(expected, actual);
    }
}
