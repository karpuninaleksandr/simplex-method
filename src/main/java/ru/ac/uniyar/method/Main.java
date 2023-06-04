package ru.ac.uniyar.method;

public class Main {
    public static void main(String[] args) {
        ProblemToSolve problemToSolve = new ProblemToSolve();
        problemToSolve.printCurrentState();

        if (!problemToSolve.simulatedBasis()) {
            GaussMethod.straightWay(problemToSolve);
            GaussMethod.backwards(problemToSolve);
            problemToSolve.printCurrentState();
        }

        problemToSolve.prepareMatrix();

        int amountOfTimes = problemToSolve.simulatedBasis() ? 2 : 1;

        for (int i = 0; i < amountOfTimes; ++i) {
            if (!problemToSolve.getState().equals(State.ERROR)) {
                SimplexMethod.init(problemToSolve);
                problemToSolve.printCurrentState();

                while (SimplexMethod.checkState(problemToSolve).equals(State.IN_PROGRESS)) {
                    SimplexMethod.makeStep(problemToSolve);
                    problemToSolve.printCurrentState();
                }

                //debug
                if (SimplexMethod.checkState(problemToSolve).equals(State.ERROR)) System.out.println("ERROR");
                else SimplexMethod.getAnswer(problemToSolve);
            }
        }
    }
}
