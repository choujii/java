import java.util.Arrays; 

public class MaxInRows {

    public static int[] findMaxInEachRow(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }

        int[] result = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            if (row == null || row.length == 0) {
                throw new IllegalArgumentException("Строка " + i + " пустая или содержит null.");
            }

            int max = row[0];
            for (int num : row) {
                if (num > max) {
                    max = num;
                }
            }
            result[i] = max;
        }

        return result;
    }

    public static void main(String[] args) {
        //Обычный случай
        int[][] test1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] result1 = findMaxInEachRow(test1);
        System.out.println(Arrays.toString(result1)); // [3, 6, 9]

        //Отрицательные числа
        int[][] test2 = {{-5, -2, -10}, {-1, -3}};
        int[] result2 = findMaxInEachRow(test2);
        System.out.println(Arrays.toString(result2)); // [-2, -1]

        //Пустой входной массив
        int[][] test3 = {};
        int[] result3 = findMaxInEachRow(test3);
        System.out.println(Arrays.toString(result3)); // []

        //Строки разной длины
        int[][] test4 = {{10}, {2, 5, 8}, {3}};
        int[] result4 = findMaxInEachRow(test4);
        System.out.println(Arrays.toString(result4)); // [10, 8, 3]
    }
}