package utility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http utility.
 */
public class HttpUtility {

    public static HttpURLConnection makeURLConnection(String url) {
        try {
            return (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            System.out.println("Connection could not be made.");
        }
        return null;
    }

    public static String readDataFromURL(HttpURLConnection connection) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String data = "";
            String line;
            while ((line = reader.readLine()) != null) {
                data += line;
            }
            reader.close();
            return data;
        } catch (IOException e) {
            System.out.println("Data from URL could not be read.");
        }
        return null;
    }
}
