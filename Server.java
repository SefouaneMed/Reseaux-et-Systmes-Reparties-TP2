import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                // read the command from the client
                //BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //String command = reader.readLine();
                //my
                //DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
		        //String command = (String)dis.readUTF();
                //System.out.println(command);
                //mu
                // read the matrices from the client
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                //my
                String command = (String) inputStream.readObject();
                //my
                double[][] matrixA = (double[][]) inputStream.readObject();
                double[][] matrixB = (double[][]) inputStream.readObject();
                // perform the requested operation
                double[][] resultMatrix;
                switch (command) {
                    case "add":
                        resultMatrix = addMatrices(matrixA, matrixB);
                        break;
                    case "subtract":
                        resultMatrix = subtractMatrices(matrixA, matrixB);
                        break;
                    case "multiply":
                        resultMatrix = multiplyMatrices(matrixA, matrixB);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid command: " + command);
                }

                // send the result back to the client
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(resultMatrix);

                clientSocket.close();
                System.out.println("Client disconnected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double[][] addMatrices(double[][] matrixA, double[][] matrixB) {
        int numRows = matrixA.length;
        int numCols = matrixA[0].length;

        if (matrixB.length != numRows || matrixB[0].length != numCols) {
            throw new IllegalArgumentException("Matrix dimensions are incompatible");
        }

        double[][] resultMatrix = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                resultMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }

        return resultMatrix;
    }

    public static double[][] subtractMatrices(double[][] matrixA, double[][] matrixB) {
        int numRows = matrixA.length;
        int numCols = matrixA[0].length;

        if (matrixB.length != numRows || matrixB[0].length != numCols) {
            throw new IllegalArgumentException("Matrix dimensions are incompatible");
        }

        double[][] resultMatrix = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                resultMatrix[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }

        return resultMatrix;
    }

    public static double[][] multiplyMatrices(double[][] matrixA, double[][] matrixB) {
        int numRowsA = matrixA.length;
        int numColsA = matrixA[0].length;
        int numRowsB = matrixB.length;
        int numColsB = matrixB[0].length;

        if (numColsA != numRowsB) {
            throw new IllegalArgumentException("Matrix dimensions are incompatible");
        }

        double[][] resultMatrix = new double[numRowsA][numColsB];

        for (int i = 0; i < numRowsA; i++) {
            for (int j = 0; j < numColsB; j++) {
                double sum = 0;
                for (int k = 0; k < numColsA; k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                resultMatrix[i][j] = sum;
            }
        }

        return resultMatrix;
    }
}
