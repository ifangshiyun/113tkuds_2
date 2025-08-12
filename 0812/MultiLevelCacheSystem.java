// File: MultiLevelCacheSystem.java
import java.util.*;

/**
 * Multi-level LRU cache (L1/L2/L3) with heap-based global prioritization.
 * - Each key tracks frequency and last-access time.
 * - After each get/put, we rebuild placement using a MAX-HEAP by "hotness" score:
 *      score = freq * 100 - (now - lastAccess)
 *   Highest scores occupy faster levels first (L1 -> L2 -> L3).
 * - Inside each level we keep LRU order (LinkedHashMap with accessOrder = true).
 */
public class MultiLevelCacheSystem {

    /* ---------- Configuration (can be tweaked) ---------- */
    public static class LevelConf {
        final int capacity;
        final int accessCost; // lower means faster (L1 < L2 < L3)
        LevelConf(int capacity, int accessCost) {
            this.capacity = capacity; this.accessCost = accessCost;
        }
    }

    /* ---------- Cache entry ---------- */
    private static class Entry {
        final int key;
        String value;
        int freq;          // access count
        long lastAccess;   // logical time
        int level;         // 1,2,3 (for display only)

        Entry(int key, String value, long t) {
            this.key = key; this.value = value;
            this.freq = 1; this.lastAccess = t; this.level = 0;
        }
    }

    /* ---------- A single cache level (keeps LRU order) ---------- */
    private static class Level {
        final String name;
        final int capacity;
        final int cost;
        final LinkedHashMap<Integer, Entry> data; // access-order

        Level(String name, int cap, int cost) {
            this.name = name; this.capacity = cap; this.cost = cost;
            this.data = new LinkedHashMap<>(16, 0.75f, true);
        }
        void clear() { data.clear(); }

        void place(Entry e) {
            e.level = name.equals("L1") ? 1 : name.equals("L2") ? 2 : 3;
            data.put(e.key, e);
        }

        List<Integer> keysInLRUOrder() {
            return new ArrayList<>(data.keySet());
        }
    }

    /* ---------- The multi-level cache ---------- */
    public static class MultiLevelCache {
        private final Map<Integer, Entry> map = new HashMap<>();
        private final Level[] levels;
        private long tick = 0; // logical time

        public MultiLevelCache(LevelConf l1, LevelConf l2, LevelConf l3) {
            levels = new Level[] {
                new Level("L1", l1.capacity, l1.accessCost),
                new Level("L2", l2.capacity, l2.accessCost),
                new Level("L3", l3.capacity, l3.accessCost)
            };
        }

        /* ---- Public API ---- */

        public String get(int key) {
            Entry e = map.get(key);
            if (e == null) return null;
            touch(e);
            rebalance(); // move item upward if it becomes hotter
            return e.value;
        }

        public void put(int key, String value) {
            Entry e = map.get(key);
            if (e == null) {
                e = new Entry(key, value, ++tick);
                map.put(key, e);
            } else {
                e.value = value;
                touch(e);
            }
            rebalance();
        }

        /* ---- Debug helpers for sample output ---- */
        public String snapshot() {
            StringBuilder sb = new StringBuilder();
            for (Level lv : levels) {
                sb.append(lv.name).append(": ").append(levelList(lv)).append("  ");
            }
            return sb.toString();
        }

        private String levelList(Level lv) {
            List<Integer> ks = lv.keysInLRUOrder();
            return ks.toString();
        }

        /* ---- Internal helpers ---- */

        private void touch(Entry e) {
            e.freq++;
            e.lastAccess = ++tick;
        }

        // Reassign items to levels using a MAX-HEAP by hotness score.
        private void rebalance() {
            // Build max-heap of all entries
            PriorityQueue<Entry> pq = new PriorityQueue<>((a, b) -> {
                long sa = score(a), sb = score(b);
                if (sa != sb) return Long.compare(sb, sa);  // higher score first
                return Long.compare(b.lastAccess, a.lastAccess); // more recent first
            });
            pq.addAll(map.values());

            // Clear all levels
            for (Level lv : levels) lv.clear();

            // Fill levels by priority
            for (Level lv : levels) {
                int slots = lv.capacity;
                while (slots > 0 && !pq.isEmpty()) {
                    Entry e = pq.poll();
                    lv.place(e);
                    slots--;
                }
            }

            // Items beyond total capacity (if ever configured that way) would be dropped:
            while (!pq.isEmpty()) {
                Entry drop = pq.poll();
                map.remove(drop.key);
            }
        }

        private long score(Entry e) {
            // Higher freq and more recent → higher priority to sit in faster level
            // Weight 100 keeps freq dominant while allowing recency to break ties gently.
            return (long) e.freq * 100L - (tick - e.lastAccess);
        }

        /* ---- Introspection (optional) ---- */
        public Map<Integer, Integer> keyToLevel() {
            Map<Integer, Integer> res = new HashMap<>();
            for (Level lv : levels) {
                for (Entry e : lv.data.values()) res.put(e.key, e.level);
            }
            return res;
        }
    }

    /* ================= Demo with the handout’s scenario ================= */
    public static void main(String[] args) {
        // Design considerations (from prompt):
        // L1: cap=2, cost=1; L2: cap=5, cost=3; L3: cap=10, cost=10
        MultiLevelCache cache = new MultiLevelCache(
            new LevelConf(2, 1),
            new LevelConf(5, 3),
            new LevelConf(10, 10)
        );

        cache.put(1, "A"); cache.put(2, "B"); cache.put(3, "C");
        System.out.println("// After put 1,2,3");
        System.out.println(cache.snapshot()); // Expected shape: L1 holds the two hottest, L2 keeps the rest

        cache.get(1); cache.get(1); cache.get(2);
        System.out.println("// After get(1), get(1), get(2) – frequently accessed keys move up");
        System.out.println(cache.snapshot()); // Expected: L1 contains [1,2], L2 contains [3]

        cache.put(4, "D"); cache.put(5, "E"); cache.put(6, "F");
        System.out.println("// After put 4,5,6 (distribution determined by access hotness)");
        System.out.println(cache.snapshot());
    }
}
