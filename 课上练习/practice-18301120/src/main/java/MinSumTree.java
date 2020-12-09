/**
 * @author paul
 */
public class MinSumTree {
    public static int minSum = Integer.MAX_VALUE;
    public static int getMinSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getMinSum(root.left);
        int right = getMinSum(root.right);
        minSum = Math.min(minSum, left + right + root.value);
        return minSum;
    }
}
