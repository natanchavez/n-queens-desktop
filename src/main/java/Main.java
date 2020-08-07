import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static constants.Constants.FXML_FILE;
import static constants.Constants.WINDOW_TITLE;

/**
 * This class contains the <b>main method</b>  which is the entry point of this program.
 */
public class Main extends Application {
    
    /**
     * The entry point of the application. This method is executed right after the application starts.
     *
     * @param args default value
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * This method is called from the main and it shows the primary stage of this program which has all the graphic
     * components.
     *
     * @param primaryStage contains the JavaFX stage of this program and holds all the graphic elements of the GUI.
     * @throws Exception this exception is thrown when something goes wrong. (according to Oracle's documentation)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource(FXML_FILE));
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
