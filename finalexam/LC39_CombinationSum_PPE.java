import java.io.*;
import java.util.*;

public class LC39_CombinationSum_PPE {
    static List<List<Integer>> res = new ArrayList<>();
    static int[] candidates;
    static int target;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] first = br.readLine().trim().split("\\s+");
        int n = Integer.parseInt(first[0]);
        target = Integer.parseInt(first[1]);

        String[] parts = br.readLine().trim().split("\\s+");
        candidates = new int[n];
        for (int i = 0; i < n; i++) candidates[i] = Integer.parseInt(parts[i]);

        Arrays.sort(candidates); // sort to keep ascending order

        backtrack(new ArrayList<>(), 0, target);

        // print results
        for (List<Integer> comb : res) {
            for (int i = 0; i < comb.size(); i++) {
                System.out.print(comb.get(i));
                if (i < comb.size() - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }

    static void backtrack(List<Integer> cur, int start, int remain) {
        if (remain == 0) {
            res.add(new ArrayList<>(cur));
            return;
        }
        if (remain < 0) return;

        for (int i = start; i < candidates.length; i++) {
            cur.add(candidates[i]);
            // allow reuse → pass same i
            backtrack(cur, i, remain - candidates[i]);
            cur.remove(cur.size() - 1);
        }
    }
}
