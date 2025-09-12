package mortis;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        assert args != null : "Launcher arguments must not be null";
        Application.launch(Main.class, args);
    }
}

