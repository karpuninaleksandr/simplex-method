package ru.ac.uniyar.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import ru.ac.uniyar.method.ProblemToSolve;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                StringBuilder minor;
                if (isSimulatedBasis) minor = new StringBuilder("0");
                else minor = new StringBuilder();
                if (isSimulatedBasis) minor.append(" 0".repeat(Math.max(0, amountOfVariables - 1)));
                else minor.append(((TextField) topBox.getChildren().stream().filter(it -> it.getId() != null && it.getId()
                        .equals("minor")).toList().get(0)).getText());
                onTopBoxInput(amountOfVariables, amountOfLimits, function, minor.toString(), isAutomatic, isSimulatedBasis);
                topBox.setDisable(true);
            }
        });
        topBox.getChildren().add(input);

    }

    private void onTopBoxInput(int amountOfVariables, int amountOfLimits, String function, String minor, boolean isAutomatic,
                               boolean isSimulatedBasis) {
        centerBox = new VBox();
        Label matrixLabel = new Label("Введите матрицу ограничений:");
        centerBox.getChildren().add(matrixLabel);
        for (int i = 0; i < amountOfLimits;) {
            TextField field = new TextField();
            field.setId("field" + ++i);
            centerBox.getChildren().add(field);
        }
        Button input = new Button("Ввести");
        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<String> matrix = centerBox.getChildren().stream().filter(it -> it.getId() != null && it.getId()
                                .contains("field")).map(it -> ((TextField) it).getText()).toList();
                printDataToFile(amountOfVariables, amountOfLimits, function, matrix, minor, isAutomatic, isSimulatedBasis);
                onMethodStart();
            }
        });
        centerBox.getChildren().add(input);
        mainPane.setCenter(centerBox);
    }

    private void printDataToFile(int amountOfVariables, int amountOfLimits, String function, List<String> matrix,
                                 String minor, boolean isAutomatic, boolean isSimulatedBasis) {
        try (PrintWriter writer = new PrintWriter("file.txt", StandardCharsets.UTF_8)) {
            writer.println(amountOfLimits + " " + (amountOfVariables + 1));
            writer.println(function);
            matrix.forEach(writer::println);
            writer.println(minor);
            writer.println(isAutomatic);
            writer.println(isSimulatedBasis);
        } catch (IOException e) {
            System.out.println("error creating or writing to file");
        }
    }

    private void onMethodStart() {

    }
}
