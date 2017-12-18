package weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utility.HttpUtility;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A forecast report that gives an overview of the weather of 3 following days.
 */
public class ForecastReport {

    private JSONObject data;

    private String day1;

    private String day2;

    private String day3;

    private WeatherStatusRequest request;

    public ForecastReport(WeatherStatusRequest request) throws JSONException {
        this.request = request;
    }

    public void setRawForecastData(String rawData) throws JSONException {
        data = new JSONObject(rawData);
    }

    public void get3DaysDates() throws JSONException, ParseException {
        String dayUnderObservation = "";
        int daysChecked = 0;
        JSONArray array = data.getJSONArray("list");
        for (int i = 0; i < array.length(); i++) {
            String[] time = array.getJSONObject(i).getString("dt_txt").split(" ");
            if (!dayUnderObservation.equals(time[0]) && daysChecked == 3) {
                break;
            }
            if (!dayUnderObservation.equals(time[0])) {
                daysChecked++;
                dayUnderObservation = time[0];
                if (daysChecked == 1) {
                    day1 = dayUnderObservation;
                } else if (daysChecked == 2) {
                    day2 = dayUnderObservation;
                } else {
                    day3 = dayUnderObservation;
                }
            }
        }
    }

    public List<Double> getTemperaturesOfTheDay(String day) throws JSONException {
        List<Double> temperatures = new ArrayList<>();
        JSONArray array = data.getJSONArray("list");
        for (int i = 0; i < array.length(); i++) {
            String[] time = array.getJSONObject(i).getString("dt_txt").split(" ");
            if (day.equals(time[0])) {
                Double tempMax = Double.valueOf(array.getJSONObject(i).getJSONObject("main").getString("temp_max"));
                Double tempMin = Double.valueOf(array.getJSONObject(i).getJSONObject("main").getString("temp_min"));
                temperatures.add(tempMax);
                temperatures.add(tempMin);
            } else if (!day.equals(time[0]) && !temperatures.isEmpty()) {
                break;
            }
        }
        return temperatures;
    }

    public double getHighestTemperatureOfTheDay(List<Double> temperatures) {
        double highestTemperature = -1 * Double.MAX_VALUE;
        for (Double temperature : temperatures) {
            if (temperature > highestTemperature) {
                highestTemperature = temperature;
            }
        }
        return highestTemperature;
    }

    public double getLowestTemperatureOfTheDay(List<Double> temperatures) {
        double lowestTemperature = Double.MAX_VALUE;
        for (Double temperature : temperatures) {
            if (temperature < lowestTemperature) {
                lowestTemperature = temperature;
            }
        }
        return lowestTemperature;
    }

    private String getReportLine(double max, double min) {
        return " |Maximum temperature: " + max + "; minimum temperature: " + min + "|\n";
    }

    public String getForecastReport() throws JSONException, ParseException {
        get3DaysDates();
        List<Double> day1Temperatures = getTemperaturesOfTheDay(day1);
        List<Double> day2Temperatures = getTemperaturesOfTheDay(day2);
        List<Double> day3Temperatures = getTemperaturesOfTheDay(day3);
        double day1max = getHighestTemperatureOfTheDay(day1Temperatures);
        double day2max = getHighestTemperatureOfTheDay(day2Temperatures);
        double day3max = getHighestTemperatureOfTheDay(day3Temperatures);
        double day1min = getLowestTemperatureOfTheDay(day1Temperatures);
        double day2min = getLowestTemperatureOfTheDay(day2Temperatures);
        double day3min = getLowestTemperatureOfTheDay(day3Temperatures);
        return formatDate(day1) + getReportLine(day1max, day1min)
                + formatDate(day2) + getReportLine(day2max, day2min)
                + formatDate(day3) + getReportLine(day3max, day3min);
    }

    private static String formatDate(String day) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public WeatherStatusRequest getRequest() {
        return request;
    }

    public String getDay1() {
        return day1;
    }

    public String getDay2() {
        return day2;
    }

    public String getDay3() {
        return day3;
    }
}
