```java
class Solution {
    public int stableIndex(int[] nums, int k) {
        int n = nums.length;

        // Step 1: Create suffix minimum array
        int[] suffixMin = new int[n];

        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(nums[i], suffixMin[i + 1]);
        }

        // Step 2: Calculate prefix maximum
        int prefixMax = nums[0];

        for (int i = 0; i < n; i++) {

            prefixMax = Math.max(prefixMax, nums[i]);

            // Step 3: Calculate instability score
            int instability = prefixMax - suffixMin[i];

            // Step 4: Check if index is stable
            if (instability <= k) {
                return i;
            }
        }

        // No stable index found
        return -1;
    }
}
```
