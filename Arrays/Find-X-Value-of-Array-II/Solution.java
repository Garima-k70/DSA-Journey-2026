class Solution {

    int k;

    class Node {
        int product;
        long[] count;

        Node(int product, long[] count) {
            this.product = product;
            this.count = count;
        }
    }

    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.k = k;

        int n = nums.length;
        tree = new Node[4 * n];

        build(nums, 1, 0, n - 1);

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Permanent update
            update(1, 0, n - 1, index, value);

            // Query [start, n - 1]
            Node result = query(1, 0, n - 1, start, n - 1);

            answer[i] = (int) result.count[x];
        }

        return answer;
    }

    private void build(int[] nums, int node, int left, int right) {

        if (left == right) {
            tree[node] = createNode(nums[left]);
            return;
        }

        int mid = left + (right - left) / 2;

        build(nums, node * 2, left, mid);
        build(nums, node * 2 + 1, mid + 1, right);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(
            int node,
            int left,
            int right,
            int index,
            int value) {

        if (left == right) {
            tree[node] = createNode(value);
            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node query(
            int node,
            int left,
            int right,
            int queryLeft,
            int queryRight) {

        if (queryLeft <= left && right <= queryRight) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        if (queryRight <= mid) {
            return query(
                    node * 2,
                    left,
                    mid,
                    queryLeft,
                    queryRight
            );
        }

        if (queryLeft > mid) {
            return query(
                    node * 2 + 1,
                    mid + 1,
                    right,
                    queryLeft,
                    queryRight
            );
        }

        Node leftResult = query(
                node * 2,
                left,
                mid,
                queryLeft,
                queryRight
        );

        Node rightResult = query(
                node * 2 + 1,
                mid + 1,
                right,
                queryLeft,
                queryRight
        );

        return merge(leftResult, rightResult);
    }

    private Node createNode(int value) {

        int remainder = value % k;

        long[] count = new long[k];

        // The single element is one valid prefix
        count[remainder] = 1;

        return new Node(remainder, count);
    }

    private Node merge(Node left, Node right) {

        int product = (left.product * right.product) % k;

        long[] count = new long[k];

        // Prefixes completely inside the left part
        for (int r = 0; r < k; r++) {
            count[r] += left.count[r];
        }

        // Prefixes that contain the whole left part
        // and then some prefix of the right part
        for (int r = 0; r < k; r++) {

            int newRemainder =
                    (left.product * r) % k;

            count[newRemainder] += right.count[r];
        }

        return new Node(product, count);
    }
}
