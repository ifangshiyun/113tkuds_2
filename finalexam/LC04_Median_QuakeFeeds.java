import java.io.*;
import java.util.*;

public class LC04_Median_QuakeFeeds {

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        int m = fs.nextInt();

        double[] A = new double[n];
        double[] B = new double[m];

        for (int i = 0; i < n; i++) A[i] = fs.nextDouble();
        for (int i = 0; i < m; i++) B[i] = fs.nextDouble();

        double median = findMedianSortedArrays(A, B);

        // 輸出保留 1 位小數
        System.out.printf("%.1f%n", median);
    }

    // O(log(min(n,m))) median finder
    static double findMedianSortedArrays(double[] A, double[] B) {
        if (A.length > B.length) return findMedianSortedArrays(B, A); // 保證 A 是較短的

        int n = A.length, m = B.length;
        int totalLeft = (n + m + 1) / 2;

        int lo = 0, hi = n;
        while (lo <= hi) {
            int i = (lo + hi) / 2;        // cut A
            int j = totalLeft - i;        // cut B

            double Aleft  = (i == 0) ? Double.NEGATIVE_INFINITY : A[i - 1];
            double Aright = (i == n) ? Double.POSITIVE_INFINITY : A[i];
            double Bleft  = (j == 0) ? Double.NEGATIVE_INFINITY : B[j - 1];
            double Bright = (j == m) ? Double.POSITIVE_INFINITY : B[j];

            if (Aleft <= Bright && Bleft <= Aright) {
                // perfect cut
                if (((n + m) % 2) == 1) {
                    return Math.max(Aleft, Bleft); // odd
                } else {
                    return (Math.max(Aleft, Bleft) + Math.min(Aright, Bright)) / 2.0;
                }
            } else if (Aleft > Bright) {
                hi = i - 1;
            } else {
                lo = i + 1;
            }
        }
        throw new RuntimeException("Should not reach here");
    }

    // Simple fast scanner
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) { in = is; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) != -1 && c <= ' ');
            if (c == -1) return null;
            do {
                sb.append((char)c);
            } while ((c = read()) != -1 && c > ' ');
            return sb.toString();
        }

        int nextInt() throws IOException { return Integer.parseInt(next()); }
        double nextDouble() throws IOException { return Double.parseDouble(next()); }
    }
}
