class Solution {

    static final int MOD = 1_000_000_007;

    public int numberOfSets(int n, int k) {

        long[][] dp = new long[k + 1][n];

        // dp[0][i] = 1 way to choose 0 segments
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int segments = 1; segments <= k; segments++) {

            long sum = 0;

            for (int points = 1; points < n; points++) {

                // Ways to start a new segment
                sum = (sum + dp[segments - 1][points - 1]) % MOD;

                // Ways to keep the current segment going
                dp[segments][points] =
                        (dp[segments][points - 1] + sum) % MOD;
            }
        }

        return (int) dp[k][n - 1];
    }
}
