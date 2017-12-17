package weatherproject;

import org.junit.Test;
import utility.HttpUtility;
import java.io.IOException;
import java.net.HttpURLConnection;
import static org.junit.Assert.assertEquals;

/**
 * Tests for checking html utilities.
 */
public class HtmlTester {

    private static final int HTTP_CODE_SUCCESS = 200;

    @Test
    public void testConnectionToWeatherAPI() throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn"
                + "&APPID=2b7472354d73a60236402c0214cd02ce";
        HttpURLConnection connection = HttpUtility.makeURLConnection(url);
        assertEquals(connection.getResponseCode(), HTTP_CODE_SUCCESS);
    }

    @Test
    public void testConnectionToForecastAPI() throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn"
                + "&APPID=2b7472354d73a60236402c0214cd02ce";
        HttpURLConnection connection = HttpUtility.makeURLConnection(url);
        assertEquals(connection.getResponseCode(), HTTP_CODE_SUCCESS);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConnectionWithNonexistentCity() throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=gfsdgfds"
                + "&APPID=2b7472354d73a60236402c0214cd02ce";
        HttpUtility.makeURLConnection(url);
    }
}
