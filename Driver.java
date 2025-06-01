import java.io.*;

public class Driver {
    public static void main(String[] args) {
        try {

            File f1 = new File("poly1.txt");
            File f2 = new File("poly2.txt");

            Polynomial p1 = new Polynomial(f1);
            Polynomial p2 = new Polynomial(f2);

            System.out.println("Polynomial 1: " + p1);
            System.out.println("Polynomial 2: " + p2);

            Polynomial product = p1.multiply(p2);
            System.out.println("Product: " + product);

            product.saveToFile("result.txt");

            System.out.println("Result saved to result.txt");

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}