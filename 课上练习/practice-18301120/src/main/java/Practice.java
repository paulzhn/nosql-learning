/**
 * @author paul
 */
public class Practice {
    public static String[] reRankTwoPointers(String[] input) {
        if (input == null || input.length == 0) {
            return new String[0];
        }
        int i = 0;
        int left = 0;
        int right = input.length - 1;
        while (i <= right) {
            if ("r".equals(input[i])) {
                String t = input[i];
                input[i] = input[left];
                input[left] = t;
                i++;
                left++;
            } else if ("g".equals(input[i])) {
                i++;
            } else if ("b".equals(input[i])) {
                String t1 = input[i];
                input[i] = input[right];
                input[right] = t1;
                right--;
            }
        }
        return input;
    }
}
