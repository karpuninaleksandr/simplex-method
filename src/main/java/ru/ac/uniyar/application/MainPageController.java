package ru.ac.uniyar.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainPageController {
    @FXML
    private BorderPane mainPane;

    @FXML
    private VBox leftBox;

    @FXML
    private VBox centerBox;

    @FXML
    private VBox topBox;

    @FXML
    protected void onInit() {
        mainPane.getChildren().clear();
        configureLeftBox();
        configureTopBox();


    }

    @FXML
    protected void onStartButtonClick() {
        mainPane.getChildren().clear();
        Label amountOfVariables = new Label("Введите количество переменных:");
        Spinner<Integer> amountOfVariablesSpinner = new Spinner<>(1, 16, 1);
        Label amountOfLimits = new Label("Введите количество ограничений:");
        Spinner<Integer> amountOfLimitsSpinner = new Spinner<>(1, 16, 1);
        Button next = new Button("Далее");
//        next.setId("");
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        VBox leftBox = new VBox();
        leftBox.setAlignment(Pos.CENTER);
        leftBox.getChildren().addAll(amountOfVariables, amountOfVariablesSpinner, amountOfLimits, amountOfLimitsSpinner);
        mainPane.setLeft(leftBox);
        VBox centerBox = new VBox();
        Label center = new Label("center");
        centerBox.getChildren().add(center);
        centerBox.setAlignment(Pos.CENTER);
        mainPane.setCenter(centerBox);
    }

    private void configureLeftBox() {
        Label amountOfVariables = new Label("Введите количество переменных:");
        Spinner<Integer> amountOfVariablesSpinner = new Spinner<>(1, 16, 1);
        Label amountOfLimits = new Label("Введите количество ограничений:");
        Spinner<Integer> amountOfLimitsSpinner = new Spinner<>(1, 16, 1);
        Button addParametersButton = new Button("Ввести");
        addParametersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                leftBox.setDisable(true);
            }
        });
        leftBox = new VBox();
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"), BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        leftBox.getChildren().addAll(amountOfVariables, amountOfVariablesSpinner, amountOfLimits, amountOfLimitsSpinner,
                addParametersButton);
        mainPane.setLeft(leftBox);
    }

    private void configureTopBox() {
        topBox = new VBox();
        topBox.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"), BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Label label = new Label("topBox");
        topBox.getChildren().add(label);
        topBox.setAlignment(Pos.CENTER);
        mainPane.setTop(topBox);
    }
}
