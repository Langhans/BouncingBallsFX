package se.yrgo.timlanghans;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Pane ballPane;

    @FXML
    HBox menuBox;

    @FXML
    CheckBox bWobble, bGravity;

    @FXML
    Slider slider;

    @FXML
    Button bAdd;

    @FXML
    Button bDel;

    @FXML
    public Button bDelAll;

    private Bounds bounds;

    private List<Ball> spheres = new ArrayList<>();

    private Timeline animation;

    private BooleanProperty gravity = new SimpleBooleanProperty();

    private static final double GRAVITY = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ballPane.autosize();
        bounds = ballPane.getLayoutBounds();

        // complicated way to set a background-image => go CSS!
        ballPane.setBackground(new Background(new BackgroundImage(new Image(
                getClass().getResource("/assets/cockpit.jpg").toExternalForm()),
                null, null, null, null)));

        // uncomment to start Application with 5 balls
        // makeSpheres();

        animation = new Timeline(new KeyFrame(Duration.millis(25),
                e -> paintNextFrame()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.playFromStart();

        initEventHandlers();

        bindControls();
    }


    private void initEventHandlers() {

        ballPane.setOnZoom(e -> {
            Platform.runLater(() ->
                    spheres.forEach(b -> b.puffUpBall()));
        });

        ballPane.setOnSwipeRight(e -> {
            Platform.runLater(() ->
                    spheres.forEach(b -> b.setDx(b.getDx() + 3)));
        });

        ballPane.setOnSwipeLeft(e -> {
            spheres.forEach(b -> b.setDx(b.getDx() - 3));
        });

        ballPane.setOnRotate(e -> {
            spheres.forEach(b -> b.setDx(b.getDx() - 3));
        });

        bAdd.setOnAction(e -> {
            addBallToPlane();
        });

        bDel.setOnAction(e -> deleteBallFromPlane());

        bDelAll.setOnAction(e -> deleteAllBallsOnPlane());
    }


    private void bindControls() {
        bGravity.selectedProperty().bindBidirectional(gravity);
        bWobble.selectedProperty().bindBidirectional(Ball.pressAnimationOn);
        slider.valueProperty().bindBidirectional(Ball.animationAmount);
    }

    private void paintNextFrame() {
        for (int i = 0; i < spheres.size(); i++) {
            Ball b = spheres.get(i);
            double right = b.getX() + (b.getBallRadius() * 2);
            double left = b.getX();
            double top = b.getY();
            double bottom = b.getY() + (b.getBallRadius() * 2);
            // System.out.printf("ball %d: [%.2f,%.2f,%.2f,%.2f]%n " , i ,
            // left ,right, top , bottom);
            if (left < 0 || right >= ballPane.getWidth()) {
                b.hitVertical();
            }

            if (top < 0 || bottom >= ballPane.getHeight()) {

                if (b.getDy() <= GRAVITY) {
                    b.setDy(0);
                    b.setDx(0);

                } else {
                    b.hitHorizontal();
                }
            }
            b.setX(b.getX() + b.getDx());

            if (gravity.get() && bottom < ballPane.getHeight()) {
                b.setDy(b.getDy() + GRAVITY);
            }
            b.setY(b.getY() + b.getDy());
        }
    }


    private void makeSpheres() {
        spheres.add(makeRandomBall());
        spheres.add(makeRandomBall());
        spheres.add(makeRandomBall());
        spheres.add(makeRandomBall());
        spheres.add(makeRandomBall());
        ballPane.getChildren().addAll(spheres);
    }


    private Ball makeRandomBall() {
        Random r = new Random();
        Ball b;
        double radius = 20 + r.nextInt(50);
        b = new Ball(
                (double) r.nextInt((int) (bounds.getMaxX() - radius)),
                (double) r.nextInt((int) (bounds.getMaxY() - radius)),
                (double) r.nextInt(10) + 5,
                (double) r.nextInt(10) + 5,
                radius);
        return b;
    }

    private void addBallToPlane() {
        Ball ball = makeRandomBall();
        Platform.runLater(() -> {
            spheres.add(ball);
            ballPane.getChildren().add(ball);
        });
    }


    private void deleteAllBallsOnPlane() {
        Platform.runLater(() ->
                ballPane.getChildren().removeIf(
                        b -> (b instanceof Sphere)));
    }


    private void deleteBallFromPlane() {
        Platform.runLater(() -> {
            // BUG WARNING! Balls and controls, both in ballPane!
            if (ballPane.getChildren().size() > 1) {
                Node ball = ballPane.getChildren().remove(1);
                spheres.remove(ball);
            }
        });
    }
}
