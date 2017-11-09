package weather;
import org.json.JSONException;
import org.json.JSONObject;
import utility.HttpUtility;
import java.net.HttpURLConnection;

/**
 * A weather status report that gives an overview of the current weather as requested.
 */
public class CurrentWeatherReport {

    private static final String API_CALL_URL_BASE = "http://api.openweathermap.org/data/2.5/weather?q=";

    private static final String API_KEY = "2b7472354d73a60236402c0214cd02ce";

    private JSONObject data;

    public CurrentWeatherReport(WeatherStatusRequest request) throws JSONException {
        String source = API_CALL_URL_BASE + request.getCityName() + "&units="
                + request.getUnits() + "&APPID=" + API_KEY;
        String rawData = getRawWeatherData(source);
        data = new JSONObject(rawData);
    }

    private String getRawWeatherData(String source) {
        HttpURLConnection connection = HttpUtility.makeURLConnection(source);
        return HttpUtility.readDataFromURL(connection);
    }

    public String getCoordinates() throws JSONException {
        return data.getJSONObject("coord").getString("lat") + ":"
                + data.getJSONObject("coord").getString("lon");
    }

    public String getCity() throws JSONException {
        return data.getString("name");
    }

    public double getCurrentTemperature() throws JSONException {
        return Double.valueOf(data.getJSONObject("main").getString("temp"));
    }

    public String getCurrentWeatherReport() throws JSONException {
        return this.getCity() + "\nCoordinates: " + this.getCoordinates()
                + "\nCurrent temperature: " + this.getCurrentTemperature() + "\n";
    }
}
