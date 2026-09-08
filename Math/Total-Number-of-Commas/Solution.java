class Solution {
    public long totalCommas(int n) {
        return Math.max(0, n - 999);
    }
}


// OR other method

class Solution {
    public long totalCommas(int n) {
        if (n < 1000) {
            return 0;
        }

        return n - 999;
    }
}
