package controller;
import org.json.JSONException;
import utility.HttpUtility;
import weather.CurrentWeatherReport;
import weather.ForecastReport;
import weather.WeatherStatusRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller.
 */
public class Controller {

    private Writer writer;

    private List<String> cities = new ArrayList<>();

    private List<WeatherStatusRequest> requests = new ArrayList<>();

    private List<CurrentWeatherReport> currentWeatherReports = new ArrayList<>();

    private List<ForecastReport> forecastReports = new ArrayList<>();

    public Controller(Writer writer) {
        this.writer = writer;
    }

    public void work() throws JSONException, ParseException, IOException {
        readCitiesFromFile(Paths.get("src", "main", "java", "io", "input.txt"));
        generateRequests();
        generateReports();
        setCurrentWeatherReportsRawData();
        setForecastReportsRawData();
        writeReports();
    }

    public void generateRequests() {
        if (cities.isEmpty()) {
            String cityName = askForCityName();
            cities.add(cityName);
        }
        for (String city : cities) {
            requests.add(new WeatherStatusRequest(city));
        }
    }

    public void generateReports() throws JSONException {
        for (WeatherStatusRequest req : requests) {
            currentWeatherReports.add(new CurrentWeatherReport(req));
            forecastReports.add(new ForecastReport(req));
        }
    }

    public void setCurrentWeatherReportsRawData() throws JSONException, IOException {
        for (CurrentWeatherReport report : currentWeatherReports) {
            WeatherStatusRequest request = report.getRequest();
            String source = "http://api.openweathermap.org/data/2.5/weather?q=" + request.getCityName()
                    + "&units=metric&APPID=2b7472354d73a60236402c0214cd02ce";
            HttpURLConnection connection = HttpUtility.makeURLConnection(source);
            String rawData = HttpUtility.readDataFromURL(connection);
            report.setRawWeatherData(rawData);
        }
    }

    public void setForecastReportsRawData() throws JSONException, IOException {
        for (ForecastReport report : forecastReports) {
            WeatherStatusRequest request = report.getRequest();
            String source = "http://api.openweathermap.org/data/2.5/forecast?q=" + request.getCityName()
                    + "&units=metric&APPID=2b7472354d73a60236402c0214cd02ce";
            HttpURLConnection connection = HttpUtility.makeURLConnection(source);
            String rawData = HttpUtility.readDataFromURL(connection);
            report.setRawForecastData(rawData);
        }
    }

    public void writeReports() throws JSONException, ParseException {
        for (CurrentWeatherReport cReport : currentWeatherReports) {
            int mutualIndex = currentWeatherReports.indexOf(cReport);
            ForecastReport fReport = forecastReports.get(mutualIndex);
            String writeable = cReport.getCurrentWeatherReport() + fReport.getForecastReport();
            String cityName = cReport.getCity();
            writer.writeFullReportToFile(writeable, cityName);
        }
    }

    private String askForCityName() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("You want a weather report written about the following city (enter name): ");
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> readCitiesFromFile(Path filePath) {
        try {
            BufferedReader reader = Files.newBufferedReader(filePath);
            String city = reader.readLine();
            while (city != null) {
                cities.add(city);
                city = reader.readLine();
            }
            reader.close();
            return cities;
        } catch (IOException e) {
            System.out.println("File could not be found.");
        }
        return cities;
    }

    public List<CurrentWeatherReport> getCurrentWeatherReports() {
        return currentWeatherReports;
    }

    public List<ForecastReport> getForecastReports() {
        return forecastReports;
    }

    public List<WeatherStatusRequest> getRequests() {
        return requests;
    }

    public List<String> getCities() {
        return cities;
    }
}
