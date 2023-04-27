package ru.ac.uniyar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ProblemToSolve {
    private ArrayList<ArrayList<Double>> matrix;
    private int amountOfColumns;
    private int amountOfRows;
    private ArrayList<Double> function;
    private ArrayList<Integer> minor;

    public ProblemToSolve() {
        matrix = new ArrayList<>();
        fillTheMatrix();
    }

    public double getMatrixElement(int row, int column) {
        return matrix.get(row).get(column);
    }

    //todo fix method
    public HashMap getPossibleElements() {
        HashMap possibleElementsForStep = new HashMap<>();
        for (int i = 0; i < amountOfRows - 1; ++i) {

        }
        return possibleElementsForStep;
    }

    public ArrayList<ArrayList<Double>> getMatrix() {
        return matrix;
    }

    public ArrayList<Integer> getMinor() {
        return minor;
    }

    public void printCurrentState() {
        for (int i = 0; i < amountOfRows; ++i) {
            for (int j = 0; j < amountOfColumns; ++j) System.out.print(String.format("%.2f", matrix.get(i).get(j)) + " ");
            System.out.println();
        }
    }

    //temporary
    public void fillTheMatrix() {
        try {
            System.out.print("Checking for file... ");
            Scanner scanner = new Scanner(new File("temporary_input.txt"));
            System.out.println("done.");
            System.out.print("Reading file... ");
            String first = scanner.nextLine();
            amountOfRows = Integer.parseInt(first.split(" ")[0]);
            amountOfColumns = Integer.parseInt(first.split(" ")[1]);
            function = (ArrayList<Double>) Arrays.stream(scanner.nextLine().split(" ")).map(Double::parseDouble)
                    .collect(Collectors.toList());
            for (int i = 0; i < amountOfRows; ++i) matrix.add((ArrayList<Double>) Arrays.stream(scanner.nextLine()
                    .split(" ")).map(Double::parseDouble).collect(Collectors.toList()));
            minor = (ArrayList<Integer>) Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt)
                    .collect(Collectors.toList());
            System.out.println("done.");
        } catch (FileNotFoundException ex) {
            System.out.println("no file was found.");
        }


    }
}
