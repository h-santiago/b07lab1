import java.io.*;
import java.util.Scanner;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }

    public Polynomial(double[] coeffs, int[] exps) {
        if (coeffs.length != exps.length)
            throw new IllegalArgumentException("Arrays must have same length.");
        coefficients = coeffs;
        exponents = exps;
    }

    public Polynomial(File file) throws IOException {
        Scanner input = new Scanner(file);
        String line = input.nextLine().trim();
        input.close();

        line = line.replaceAll("(?<!^)(?=[+-])", " ");

        String[] terms = line.split(" ");
        double[] tempCoeffs = new double[terms.length];
        int[] tempExps = new int[terms.length];
        int count = 0;

        for (String term : terms) {
            term = term.trim();
            double coeff;
            int exp;

            if (term.contains("x")) {
                String[] parts = term.split("x");
                if (parts[0].equals("") || parts[0].equals("+")) coeff = 1;
                else if (parts[0].equals("-")) coeff = -1;
                else coeff = Double.parseDouble(parts[0]);

                if (parts.length == 1 || parts[1].equals("")) exp = 1;
                else exp = Integer.parseInt(parts[1]);
            } else {
                coeff = Double.parseDouble(term);
                exp = 0;
            }

            tempCoeffs[count] = coeff;
            tempExps[count] = exp;
            count++;
        }

        coefficients = new double[count];
        exponents = new int[count];
        for (int i = 0; i < count; i++) {
            coefficients[i] = tempCoeffs[i];
            exponents[i] = tempExps[i];
        }
    }

    public Polynomial multiply(Polynomial other) {
        int size = this.coefficients.length * other.coefficients.length;
        double[] tempCoeffs = new double[size];
        int[] tempExps = new int[size];
        int count = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double coeff = this.coefficients[i] * other.coefficients[j];
                int exp = this.exponents[i] + other.exponents[j];

                // Check if we already have this exponent
                boolean found = false;
                for (int k = 0; k < count; k++) {
                    if (tempExps[k] == exp) {
                        tempCoeffs[k] += coeff;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    tempCoeffs[count] = coeff;
                    tempExps[count] = exp;
                    count++;
                }
            }
        }

        double[] finalCoeffs = new double[count];
        int[] finalExps = new int[count];
        for (int i = 0; i < count; i++) {
            finalCoeffs[i] = tempCoeffs[i];
            finalExps[i] = tempExps[i];
        }

        return new Polynomial(finalCoeffs, finalExps);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-9;
    }

    public void saveToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(this.toString());
        writer.close();
    }

    public String toString() {
        if (coefficients.length == 0) return "0";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            double c = coefficients[i];
            int e = exponents[i];

            if (i > 0 && c >= 0) sb.append("+");

            if (e == 0) sb.append(c);
            else if (e == 1) sb.append(c == 1 ? "x" : c == -1 ? "-x" : c + "x");
            else sb.append(c == 1 ? "x" + e : c == -1 ? "-x" + e : c + "x" + e);
        }
        return sb.toString();
    }
}