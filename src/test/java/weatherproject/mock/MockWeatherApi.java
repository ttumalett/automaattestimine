package weatherproject.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Returns an example of current weather API JSON for testing purposes.
 */
public class MockWeatherApi {

    public String getJSON() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("src",
                "test", "java", "weatherproject", "mock", "currentWeather.txt"));
        String line = reader.readLine();
        reader.close();
        return line;
    }
}
