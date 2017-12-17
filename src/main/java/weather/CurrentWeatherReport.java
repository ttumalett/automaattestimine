package weather;
import org.json.JSONException;
import org.json.JSONObject;
import utility.HttpUtility;
import java.net.HttpURLConnection;

/**
 * A weather status report that gives an overview of the current weather as requested.
 */
public class CurrentWeatherReport {

    private JSONObject data;

    private WeatherStatusRequest request;

    public CurrentWeatherReport(WeatherStatusRequest request) throws JSONException {
        this.request = request;
    }

    public void setRawWeatherData(String rawData) throws JSONException {
        data = new JSONObject(rawData);
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

    public WeatherStatusRequest getRequest() {
        return request;
    }
}
