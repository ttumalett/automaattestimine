package utility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Http utility.
 */
public class HttpUtility {

    private static final int HTTP_CODE_SUCCESS = 200;

    public static HttpURLConnection makeURLConnection(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        if (connection.getResponseCode() != HTTP_CODE_SUCCESS) {
            throw new IllegalArgumentException("Connection could not be made.");
        } else {
            return connection;
        }
    }

    public static String readDataFromURL(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String data = "";
        String line;
        while ((line = reader.readLine()) != null) {
            data += line;
        }
        reader.close();
        return data;
    }
}
