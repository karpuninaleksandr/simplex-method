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

    public boolean stepIsPossible() {

        return false;
    }

    private static void changeNumber(ArrayList<Double> arrayList, int position, double newNumber) {
        if (arrayList.size() > position) arrayList.remove(position);
        arrayList.add(position, newNumber);
    }
}
