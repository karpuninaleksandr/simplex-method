package ru.ac.uniyar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ProblemToSolve {
    private static ArrayList<ArrayList<Double>> matrix;
    private static int amountOfColumns;
    private static int amountOfRows;
    private ArrayList<Double> function;
    private ArrayList<Integer> minor;
    private boolean isAutomatic;
    private boolean simulatedBasis;
    private State state;

    public ProblemToSolve() {
        matrix = new ArrayList<>();
        getData();
    }

    public double getMatrixElement(int row, int column) {
        return matrix.get(row).get(column);
    }

    public ArrayList<ArrayList<Double>> getMatrix() {
        return matrix;
    }
    public ArrayList<Integer> getMinor() {
        return minor;
    }
    public ArrayList<Double> getFunction() {
        return function;
    }

    public void printCurrentState() {
        for (int i = 0; i < amountOfRows; ++i) {
            for (int j = 0; j < amountOfColumns; ++j) System.out.print(String.format("%.2f", matrix.get(i).get(j)) + " ");
            System.out.println();
        }
    }

    private void getData() {
        try {
            System.out.print("Checking for file... ");
            Scanner scanner = new Scanner(new File("input.txt"));
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
            isAutomatic = Boolean.parseBoolean(scanner.nextLine());
            simulatedBasis = Boolean.parseBoolean(scanner.nextLine());
            setState(State.IN_PROGRESS);
            System.out.println("done.");
        } catch (FileNotFoundException ex) {
            System.out.println("no file was found.");
        }
    }

    public static void prepareMatrix() {
        for (int i = 0; i < amountOfRows; ++i) {
            if (matrix.get(i).get(amountOfColumns - 1) < 0) {
                ArrayList<Double> newLine = new ArrayList<>();
                for (int j = 0; j < amountOfColumns; ++j) newLine.add(-1 * matrix.get(i).get(j));
                matrix.remove(i);
                matrix.add(i, newLine);
            }
        }
    }

    public void removeElement(int row, int column) {
        matrix.get(row).remove(column);
    }
    public void decreaseAmountOfColumns() {
        --amountOfColumns;
    }
    public void decreaseAmountOfRows() {
        --amountOfRows;
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }
    public boolean simulatedBasis() {
        return simulatedBasis;
    }
    public int getAmountOfRows() {
        return amountOfRows;
    }
    public int getAmountOfColumns() {
        return amountOfColumns;
    }
    public State setState(State state) {
        this.state = state;
        return state;
    }
    public State getState() {
        return state;
    }
}
