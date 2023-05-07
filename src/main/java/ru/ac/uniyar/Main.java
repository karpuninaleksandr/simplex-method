package ru.ac.uniyar;

public class Main {
    public static void main(String[] args) {
        ProblemToSolve problemToSolve = new ProblemToSolve();
        problemToSolve.printCurrentState();

        if (!problemToSolve.simulatedBasis()) {
            GaussMethod.straightWay(problemToSolve);
            problemToSolve.printCurrentState();

            GaussMethod.backwards(problemToSolve);
            problemToSolve.printCurrentState();
        }

        SimplexMethod.init(problemToSolve);

    }
}
