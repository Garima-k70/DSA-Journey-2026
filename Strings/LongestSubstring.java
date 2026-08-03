import java.util.HashMap;

class Solution {

    public int lengthOfLongestSubstring(String s) {

        HashMap<Character, Integer> map = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {

            char currentChar = s.charAt(right);

            if (map.containsKey(currentChar) && map.get(currentChar) >= left) {
                left = map.get(currentChar) + 1;
            }

            map.put(currentChar, right);

            int currentLength = right - left + 1;

            if (currentLength > maxLength) {
                maxLength = currentLength;
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {

        Solution obj = new Solution();

        String s = "abcabcbb";

        System.out.println("Length of Longest Substring: " +
                obj.lengthOfLongestSubstring(s));
    }
}
