import java.util.*;

class Solution {

    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;
        int INF = n + 1;

        // best[i] = minimum length of a valid subarray
        // ending at or before index i
        int[] best = new int[n];

        Arrays.fill(best, INF);

        int left = 0;
        int sum = 0;
        int answer = INF;
        int minLength = INF;

        for (int right = 0; right < n; right++) {

            sum += arr[right];

            // Shrink window if sum is greater than target
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // Found a subarray with sum = target
            if (sum == target) {

                int length = right - left + 1;

                // Combine it with the best previous subarray
                if (left > 0 && best[left - 1] != INF) {
                    answer = Math.min(
                        answer,
                        length + best[left - 1]
                    );
                }

                minLength = Math.min(minLength, length);
            }

            // Store best subarray found so far
            best[right] = minLength;
        }

        return answer == INF ? -1 : answer;
    }
}
