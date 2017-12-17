package weatherproject.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Returns an example of foreast API JSON for testing purposes.
 */
public class MockForecastApi {

    public String getJSON() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("src",
                "test", "java", "weatherproject", "mock", "forecast.txt"));
        String line = reader.readLine();
        reader.close();
        return line;
    }
}
