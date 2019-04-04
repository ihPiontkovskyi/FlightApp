package gui.mainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FlightApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader root = new FXMLLoader(FlightApp.class.getResource("/gui/mainWindow.fxml"));
        AnchorPane anchorPane = root.load();
        Scene scene = new Scene(anchorPane, 600, 400);
        primaryStage.setTitle("FlightDatabaseApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
