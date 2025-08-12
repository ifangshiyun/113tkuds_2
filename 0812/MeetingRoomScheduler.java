// File: MeetingRoomScheduler.java
import java.util.*;

/** 會議室排程：最少會議室數 & 有限會議室下最大總會議時間 */
public class MeetingRoomScheduler {

    /** ============ Part 1. 最少需要幾間會議室 ============ */
    // intervals: int[n][2] = {start, end}
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        // 依開始時間排序
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // 以「結束時間」為鍵的 min-heap
        PriorityQueue<Integer> endHeap = new PriorityQueue<>();

        for (int[] itv : intervals) {
            int start = itv[0], end = itv[1];

            // 若最早結束的會議已在 start 前/同時結束，釋放那個房間
            if (!endHeap.isEmpty() && endHeap.peek() <= start) {
                endHeap.poll();
            }
            // 占用一間房
            endHeap.offer(end);
        }
        // Heap 大小即為同時進行的最大值 = 需要的房間數
        return endHeap.size();
    }

    /** ============ Part 2. 只有 rooms 間會議室，最大化總會議時間 ============ */
    // 回傳選到的總會議時間長度（可改成回傳被選會議清單，這裡先回總長度）
    public static int maxTotalDurationWithRooms(int[][] intervals, int rooms) {
        if (intervals == null || intervals.length == 0 || rooms <= 0) return 0;

        final int n = intervals.length;
        // 事件：time, type(0=end,1=start), id
        // 同時刻「先 end 後 start」=> [start, end) 模型
        class Event {
            int time, type, id;
            Event(int t, int ty, int i) { time = t; type = ty; id = i; }
        }

        int[] start = new int[n], end = new int[n], len = new int[n];
        List<Event> events = new ArrayList<>(n * 2);
        for (int i = 0; i < n; i++) {
            start[i] = intervals[i][0];
            end[i]   = intervals[i][1];
            len[i]   = Math.max(0, end[i] - start[i]);
            events.add(new Event(start[i], 1, i)); // start
            events.add(new Event(end[i],   0, i)); // end
        }
        events.sort((a, b) -> {
            if (a.time != b.time) return Integer.compare(a.time, b.time);
            return Integer.compare(a.type, b.type); // 0(end) 在 1(start) 之前
        });

        // 以「時長」為鍵的 min-heap（保留當前活動中時長最短在頂端，方便超出 rooms 時淘汰）
        class Node {
            int duration, id;
            Node(int d, int i) { duration = d; id = i; }
        }
        PriorityQueue<Node> durHeap = new PriorityQueue<>(Comparator.comparingInt(n1 -> n1.duration));

        boolean[] active = new boolean[n]; // 是否目前被「保留」在排程中（未被淘汰）
        int activeCnt = 0;
        int total = 0;

        // 懶刪除：pop 到遇見仍 active 的節點
        Runnable prune = () -> {
            while (!durHeap.isEmpty() && !active[durHeap.peek().id]) {
                durHeap.poll();
            }
        };

        for (Event e : events) {
            if (e.type == 0) { // end
                int id = e.id;
                if (active[id]) {
                    // 仍在排程中 -> 累計其時長
                    total += len[id];
                    active[id] = false;
                    activeCnt--;
                }
                // 清理堆頂陳舊項
                prune.run();
            } else { // start
                int id = e.id;
                active[id] = true;
                activeCnt++;
                durHeap.offer(new Node(len[id], id));

                // 若同時進行超過 rooms，淘汰總長中最不划算（時長最短）的
                prune.run();
                while (activeCnt > rooms) {
                    Node drop = durHeap.poll();
                    // 可能遇到已非 active 的舊節點，繼續清
                    while (drop != null && !active[drop.id]) {
                        drop = durHeap.poll();
                    }
                    if (drop == null) break;
                    active[drop.id] = false; // 被踢掉，不計入總長度
                    activeCnt--;
                }
            }
        }
        return total;
    }

    /** ============ Demo with samples ============ */
    public static void main(String[] args) {
        // ---- Part 1: 最少會議室數 ----
        int[][] a1 = {{0,30},{5,10},{15,20}};
        int[][] a2 = {{9,10},{4,9},{4,17}};
        int[][] a3 = {{1,5},{8,9},{8,9}};
        System.out.println(minMeetingRooms(a1)); // 2
        System.out.println(minMeetingRooms(a2)); // 2
        System.out.println(minMeetingRooms(a3)); // 2

        // ---- Part 2: 只有 N 間會議室，最大總會議時間 ----
        int[][] b1 = {{1,4},{2,3},{4,6}};
        System.out.println(maxTotalDurationWithRooms(b1, 1)); // 5（選 [1,4] 和 [4,6]）
    }
}
