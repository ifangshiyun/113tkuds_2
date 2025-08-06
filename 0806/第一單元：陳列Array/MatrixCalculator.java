import java.util.Arrays;

public class MatrixCalculator {
    public static void main(String[] args) {
        // 測試矩陣
        int[][] matrixA = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int[][] matrixB = {
            {7, 8, 9},
            {10, 11, 12}
        };

        int[][] matrixC = {
            {1, 2},
            {3, 4},
            {5, 6}
        };

        // 1. 矩陣加法
        System.out.println("矩陣加法 (A + B):");
        printMatrix(addMatrix(matrixA, matrixB));

        // 2. 矩陣乘法
        System.out.println("\n矩陣乘法 (A × C):");
        printMatrix(multiplyMatrix(matrixA, matrixC));

        // 3. 矩陣轉置
        System.out.println("\n矩陣轉置 (A 的轉置):");
        printMatrix(transposeMatrix(matrixA));

        // 4. 最大值與最小值
        System.out.println("\nA 中的最大值與最小值:");
        findMaxMin(matrixA);
    }

    // 矩陣加法
    public static int[][] addMatrix(int[][] a, int[][] b) {
        int rows = a.length;
        int cols = a[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[i][j] = a[i][j] + b[i][j];

        return result;
    }

    // 矩陣乘法
    public static int[][] multiplyMatrix(int[][] a, int[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;
        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++)
            for (int j = 0; j < colsB; j++)
                for (int k = 0; k < colsA; k++)
                    result[i][j] += a[i][k] * b[k][j];

        return result;
    }

    // 矩陣轉置
    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                transposed[j][i] = matrix[i][j];

        return transposed;
    }

    // 尋找最大值與最小值
    public static void findMaxMin(int[][] matrix) {
        int max = matrix[0][0];
        int min = matrix[0][0];

        for (int[] row : matrix) {
            for (int value : row) {
                if (value > max) max = value;
                if (value < min) min = value;
            }
        }

        System.out.println("最大值: " + max);
        System.out.println("最小值: " + min);
    }

    // 工具函式：列印矩陣
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
