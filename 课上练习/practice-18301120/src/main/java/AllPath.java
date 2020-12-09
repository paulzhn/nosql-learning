import java.util.ArrayList;
import java.util.List;

/**
 * @author paul
 */
public class AllPath {
    public static List<String> findPaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null) {
            return paths;
        }
        List<String> leftPaths = findPaths(root.left);
        List<String> rightPaths = findPaths(root.right);
        for (String leftPath : leftPaths) {
            paths.add(root.value + "->" + leftPath);
        }
        for (String rightPath : rightPaths) {
            paths.add(root.value + "->" + rightPath);
        }
        if (paths.isEmpty()) {
            paths.add(Integer.toString(root.value));
        }
        return paths;
    }

}