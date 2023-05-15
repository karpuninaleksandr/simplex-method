package ru.ac.uniyar;

import java.util.ArrayList;
import java.util.Scanner;

public class SimplexMethod {
    private static ArrayList<Double> leftXs;
    private static ArrayList<Double> topXs;
    private static ArrayList<Double> functionBelow;
    private static int oldAmountOfColumns;
    private static boolean secondTime;

    public static void init(ProblemToSolve problemToSolve) {
        System.out.print("Simplex method init... ");
        if (problemToSolve.getState().equals(State.DONE)) {
            secondTime = true;
            initializeFunctionBelow(problemToSolve);
            checkState(problemToSolve);
            System.out.println("done.");
            return;
        }
        leftXs = new ArrayList<>();
        topXs = new ArrayList<>();
        for (int i = 0; i < problemToSolve.getAmountOfColumns() -1; ++i) changeNumber(topXs, i, i + 1);
        functionBelow = new ArrayList<>();
        if (problemToSolve.simulatedBasis()) {
            oldAmountOfColumns = problemToSolve.getAmountOfColumns();
            for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i)
                changeNumber(leftXs, i, problemToSolve.getAmountOfColumns() + i);
            for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i)
                changeNumber(functionBelow, i, -1 * problemToSolve.getMatrixElement(0, i));
            for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i)
                for (int j = 1; j < problemToSolve.getAmountOfRows(); ++j)
                    changeNumber(functionBelow, i,
                            -1 * (problemToSolve.getMatrixElement(j, i)) + functionBelow.get(i));
        } else {
            for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i)
                changeNumber(functionBelow, i, problemToSolve.getFunction().get(i));
            changeNumber(functionBelow, problemToSolve.getAmountOfColumns() - 1,
                    -1 * functionBelow.get(problemToSolve.getAmountOfColumns() - 1));
            for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i)
                for (int j = 0; j < problemToSolve.getAmountOfColumns() - 1; ++j)
                    if (problemToSolve.getMinor().contains(topXs.get(j).intValue()) && problemToSolve.getMatrixElement(i, j) != 0
                            && problemToSolve.getMatrixElement(i, j) != -0) leftXs.add(topXs.get(j));
            int iterator = 0;
            while (iterator < problemToSolve.getAmountOfColumns() - 1) {
                if (leftXs.contains(topXs.get(iterator))) {
                    problemToSolve.decreaseAmountOfColumns();
                    topXs.remove(iterator);
                    functionBelow.remove(iterator);
                    for (int j = 0; j < problemToSolve.getAmountOfRows(); ++j) problemToSolve.removeElement(j, iterator);
                    continue;
                }
                ++iterator;
            }
            initializeFunctionBelow(problemToSolve);
        }
        System.out.println("done.");
        problemToSolve.printCurrentState();
    }

    public static void makeStep(ProblemToSolve problemToSolve) {
        System.out.print("Simplex method step... ");
        int row, column;
        if (problemToSolve.isAutomatic()) {
            row = 0;
            column = 0;
            double diff = Integer.MAX_VALUE;
            for (int i = 0; i < problemToSolve.getAmountOfColumns() - 1; ++i)
                if (functionBelow.get(i) < 0)
                    for (int j = 0; j < problemToSolve.getAmountOfRows(); ++j)
//                    if (leftXs.get(j) >= oldAmountOfColumns)
                        if (problemToSolve.getMatrixElement(j, i) > 0 &&
                                problemToSolve.getMatrixElement(j, problemToSolve.getAmountOfColumns() - 1)
                                        / problemToSolve.getMatrixElement(j, i) < diff) {
                            row = j;
                            column = i;
                            diff = problemToSolve.getMatrixElement(j, problemToSolve.getAmountOfColumns() - 1)
                                    / problemToSolve.getMatrixElement(j, i);
                        }
        } else {
            row = getElement();
            column = getElement();
        }
        ArrayList<Double> oldColumn = new ArrayList<>();
        for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i) oldColumn.add(i, problemToSolve.getMatrixElement(i, column));
        oldColumn.add(problemToSolve.getAmountOfRows(), functionBelow.get(column));
        double tmp = topXs.get(column);
        changeNumber(topXs, column, leftXs.get(row));
        changeNumber(leftXs, row, tmp);
        changeNumber(problemToSolve.getMatrix().get(row), column, 1 / problemToSolve.getMatrixElement(row, column));
        for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i) {
            if (i == column) continue;
            changeNumber(problemToSolve.getMatrix().get(row), i, problemToSolve.getMatrixElement(row, i)
                    * problemToSolve.getMatrixElement(row, column));
        }
        for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i) {
            if (i == row) continue;
            changeNumber(problemToSolve.getMatrix().get(i), column, problemToSolve.getMatrixElement(i, column)
                    * problemToSolve.getMatrixElement(row, column) * -1);
        }
        changeNumber(functionBelow, column, functionBelow.get(column) * problemToSolve.getMatrixElement(row, column) * -1);
        for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i) {
            if (i == row) continue;
            for (int j = 0; j < problemToSolve.getAmountOfColumns(); ++j) {
                if (j == column) continue;
                changeNumber(problemToSolve.getMatrix().get(i), j, problemToSolve.getMatrixElement(i, j)
                        - oldColumn.get(i) * problemToSolve.getMatrixElement(row, j));
            }
        }
        for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i) {
            if (i == column) continue;
            changeNumber(functionBelow, i, functionBelow.get(i)
                    - oldColumn.get(problemToSolve.getAmountOfRows()) * problemToSolve.getMatrixElement(row, i));
        }
        if (problemToSolve.simulatedBasis() && topXs.get(column) >= oldAmountOfColumns) {
            problemToSolve.decreaseAmountOfColumns();
            topXs.remove(column);
            functionBelow.remove(column);
            for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i) problemToSolve.removeElement(i, column);
        }
        System.out.println("done.");
    }

    public static State checkState(ProblemToSolve problemToSolve) {
        if (problemToSolve.getMatrix().stream().anyMatch(it -> (it.get(problemToSolve.getAmountOfColumns() - 1)) < 0))
            return problemToSolve.setState(State.ERROR);
        for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i)
            if (functionBelow.get(i).intValue() < 0) {
                int finalI = i;
                if (problemToSolve.getMatrix().stream().filter(it -> it.get(finalI) <= 0).count() == problemToSolve.getAmountOfRows())
                    return problemToSolve.setState(State.ERROR);
            }
        for (int i = 0; i < problemToSolve.getAmountOfColumns() - 1; ++i)
            if (functionBelow.get(i).intValue() < 0) return problemToSolve.setState(State.IN_PROGRESS);
        if (problemToSolve.simulatedBasis() && problemToSolve.getState().equals(State.DONE) && !secondTime) {
            boolean check = false;
            for (int i = 0; i < functionBelow.size() - 1; ++i) if (functionBelow.get(i).intValue() != 0) check = true;
            if (check) return problemToSolve.setState(State.ERROR);
        }
        return problemToSolve.setState(State.DONE);
    }

    private static void changeNumber(ArrayList<Double> arrayList, int position, double newNumber) {
        if (arrayList.size() > position) arrayList.remove(position);
        arrayList.add(position, newNumber);
    }

    //temporary
    public static void getAnswer(ProblemToSolve problemToSolve) {
        System.out.println("ANSWER:");
        int iterator = 1;
        while (iterator < problemToSolve.getFunction().size()) {
            boolean flag = false;
            for (int i = 0; i < leftXs.size(); ++i) {
                if (leftXs.get(i) ==  iterator) {
                    System.out.println("x" + leftXs.get(i).intValue() + " = " + String
                            .format("%.2f", problemToSolve.getMatrixElement(i, Math.max(0, problemToSolve.getAmountOfColumns() - 1))));
                    flag = true;
                }
            }
            if (!flag) System.out.println("x" + iterator + " = 0,00");
            ++iterator;
        }
        System.out.println("function = " + String.format("%.2f",
                -1 * functionBelow.get(Math.max(0, problemToSolve.getAmountOfColumns() - 1))));
    }

    private static void initializeFunctionBelow(ProblemToSolve problemToSolve) {
        ArrayList<Double> newFunctionBelow = new ArrayList<>();
        ArrayList<Double> functionToChange = new ArrayList<>(problemToSolve.getFunction());
        if (problemToSolve.simulatedBasis()) {
            for (int i = 0; i < topXs.size(); ++i)
                newFunctionBelow.add((double) (functionToChange.get(topXs.get(i).intValue() - 1).intValue()));
            newFunctionBelow.add(functionToChange.get(functionToChange.size() - 1));
            int iter = 0;
            while (iter < leftXs.size()) {
                if (leftXs.get(iter)>= oldAmountOfColumns) {
                    problemToSolve.getMatrix().remove(iter);
                    problemToSolve.decreaseAmountOfRows();
                    leftXs.remove(iter);
                }
                ++iter;
            }
            System.out.println(newFunctionBelow);
            for (int i = 0; i < leftXs.size(); ++i) {
                while (functionToChange.get(leftXs.get(i).intValue() - 1) != 0) {
                    System.out.println("x" + (leftXs.get(i).intValue()));
                    if (functionToChange.get(leftXs.get(i).intValue() - 1) < 0) {
                        changeNumber(functionToChange, leftXs.get(i).intValue() - 1,
                                functionToChange.get(leftXs.get(i).intValue() - 1) + 1);
                        for (int j = 0; j < problemToSolve.getAmountOfColumns() - 1; ++j)
                            changeNumber(newFunctionBelow, j, newFunctionBelow.get(j) + problemToSolve.getMatrixElement(i, j));
                        changeNumber(newFunctionBelow, problemToSolve.getAmountOfColumns() - 1,
                                newFunctionBelow.get(problemToSolve.getAmountOfColumns() - 1) +
                                        problemToSolve.getMatrixElement(i, problemToSolve.getAmountOfColumns() - 1));
                    } else {
                        changeNumber(functionToChange, leftXs.get(i).intValue() - 1,
                                functionToChange.get(leftXs.get(i).intValue() - 1) - 1);
                        for (int j = 0; j < problemToSolve.getAmountOfColumns() - 1; ++j)
                            changeNumber(newFunctionBelow, j, newFunctionBelow.get(j) - problemToSolve.getMatrixElement(i, j));
                        changeNumber(newFunctionBelow, problemToSolve.getAmountOfColumns() - 1,
                                newFunctionBelow.get(problemToSolve.getAmountOfColumns() - 1) -
                                        problemToSolve.getMatrixElement(i, problemToSolve.getAmountOfColumns() - 1));
                    }
                    System.out.println(newFunctionBelow.toString());
                }
            }
            functionBelow = newFunctionBelow;
        } else {
            for (int i = 0; i < leftXs.size(); ++i)
                while (problemToSolve.getFunction().get(leftXs.get(i).intValue() - 1) != 0)
                    if (problemToSolve.getFunction().get(leftXs.get(i).intValue() - 1) < 0) {
                        changeNumber(problemToSolve.getFunction(), leftXs.get(i).intValue() - 1,
                                problemToSolve.getFunction().get(leftXs.get(i).intValue() - 1) + 1);
                        for (int j = 0; j < problemToSolve.getAmountOfColumns(); ++j)
                            changeNumber(functionBelow, j, functionBelow.get(j) + problemToSolve.getMatrixElement(i, j));
                    } else {
                        changeNumber(problemToSolve.getFunction(), leftXs.get(i).intValue() - 1,
                                problemToSolve.getFunction().get(leftXs.get(i).intValue() - 1) - 1);
                        for (int j = 0; j < problemToSolve.getAmountOfColumns(); ++j)
                            changeNumber(functionBelow, j, functionBelow.get(j) - problemToSolve.getMatrixElement(i, j));
                    }
        }
    }

    //temporary
    private static int getElement() {
        System.out.print("ENTER INTEGER: ");
        Scanner scanner = new Scanner(System.in);
        int output = -1;
        while (output == -1) {
            try {
                output = Integer.parseInt(scanner.nextLine());
                if (output < 0) output = -1;
            } catch (Exception e) {
                System.out.print("ENTER INTEGER: ");
            }
        }
        return output;
    }
}
