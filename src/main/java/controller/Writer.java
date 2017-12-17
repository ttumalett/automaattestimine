package controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writer.
 */
public class Writer {

    public void writeFullReportToFile(String report, String cityName) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("src", "main", "java", "io", cityName + ".txt"));
            writer.write(report);
            writer.close();
        } catch (IOException e) {
            System.out.println("File could not be found.");
        }
    }
}
