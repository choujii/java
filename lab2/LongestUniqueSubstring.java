import java.util.HashMap;
import java.util.Map;

public class LongestUniqueSubstring {

    public String longestUniqueSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        int start = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                left = charIndexMap.get(currentChar) + 1;
            }

            charIndexMap.put(currentChar, right);
            int currentLength = right - left + 1;

            if (currentLength > maxLength) {
                maxLength = currentLength;
                start = left;
            }
        }

        return s.substring(start, start + maxLength);
    }

    public static void main(String[] args) {
        LongestUniqueSubstring solution = new LongestUniqueSubstring();
        System.out.println(solution.longestUniqueSubstring("abcabcbb")); // "abc"
        System.out.println(solution.longestUniqueSubstring("bbbbb"));     // "b"
        System.out.println(solution.longestUniqueSubstring("pwwkew"));    // "wke" или "kew"
        System.out.println(solution.longestUniqueSubstring("abba"));      // "ab" или "bb"
        System.out.println(solution.longestUniqueSubstring("dvdf"));      // "vdf"
    }
}