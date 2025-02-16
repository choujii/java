public class MaxSubarraySum {

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Массив не может быть пустым");
        }

        int maxCurrent = nums[0];
        int maxGlobal = nums[0];

        for (int i = 1; i < nums.length; i++) {
            maxCurrent = Math.max(nums[i], maxCurrent + nums[i]);
            maxGlobal = Math.max(maxGlobal, maxCurrent);
        }

        return maxGlobal;
    }

    public static void main(String[] args) {
        MaxSubarraySum solution = new MaxSubarraySum();

        int[] test1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(solution.maxSubArray(test1)); // 6 (подмассив [4, -1, 2, 1])

        int[] test2 = {-3, -1, -2};
        System.out.println(solution.maxSubArray(test2)); // -1

        int[] test3 = {5};
        System.out.println(solution.maxSubArray(test3)); // 5

        int[] test4 = {-1, 0, -2};
        System.out.println(solution.maxSubArray(test4)); // 0
    }
}