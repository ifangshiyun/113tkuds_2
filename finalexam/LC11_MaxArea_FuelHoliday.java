import java.io.*;
import java.util.*;

public class LC11_MaxArea_FuelHoliday {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        String[] parts = br.readLine().trim().split("\\s+");

        int[] h = new int[n];
        for (int i = 0; i < n; i++) h[i] = Integer.parseInt(parts[i]);

        System.out.println(maxArea(h));
    }

    // Two-pointer method
    // Time: O(n), Space: O(1)
    static long maxArea(int[] h) {
        int l = 0, r = h.length - 1;
        long best = 0;

        while (l < r) {
            long width = r - l;
            long height = Math.min(h[l], h[r]);
            best = Math.max(best, width * height);

            // move the shorter edge inward
            if (h[l] < h[r]) {
                l++;
            } else {
                r--;
            }
        }
        return best;
    }
}
