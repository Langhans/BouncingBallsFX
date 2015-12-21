package se.yrgo.timlanghans;

import com.gluonhq.charm.down.common.PlatformFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main1 extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("/assets/TextFxFxml.fxml"));
            Scene scene;

            if (PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)) {
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());

            } else {
                scene = new Scene(root);
            }
            scene.getStylesheets().add(Main1.class.getResource("/assets/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(PlatformFactory.getPlatform().getName() + " is run now!");
        launch(args);
    }
}