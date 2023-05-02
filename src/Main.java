import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        try {
            ui.LoginUI.main(args);
        } catch (UnsupportedClassVersionError e) {
            System.out.println("Error, Java class not supported. Download Java 19 please.");
            String  linkDescarga = "https://www.java.com/en/download/";
            Desktop.getDesktop().browse(new URI(linkDescarga));
        }


    }
}

