package odtest;

import java.util.*;

public class Test001 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numLength = scanner.nextInt();
        //int[] nums = {1, 2, 3};
        int[] nums = new int[numLength];
        for (int i = 0; i < numLength; i++) {
            nums[i] = scanner.nextInt();
        }

        List<Integer> paths = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        iterate(nums, paths, result, 0);
        System.out.print("[");
        for (int j = 0; j < result.size(); j++) {
            List<Integer> item = result.get(j);
            System.out.print("[");
            for (int i = 0; i < item.size(); i++) {
                System.out.print(item.get(i));
                if (i < item.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.print("]");
            if (j < result.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");
        System.out.println("");
        System.out.println("length=" + result.size());
    }

    public static void iterate(int[] nums, List<Integer> paths, List<List<Integer>> result, int level) {
        if (level >= nums.length) {
            result.add(new ArrayList<>(paths));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!paths.contains(nums[i])) {
                paths.add(nums[i]);
                iterate(nums, paths, result, level + 1);
                paths.remove(paths.size() - 1);
            }
        }
    }
}
