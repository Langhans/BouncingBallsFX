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
            Pane root;
            Scene scene;
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

            if (PlatformFactory.getPlatform().getName().
                    equalsIgnoreCase(PlatformFactory.ANDROID)) {
                root = (Pane) FXMLLoader.load(getClass().
                        getResource("/assets/BallPane_Android.fxml"));
                scene = new Scene(root,
                        visualBounds.getWidth(),
                        visualBounds.getHeight());
            } else {
                root = (Pane) FXMLLoader.load(getClass().
                        getResource("/assets/BallPane_Desktop.fxml"));
                scene = new Scene(root);
            }
            scene.getStylesheets().add(getClass().getResource(
                    "/assets/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}