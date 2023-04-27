package ru.ac.uniyar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GaussMethod {
    private static ArrayList<Double> newLine;

    public static void straightWay(ProblemToSolve problemToSolve) {
        List<Integer> usedLines = new ArrayList<>();
        System.out.print("Straight way... ");
        for (int i = 0; i < problemToSolve.getMinor().size(); ++i) {
            int columnNumber = problemToSolve.getMinor().get(i) - 1;
            for (int j = 0; j < problemToSolve.getMatrix().size(); ++j)
                if (problemToSolve.getMatrix().get(j).get(columnNumber) != 0 && problemToSolve.getMatrix().get(j).get(columnNumber) != 1) {
                    double check = problemToSolve.getMatrix().get(j).get(columnNumber);
                    removeAndAdd(j, (ArrayList<Double>) problemToSolve.getMatrix().get(j).stream().map(it -> it /= check)
                            .collect(Collectors.toList()), problemToSolve);
                }
            int lineNumber = -1;
            for (int j = 0; j < problemToSolve.getMatrix().size(); ++j)
                if (problemToSolve.getMatrix().get(j).get(columnNumber) == 1 && !usedLines.contains(j)) {
                    lineNumber = j;
                    usedLines.add(j);
                    break;
                }
            if (lineNumber == -1) break;
            for (int j = 0; j < problemToSolve.getMatrix().size(); ++j) {
                if (j == lineNumber || problemToSolve.getMatrix().get(j).get(columnNumber) == 0) continue;
                newLine = new ArrayList<>();
                for (int k = 0; k < problemToSolve.getMatrix().get(j).size(); ++k) newLine.add(problemToSolve.getMatrix()
                        .get(j).get(k) - problemToSolve.getMatrix().get(lineNumber).get(k));
                removeAndAdd(j, newLine, problemToSolve);
            }
        }
        usedLines.clear();
        System.out.println("done.");
    }

    public static void backwards(ProblemToSolve problemToSolve) {
        System.out.print("Backwards way... ");
        for (int i = problemToSolve.getMinor().size() - 1; i >= 0; --i) {
            int columnNumber = problemToSolve.getMinor().get(i) - 1;
            int lineNumber = -1;
            for (int j = 0; j < problemToSolve.getMatrix().size(); ++j) {
                int finalJ = j;
                if (Math.abs(problemToSolve.getMatrix().get(j).get(columnNumber)) != 0 &&
                        problemToSolve.getMatrix().get(j).stream().noneMatch(it -> (problemToSolve.getMatrix().get(finalJ)
                                .indexOf(it) != columnNumber) && Math.abs(it) != 0
                                && problemToSolve.getMinor().contains(problemToSolve.getMatrix().get(finalJ)
                                .indexOf(it) + 1))) {
                    lineNumber = j;
                    break;
                }
            }
            if (lineNumber == -1) break;
            for (int j = 0; j < problemToSolve.getMatrix().size(); ++j) {
                if (j == lineNumber || Math.abs(problemToSolve.getMatrix().get(j).get(columnNumber)) == 0) continue;
                newLine = new ArrayList<>();
                for (int k = 0; k < problemToSolve.getMatrix().get(j).size(); ++k) newLine.add(problemToSolve.getMatrix()
                        .get(j).get(k) - (problemToSolve.getMatrix().get(j).get(columnNumber) / problemToSolve.getMatrix()
                        .get(lineNumber).get(columnNumber)) * problemToSolve.getMatrix().get(lineNumber).get(k));
                removeAndAdd(j, newLine, problemToSolve);
            }
        }
        for (int i = 0; i < problemToSolve.getMatrix().size(); ++i) {
            double out = 0;
            for (int j = 0; j < problemToSolve.getMinor().size(); ++j) {
                if (problemToSolve.getMatrix().get(i).get(problemToSolve.getMinor().get(j) - 1) != 0) {
                    out = problemToSolve.getMatrix().get(i).get(problemToSolve.getMinor().get(j) - 1);
                    break;
                }
            }
            double finalOut = out;
            removeAndAdd(i, (ArrayList<Double>) problemToSolve.getMatrix().get(i).stream().map(it -> it /= finalOut)
                    .collect(Collectors.toList()), problemToSolve);
        }
        System.out.println("done.");
    }

    private static void removeAndAdd(int j, ArrayList<Double> newLine, ProblemToSolve problemToSolve) {
        problemToSolve.getMatrix().remove(problemToSolve.getMatrix().get(j));
        problemToSolve.getMatrix().add(j, newLine);
    }
}
