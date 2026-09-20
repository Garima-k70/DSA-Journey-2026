class Solution {

    public int reverseDegree(String s) {

        int answer = 0;

        for (int i = 0; i < s.length(); i++) {

            // Reverse alphabet value
            int reverseValue = 'z' - s.charAt(i) + 1;

            // Position in string (1-indexed)
            int position = i + 1;

            answer += reverseValue * position;
        }

        return answer;
    }
}
