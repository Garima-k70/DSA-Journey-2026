class Solution {
    public int distinctSubseqII(String s) {

        long MOD = 1_000_000_007;
        long[] dp = new long[26];

        for (char ch : s.toCharArray()) {

            int index = ch - 'a';

            long total = 0;

            for (long count : dp) {
                total = (total + count) % MOD;
            }

            dp[index] = (total + 1) % MOD;
        }

        long answer = 0;

        for (long count : dp) {
            answer = (answer + count) % MOD;
        }

        return (int) answer;
    }
}
