import java.io.*;
import java.util.*;

public class LC28_StrStr_NoticeSearch {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String haystack = br.readLine();
        String needle   = br.readLine();

        if (haystack == null) haystack = "";
        if (needle == null) needle = "";

        System.out.println(strStr(haystack, needle));
    }

    // KMP: Time O(n+m), Space O(m)
    static int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) return 0;
        if (m > n) return -1;

        int[] lps = buildLPS(needle); // longest prefix that is also suffix
        int i = 0, j = 0;             // i over haystack, j over needle

        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++; j++;
                if (j == m) return i - m;  // found
            } else if (j > 0) {
                j = lps[j - 1];            // fallback in needle
            } else {
                i++;                       // move on in haystack
            }
        }
        return -1;
    }

    // Build LPS array for pattern
    static int[] buildLPS(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0;
        for (int i = 1; i < m; ) {
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }
}
