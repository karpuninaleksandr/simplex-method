package ru.ac.uniyar;

import java.util.ArrayList;

public class SimplexMethod {
    private static ArrayList<Double> addedXs;
    private static ArrayList<Double> oldXs;
    private static ArrayList<Double> functionBelow;

    public static void init(ProblemToSolve problemToSolve) {
        System.out.print("Simplex method init... ");
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
            int row;
            int column;
            double diff = Integer.MAX_VALUE;
            for (int i = 0; i < problemToSolve.getAmountOfRows(); ++i)
                if (functionBelow.get(i) < 0)
                    for (int j = 0; j < problemToSolve.getAmountOfColumns() - 1; ++j)
                        if (problemToSolve.getMatrixElement(i, j) > 0 && problemToSolve.getMatrixElement(i, problemToSolve.getAmountOfColumns() - 1)
                                / problemToSolve.getMatrixElement(i, j) < diff) {
                            row = i;
                            column = j;
                            diff = problemToSolve.getMatrixElement(i, problemToSolve.getAmountOfColumns() - 1)
                                    / problemToSolve.getMatrixElement(i, j);
                        }

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
