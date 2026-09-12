import java.util.*;

class Solution {

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // [left, right, weight, originalIndex]
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        // Sort by right endpoint
        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[3], b[3]);
        });

        Result[][] dp = new Result[5][n + 1];

        // Only selecting 0 intervals is valid initially
        for (int i = 0; i <= n; i++) {
            dp[0][i] = new Result(0, new int[0]);
        }

        for (int i = 1; i <= n; i++) {

            for (int k = 1; k <= 4; k++) {

                // Don't take current interval
                dp[k][i] = dp[k][i - 1];

                int[] current = arr[i - 1];

                // Find last interval ending before current starts
                int prev = findPrevious(arr, i - 1, current[0]);

                Result previous = dp[k - 1][prev + 1];

                if (previous != null) {

                    long newScore = previous.score + current[2];

                    int[] newIndices =
                            Arrays.copyOf(
                                    previous.indices,
                                    previous.indices.length + 1
                            );

                    newIndices[newIndices.length - 1] = current[3];

                    Arrays.sort(newIndices);

                    Result candidate =
                            new Result(newScore, newIndices);

                    if (isBetter(candidate, dp[k][i])) {
                        dp[k][i] = candidate;
                    }
                }
            }
        }

        // We want at most 4 intervals.
        Result answer = dp[0][n];

        for (int k = 1; k <= 4; k++) {
            if (isBetter(dp[k][n], answer)) {
                answer = dp[k][n];
            }
        }

        return answer.indices;
    }

    private int findPrevious(int[][] arr, int index, int left) {

        int low = 0;
        int high = index - 1;
        int answer = -1;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            // Strictly less because sharing a boundary is overlapping
            if (arr[mid][1] < left) {
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return answer;
    }

    private boolean isBetter(Result a, Result b) {

        if (a == null) {
            return false;
        }

        if (b == null) {
            return true;
        }

        // Higher score is better
        if (a.score != b.score) {
            return a.score > b.score;
        }

        // Lexicographically smaller indices are better
        return compareArrays(a.indices, b.indices) < 0;
    }

    private int compareArrays(int[] a, int[] b) {

        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {

            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }

    static class Result {

        long score;
        int[] indices;

        Result(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }
}
