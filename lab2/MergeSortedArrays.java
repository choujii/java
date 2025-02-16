import java.util.Arrays;

public class MergeSortedArrays {

    public int[] mergeSortedArrays(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length + nums2.length];
        int i = 0, j = 0, k = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] <= nums2[j]) {
                result[k++] = nums1[i++];
            } else {
                result[k++] = nums2[j++];
            }
        }

        while (i < nums1.length) {
            result[k++] = nums1[i++];
        }

        while (j < nums2.length) {
            result[k++] = nums2[j++];
        }

        return result;
    }

    public static void main(String[] args) {
        MergeSortedArrays merger = new MergeSortedArrays();

        int[] arr1 = {1, 3, 5};
        int[] arr2 = {2, 4, 6};
        System.out.println(Arrays.toString(merger.mergeSortedArrays(arr1, arr2))); // [1, 2, 3, 4, 5, 6]

        int[] arr3 = {2, 2};
        int[] arr4 = {2};
        System.out.println(Arrays.toString(merger.mergeSortedArrays(arr3, arr4))); // [2, 2, 2]

        int[] arr5 = {};
        int[] arr6 = {1, 2, 3};
        System.out.println(Arrays.toString(merger.mergeSortedArrays(arr5, arr6))); // [1, 2, 3]

        int[] arr7 = {5};
        int[] arr8 = {1, 3};
        System.out.println(Arrays.toString(merger.mergeSortedArrays(arr7, arr8))); // [1, 3, 5]
    }
}