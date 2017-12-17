package main;

import controller.Controller;
import controller.Writer;
import org.json.JSONException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Main.
 */
public class Main {

    public static void main(String[] args) throws JSONException, ParseException, IOException {
        Controller weatherController = new Controller(new Writer());
        weatherController.work();
    }
}
