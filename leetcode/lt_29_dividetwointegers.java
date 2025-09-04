class Solution {
    public int divide(int dividend, int divisor) {
        // Edge case: overflow when dividing INT_MIN by -1
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // Work with positive longs to avoid overflow during shifts
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);

        // Determine the sign of the result (negative if exactly one is negative)
        int sign = ((dividend ^ divisor) < 0) ? -1 : 1;

        long q = 0; // quotient we build bit by bit

        // Try to subtract (b << i) from 'a' from high bit to low bit.
        // Use (a >> i) >= b to avoid using multiplication.
        for (int i = 31; i >= 0; i--) {
            if ((a >> i) >= b) {
                q += 1L << i;     // set the i-th bit of the quotient
                a -= b << i;      // subtract that chunk from the remainder
            }
        }

        q = (sign == 1) ? q : -q;

        // Clamp to 32-bit range (problem requires saturating)
        if (q > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (q < Integer.MIN_VALUE) return Integer.MIN_VALUE;

        return (int) q;
    }
}

/*
Explanation (simple):
- We cannot use *, /, or %, so we build the quotient using bit shifts.
- Convert dividend and divisor to positive longs (abs) to avoid overflow.
- From bit 31 down to 0, check if divisor * 2^i fits into the current remainder:
  - Instead of multiplying, use (a >> i) >= b which is equivalent to (b << i) <= a.
  - If it fits, set that bit in the quotient and subtract that amount from the remainder.
- Apply the correct sign at the end. Handle the special overflow case INT_MIN / -1.
- Time: O(32) ~ O(1). Space: O(1).
*/
