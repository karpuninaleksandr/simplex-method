package ru.ac.uniyar;

import java.util.ArrayList;

public class SimplexMethod {
    private static ArrayList<Double> addedXs;
    private static ArrayList<Double> oldXs;
    private static ArrayList<Double> functionBelow;
    private static int oldAmountOfColumns;

    public static void init(ProblemToSolve problemToSolve) {
        System.out.print("Simplex method init... ");
        oldAmountOfColumns = problemToSolve.getAmountOfColumns();;
        addedXs = new ArrayList<>();
        for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i)
            changeNumber(addedXs, i, problemToSolve.getAmountOfColumns() + i);
        oldXs = new ArrayList<>();
        for (int i = 0; i < problemToSolve.getAmountOfColumns() -1; ++i) changeNumber(oldXs, i, i + 1);
        functionBelow = new ArrayList<>();
        if (problemToSolve.simulatedBasis()) {
            for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i)
                changeNumber(functionBelow, i, -1 * problemToSolve.getMatrixElement(0, i));
            for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i)
                for (int j = 1; j < problemToSolve.getAmountOfRows(); ++j)
                    changeNumber(functionBelow, i,
                            -1 * (problemToSolve.getMatrixElement(j, i)) + functionBelow.get(i));
        } else {
            for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i)
                changeNumber(functionBelow, i, problemToSolve.getFunction().get(i));
            changeNumber(functionBelow, problemToSolve.getAmountOfColumns(),
                    -1 * functionBelow.get(problemToSolve.getAmountOfColumns() - 1));
        }
        System.out.println("done.");
    }

    public static void makeStep(ProblemToSolve problemToSolve) {
        if (problemToSolve.isAutomatic()) {
            int row = 0;
            int column = 0;
            double diff = Integer.MAX_VALUE;
            for (int i = 0; i < problemToSolve.getAmountOfColumns() - 1; ++i)
                if (functionBelow.get(i) < 0)
                    for (int j = 0; j < problemToSolve.getAmountOfRows(); ++j)
                        if (problemToSolve.getMatrixElement(j, i) > 0 &&
                                problemToSolve.getMatrixElement(j, problemToSolve.getAmountOfColumns() - 1)
                                / problemToSolve.getMatrixElement(j, i) < diff) {
                            row = j;
                            column = i;
                            diff = problemToSolve.getMatrixElement(j, problemToSolve.getAmountOfColumns() - 1)
                                    / problemToSolve.getMatrixElement(j, i);
                        }
            System.out.println("row: " + row + " column: " + column);
            ArrayList<Double> oldColumn = new ArrayList<>();
            for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i) oldColumn.add(i, problemToSolve.getMatrixElement(i, column));
            oldColumn.add(problemToSolve.getAmountOfRows(), functionBelow.get(column));
            double tmp = oldXs.get(column);
            changeNumber(oldXs, column, addedXs.get(row));
            changeNumber(addedXs, row, tmp);
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
            if (oldXs.get(column) >= oldAmountOfColumns) {
                problemToSolve.decreaseAmountOfColumns();
                oldXs.remove(column);
                functionBelow.remove(column);
                for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i) problemToSolve.removeElement(i, column);
            }
            System.out.print("");
        } else {
            //TODO
        }
    }

    public static State checkState(ProblemToSolve problemToSolve) {
        if (problemToSolve.getMatrix().stream().anyMatch(it -> (it.get(problemToSolve.getAmountOfColumns() - 1)) < 0))
            return problemToSolve.setState(State.ERROR);
        for (int i = 0; i < problemToSolve.getAmountOfColumns(); ++i)
            if (functionBelow.get(i) < 0) {
                int finalI = i;
                if (problemToSolve.getMatrix().stream().filter(it -> it.get(finalI) < 0).count() == problemToSolve.getAmountOfRows())
                    return problemToSolve.setState(State.ERROR);
            }
        for (int i = 0; i < problemToSolve.getAmountOfColumns() - 1; ++i)
            if (functionBelow.get(i) < 0) return problemToSolve.setState(State.IN_PROGRESS);
        return problemToSolve.setState(State.DONE);
    }

    private static void changeNumber(ArrayList<Double> arrayList, int position, double newNumber) {
        if (arrayList.size() > position) arrayList.remove(position);
        arrayList.add(position, newNumber);
    }
}
