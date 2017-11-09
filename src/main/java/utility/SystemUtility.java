package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * System utility: offers additional methods needed by the weather app in order to work.
 */
public class SystemUtility {

    public static String readCityFromFile() {
        Path filePath = Paths.get("src", "main", "java", "io", "input.txt");
        try {
            BufferedReader reader = Files.newBufferedReader(filePath);
            String line = reader.readLine();
            reader.close();
            return line;
        } catch (IOException e) {
            System.out.println("File could not be found.");
        }
        return null;
    }

    public static void writeFullReportToFile(String report) {
        Path filePath = Paths.get("src", "main", "java", "io", "output.txt");
        try {
            BufferedWriter writer = Files.newBufferedWriter(filePath);
            writer.write(report);
            writer.close();
        } catch (IOException e) {
            System.out.println("File could not be found.");
        }
    }

    public static String askForCityName() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("You want a weather report written about the following city (enter name): ");
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(String day) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
