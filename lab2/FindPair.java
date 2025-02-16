import java.util.HashSet;
import java.util.Set;

public class FindPair {

    public static int[] findPairWithSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }

        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            int complement = target - num;
            if (seen.contains(complement)) {
                return new int[]{complement, num};
            }
            seen.add(num);
        }

        return null;
    }

    public static void main(String[] args) {
        //Пара существует
        int[] arr1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] result1 = findPairWithSum(arr1, target1);
        printResult(result1); // [2, 7]

        //Пара не существует
        int[] arr2 = {1, 2, 3};
        int target2 = 7;
        int[] result2 = findPairWithSum(arr2, target2);
        printResult(result2); // null

        //Отрицательные числа
        int[] arr3 = {-1, 4};
        int target3 = 3;
        int[] result3 = findPairWithSum(arr3, target3);
        printResult(result3); // [-1, 4]

        //Дубликаты
        int[] arr4 = {3, 3};
        int target4 = 6;
        int[] result4 = findPairWithSum(arr4, target4);
        printResult(result4); // [3, 3]
    }

    private static void printResult(int[] result) {
        if (result == null) {
            System.out.println("null");
        } else {
            System.out.println("[" + result[0] + ", " + result[1] + "]");
        }
    }
}