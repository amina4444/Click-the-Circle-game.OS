package com.example.clickthecirclegame;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class HelloController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane gameArea;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button startButton;


    @FXML
    private Label timerLabel;

    private int score = 0;
    private int timeLeft = 30;
    private Timeline gameTimer;
    private Timeline circleSpawner;

    @FXML
    void startGame(ActionEvent event) {
        score = 0;
        timeLeft = 30;
        scoreLabel.setText("Your Score: " + score);
        timerLabel.setText("Time: " + timeLeft + " sc");

        startTimer();
        startSpawningCircles();
    }

    private void startTimer() {
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    timeLeft--;
                    timerLabel.setText("Time: " + timeLeft + " sc");
                    if (timeLeft <= 0) endGame();
                })
        );
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();
    }

    private void spawnCircle() {
        double radius = 10 + Math.random() * 30;
        Circle circle = new Circle(radius, Color.color(Math.random(),Math.random(),Math.random()));
        circle.setLayoutX(Math.random() * (gameArea.getWidth() - 2 * radius) + radius);
        circle.setLayoutY(Math.random() * (gameArea.getHeight()- 2 * radius) + radius);
        circle.setScaleX(0);
        circle.setScaleY(0);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), circle);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
        int points = (int) Math.round((40 - radius) / 5) + 1;

        circle.setOnMouseClicked(e -> {
            score += points;
            scoreLabel.setText("Score: " + score);
            gameArea.getChildren().remove(circle);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), circle);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(ev -> gameArea.getChildren().remove(circle));
            fadeOut.play();
        });
        gameArea.getChildren().add(circle);
    }
    private void startSpawningCircles() {
        circleSpawner = new Timeline(new KeyFrame(Duration.seconds(1), e -> spawnCircle()));
        circleSpawner.setCycleCount(Timeline.INDEFINITE);
        circleSpawner.play();
    }

    private void endGame() {
        gameTimer.stop();
        circleSpawner.stop();
        gameArea.getChildren().clear();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Your score: " + score);
            alert.showAndWait();
        });
    }
}
