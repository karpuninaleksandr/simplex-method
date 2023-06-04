package ru.ac.uniyar.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import ru.ac.uniyar.method.ProblemToSolve;

public class MainPageController {
    @FXML
    private BorderPane mainPane;
    private ProblemToSolve problemToSolve;
    private VBox leftBox;
    private VBox centerBox;
    private VBox topBox;
    private VBox bottomBox;

    @FXML
    protected void onInit() {
        mainPane.getChildren().clear();
        configureLeftBox();
        configureTopBox();
        configureBottomBox();
//        problemToSolve = new ProblemToSolve();
    }

    private void configureLeftBox() {
        Label amountOfVariables = new Label("Введите количество переменных:");
        Spinner<Integer> amountOfVariablesSpinner = new Spinner<>(1, 16, 3);
        Label amountOfLimits = new Label("Введите количество ограничений:");
        Spinner<Integer> amountOfLimitsSpinner = new Spinner<>(1, 16, 2);
        Label isAutomatic = new Label("Автоматический режим:");
        CheckBox isAutomaticCheckBox = new CheckBox();
        Label isSimulatedBasis = new Label("Искуственный базис:");
        CheckBox isSimulatedBasisCheckBox= new CheckBox();
        isSimulatedBasisCheckBox.setSelected(true);
        Button addParametersButton = new Button("Продолжить");
        addParametersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                leftBox.setDisable(true);
                topBox.setDisable(false);
                onLeftBoxInput(amountOfVariablesSpinner.getValue(), amountOfLimitsSpinner.getValue(),
                        isAutomaticCheckBox.isSelected(), isSimulatedBasisCheckBox.isSelected());
            }
        });
        leftBox = new VBox();
        leftBox.setAlignment(Pos.CENTER);
//        leftBox.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"), BorderStrokeStyle.SOLID,
//                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        leftBox.setPadding(new Insets(10));
        leftBox.setSpacing(5);
        leftBox.getChildren().addAll(amountOfVariables, amountOfVariablesSpinner, amountOfLimits, amountOfLimitsSpinner,
                isAutomatic, isAutomaticCheckBox, isSimulatedBasis, isSimulatedBasisCheckBox, addParametersButton);
        mainPane.setLeft(leftBox);
    }

    private void configureTopBox() {
        topBox = new VBox();
//        topBox.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"), BorderStrokeStyle.SOLID,
//                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Label label = new Label("Введите функцию:");
        TextField functionInputField = new TextField();
        functionInputField.setId("function");
        topBox.setAlignment(Pos.CENTER);
        topBox.setDisable(true);
        topBox.setPadding(new Insets(10));
        topBox.setSpacing(5);
        topBox.getChildren().addAll(label, functionInputField);
        mainPane.setTop(topBox);
    }

    private void configureBottomBox() {
        bottomBox = new VBox();
        Button restartButton = new Button("Заново");
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                onInit();
            }
        });
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        bottomBox.getChildren().add(restartButton);
        mainPane.setBottom(bottomBox);
    }

    private void onLeftBoxInput(int amountOfVariables, int amountOfLimits, boolean isAutomatic, boolean isSimulatedBasis) {
        System.out.println(amountOfVariables + " " + amountOfLimits + " " + isAutomatic + " " + isSimulatedBasis);
        if (!isSimulatedBasis) {
            Label minorLabel = new Label("Введите столбцы минора:");
            TextField minorTextField = new TextField();
            minorTextField.setId("minor");
            topBox.getChildren().addAll(minorLabel, minorTextField);
        }
        Button input = new Button("Ввести");
        input.setId("input");
        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String function = ((TextField) topBox.getChildren().stream().filter(it -> it.getId() != null && it.getId()
                        .equals("function")).toList().get(0)).getText();
                StringBuilder minor = new StringBuilder();
                if (isSimulatedBasis) minor.append("0 ".repeat(Math.max(0, amountOfVariables)));
                else minor.append(((TextField) topBox.getChildren().stream().filter(it -> it.getId() != null && it.getId()
                        .equals("minor")).toList().get(0)).getText());
                onTopBoxInput(function, minor.toString());
            }
        });
        topBox.getChildren().add(input);
    }

    private void onTopBoxInput(String function, String minor) {
        System.out.println(function + " " + minor);
    }
}
