class Solution {
    // Bit masks for rows, cols, and 3x3 boxes. For a digit d (1..9), we use bit (d-1).
    private int[] row = new int[9], col = new int[9], box = new int[9];
    private int[] er = new int[81], ec = new int[81]; // positions of empty cells
    private int empties = 0;
    private static final int FULL = 0x1FF; // bits 0..8 set (digits 1..9 allowed)

    public void solveSudoku(char[][] board) {
        // Initialize masks and collect empty cells.
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char ch = board[r][c];
                if (ch == '.') {
                    er[empties] = r;
                    ec[empties] = c;
                    empties++;
                } else {
                    int d = ch - '0';          // 1..9
                    int bit = 1 << (d - 1);    // bit 0..8
                    int b = (r / 3) * 3 + (c / 3);
                    row[r] |= bit;
                    col[c] |= bit;
                    box[b] |= bit;
                }
            }
        }
        dfs(board, 0);
    }

    // Backtracking with MRV (choose the next cell with the fewest candidates)
    private boolean dfs(char[][] board, int idx) {
        if (idx == empties) return true; // all filled

        // Choose the best cell (minimum remaining values)
        int best = -1, bestMask = 0, minCount = 10;
        for (int i = idx; i < empties; i++) {
            int r = er[i], c = ec[i], b = (r / 3) * 3 + (c / 3);
            int mask = FULL ^ (row[r] | col[c] | box[b]); // available digits for (r,c)
            int cnt = Integer.bitCount(mask);
            if (cnt < minCount) {
                minCount = cnt;
                best = i;
                bestMask = mask;
                if (cnt == 1) break; // cannot do better
            }
        }
        if (minCount == 0) return false; // dead end

        // Place the chosen cell at position idx (swap in arrays)
        swap(idx, best);

        int r = er[idx], c = ec[idx], b = (r / 3) * 3 + (c / 3);
        int mask = bestMask;

        // Try each candidate digit (iterate bits)
        while (mask != 0) {
            int bit = mask & -mask; // lowest set bit
            mask -= bit;
            int d = Integer.numberOfTrailingZeros(bit) + 1; // 1..9

            // Set
            board[r][c] = (char) ('0' + d);
            row[r] |= bit; col[c] |= bit; box[b] |= bit;

            if (dfs(board, idx + 1)) return true;

            // Unset (backtrack)
            board[r][c] = '.';
            row[r] &= ~bit; col[c] &= ~bit; box[b] &= ~bit;
        }

        // Restore order (swap back) for completeness (not strictly necessary)
        swap(idx, best);
        return false;
    }

    private void swap(int i, int j) {
        if (i == j) return;
        int tr = er[i], tc = ec[i];
        er[i] = er[j]; ec[i] = ec[j];
        er[j] = tr;    ec[j] = tc;
    }
}

/*
Explanation (simple):
- Use bit masks to track used digits in each row, column, and 3×3 box.
  - For digit d (1..9) we set bit (d-1).
  - FULL = 0x1FF (binary 111111111) means all digits are possible.
- Preprocess:
  - Fill row[], col[], box[] masks from the given digits.
  - Collect coordinates of empty cells into er[], ec[].
- Backtracking with MRV:
  - At each step, among remaining empty cells choose the one with the fewest valid candidates
    (Minimum Remaining Values heuristic) to reduce branching.
  - The candidate mask for cell (r,c) is: FULL ^ (row[r] | col[c] | box[b]).
  - Try each available digit (iterate bits in the mask), place it, update masks, and recurse.
  - If a branch fails, undo the placement (backtrack) and try the next digit.
- Guaranteed single solution → recursion will eventually fill all cells.
- Time: Practical backtracking with strong pruning (MRV + bit masks).
- Space: O(1) extra (fixed arrays and masks); board is modified in place.
*/
