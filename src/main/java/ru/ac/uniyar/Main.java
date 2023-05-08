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
        while (SimplexMethod.checkState(problemToSolve).equals(State.IN_PROGRESS)) SimplexMethod.makeStep(problemToSolve);

    }
}
