package se.yrgo.timlanghans;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Ball extends Sphere {

    private static final double DAMP_FACTOR = 0.95;

    final PhongMaterial redMaterial = new PhongMaterial();
    final PhongMaterial whiteMaterial = new PhongMaterial();
    final PhongMaterial greyMaterial = new PhongMaterial();
    final PhongMaterial blueMaterial = new PhongMaterial();
    final PhongMaterial lightredMaterial = new PhongMaterial();
    final PhongMaterial timMaterial = new PhongMaterial();
    final PhongMaterial timMaterial2 = new PhongMaterial();
    final PhongMaterial orangeMaterial = new PhongMaterial();

    List<PhongMaterial> materials = new ArrayList<>();

    private DoubleProperty x;
    private DoubleProperty y;
    private double dx;
    private double dy;
    private DoubleProperty radius;

    public static DoubleProperty animationAmount = new SimpleDoubleProperty(0.7);
    public static BooleanProperty pressAnimationOn = new SimpleBooleanProperty(false);

    // CONSTRUCTOR
    public Ball(
            double x,
            double y,
            double dx,
            double dy,
            double radius) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.dx = dx;
        this.dy = dy;
        this.radius = new SimpleDoubleProperty(radius);

        super.radiusProperty().bind(this.radius);
        super.layoutXProperty().bind(this.x);
        super.layoutYProperty().bind(this.y);

        createMaterials();
        this.setMaterial(materials.get((int) (Math.random() * materials.size())));

        this.setOnMouseClicked(e -> {
            puffUpBall();
        });
    }


    public void puffUpBall() {
        this.dy = 100;
        this.dx = -50 + (Math.random() * 100);
    }


    private void createMaterials() {
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);

        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);

        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUEVIOLET);

        lightredMaterial.setDiffuseColor(Color.RED);
        lightredMaterial.setSpecularColor(Color.YELLOW);

        timMaterial.setDiffuseColor(Color.WHITE);
        timMaterial.setSpecularColor(Color.WHITE);
        timMaterial.setDiffuseMap(new Image(getClass().getResource(
                "/assets/cockpit.jpg").toExternalForm()));

        timMaterial2.setDiffuseColor(Color.WHITE);
        timMaterial2.setSpecularColor(Color.WHITE);
        timMaterial2.setDiffuseMap(
                new Image(getClass().getResource(
                        "/assets/tim.jpg").toExternalForm()));

        orangeMaterial.setDiffuseColor(Color.BLACK);
        orangeMaterial.setSpecularColor(Color.ORANGE);

        materials.add(greyMaterial);
        materials.add(redMaterial);
        materials.add(whiteMaterial);
        materials.add(blueMaterial);
        materials.add(lightredMaterial);
        materials.add(timMaterial);
        materials.add(orangeMaterial);
        materials.add(timMaterial2);
    }


    public void hitVertical() {
        dx = -dx * DAMP_FACTOR;
        ballPressedOnWall();
    }


    public void hitHorizontal() {
        dy = -dy * DAMP_FACTOR;
        ballPressedOnBottom();
    }

    public void ballPressedOnWall() {
        if (pressAnimationOn.get()) {
            KeyValue kv1 = new KeyValue(super.scaleXProperty(),
                    animationAmount.get(),
                    Interpolator.EASE_OUT);
            KeyFrame k1 = new KeyFrame(Duration.millis(200), kv1);
            Timeline tl = new Timeline(k1);
            tl.setCycleCount(2);
            tl.setAutoReverse(true);
            tl.play();
        }
    }


    public void ballPressedOnBottom() {
        if (pressAnimationOn.get()) {
            KeyValue kv1 = new KeyValue(super.scaleYProperty(),
                    animationAmount.get(),
                    Interpolator.EASE_OUT);
            KeyFrame k1 = new KeyFrame(Duration.millis(200), kv1);
            Timeline tl = new Timeline(k1);
            tl.setCycleCount(2);
            tl.setAutoReverse(true);
            tl.play();
        }
    }


    public double getX() {
        return x.get();
    }


    public double getY() {
        return y.get();
    }


    public double getDx() {
        return dx;
    }


    public double getDy() {
        return dy;
    }


    public double getBallRadius() {
        return radius.get();
    }


    public void setX(double x) {
        this.x.set(x);
        ;
    }


    public void setY(double y) {
        this.y.set(y);
    }

    public void setDx(double dx) {
        this.dx = dx;
    }


    public void setDy(double dy) {
        this.dy = dy;
    }


    public void setBallRadius(double newRadius) {
        this.radius.set(newRadius);
    }

}
