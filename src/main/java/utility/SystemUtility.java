package utility;
import org.json.JSONException;
import weather.CurrentWeatherReport;
import weather.ForecastReport;
import weather.WeatherStatusRequest;
import java.text.ParseException;

/**
 * System utility: manages weather status overview requests and their correspondent reports.
 */
public class SystemUtility {

    public static void main(String[] args) throws JSONException, ParseException {
        WeatherStatusRequest request = new WeatherStatusRequest("Tallinn", "metric");
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport(request);
        ForecastReport forecastReport = new ForecastReport(request);
        System.out.println(currentWeatherReport.getCurrentWeatherReport());
        System.out.println(forecastReport.getForecastReport());
    }
}
