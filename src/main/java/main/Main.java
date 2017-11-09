package main;
import org.json.JSONException;
import utility.SystemUtility;
import weather.CurrentWeatherReport;
import weather.ForecastReport;
import weather.WeatherStatusRequest;
import java.text.ParseException;

/**
 * Main.
 */
public class Main {

    public static void main(String[] args) throws JSONException, ParseException {
        WeatherStatusRequest request = new WeatherStatusRequest();
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport(request);
        ForecastReport forecastReport = new ForecastReport(request);
        String fullReport = currentWeatherReport.getCurrentWeatherReport() + forecastReport.getForecastReport();
        SystemUtility.writeFullReportToFile(fullReport);
    }
}
