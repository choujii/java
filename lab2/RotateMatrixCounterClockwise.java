import java.util.Arrays; 

public class RotateMatrixCounterClockwise {

    public static int[][] rotateCounterClockwise(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0][0];
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[cols - 1 - j][i] = matrix[i][j];
            }
        }

        return rotated;
    }

    public static void main(String[] args) {
        
        int[][] matrix1 = {{1, 2}, {3, 4}, {5, 6}};
        printMatrix(rotateCounterClockwise(matrix1));
    }


    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row)); 
        }
    }
}