public class RotateMatrix {

    public static int[][] rotateClockwise(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0][0];
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = matrix[i][j];
            }
        }

        return rotated;
    }

    public static void main(String[] args) {
        //Квадратная матрица 3x3
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int[][] rotated1 = rotateClockwise(matrix1);
        printMatrix(rotated1); 
        // Ожидаемый результат:
        // 7 4 1
        // 8 5 2
        // 9 6 3

        //Матрица 3x2
        int[][] matrix2 = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        int[][] rotated2 = rotateClockwise(matrix2);
        printMatrix(rotated2); 
        // Ожидаемый результат:
        // 5 3 1
        // 6 4 2

        //Пустая матрица
        int[][] matrix3 = {};
        int[][] rotated3 = rotateClockwise(matrix3);
        printMatrix(rotated3); // Пустой массив
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}