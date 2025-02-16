public class MatrixSum {

    public static int sum2DArray(int[][] matrix) {
        if (matrix == null) {
            return 0;
        }

        int sum = 0;
        for (int[] row : matrix) {
            if (row != null) {
                for (int num : row) {
                    sum += num;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] matrix1 = {{1, 2}, {3, 4}, {5}};
        System.out.println(sum2DArray(matrix1)); // 15

        int[][] matrix2 = null;
        System.out.println(sum2DArray(matrix2)); // 0

        int[][] matrix3 = {{-1, 5}, null, {}};
        System.out.println(sum2DArray(matrix3)); // 4 (-1 + 5)
    }
}