import java.io.*;
import java.net.*;


public class Client {
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server"); 
            // read the operation to perform from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter operation to perform (add/subtract/multiply): ");
            String operation = reader.readLine(); 
            // read the matrices from the user
            System.out.println("Enter the first matrix (comma-separated values):");
            double[][] matrixA = readMatrix();
            System.out.println("Enter the second matrix (comma-separated values):");
            double[][] matrixB = readMatrix();
            // send the operation and matrices to the server
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(operation);
            outputStream.writeObject(matrixA);
            outputStream.writeObject(matrixB);
            // receive the result from the server
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            double[][] resultMatrix = (double[][]) inputStream.readObject();
            // print the result
            System.out.println("Result:");
            printMatrix(resultMatrix);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static double[][] readMatrix() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        String[] rows = input.split("\\s*,\\s*");
        double[][] matrix = new double[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] values = rows[i].split("\\s+");
            matrix[i] = new double[values.length];
            for (int j = 0; j < values.length; j++) {
                matrix[i][j] = Double.parseDouble(values[j]);
            }
        }
        return matrix;
    }
    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
