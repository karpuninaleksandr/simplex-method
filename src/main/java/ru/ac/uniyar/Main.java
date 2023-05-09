package ru.ac.uniyar;

public class Main {
    public static void main(String[] args) {
        ProblemToSolve problemToSolve = new ProblemToSolve();
        problemToSolve.printCurrentState();

        if (!problemToSolve.simulatedBasis()) {
            GaussMethod.straightWay(problemToSolve);
            GaussMethod.backwards(problemToSolve);
        }

        ProblemToSolve.prepareMatrix();
        SimplexMethod.init(problemToSolve);

        while (SimplexMethod.checkState(problemToSolve).equals(State.IN_PROGRESS)) {
            SimplexMethod.makeStep(problemToSolve);
            problemToSolve.printCurrentState();
        }

        if (SimplexMethod.checkState(problemToSolve).equals(State.ERROR)) System.out.println("ERROR");
        else System.out.println("DONE");

    }
}
