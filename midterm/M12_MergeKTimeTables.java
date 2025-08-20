
import java.util.*;

// 限制：K ≤ 5，每份 ≤ 200
// 複雜度分析：
//   每次取出/插入 O(log K)，總元素數 N ≤ K*200。
//   時間 O(N log K)，空間 O(K)。
//   在題目限制下非常快。
public class M12_MergeKTimeTables {
    static class Entry {
        int time;      // 到站時間
        int listIdx;   // 來源列表編號
        int pos;       // 該列表中的位置
        Entry(int t, int l, int p) {
            time = t; listIdx = l; pos = p;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();
        List<int[]> lists = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            int len = sc.nextInt();
            int[] arr = new int[len];
            for (int j = 0; j < len; j++) arr[j] = sc.nextInt();
            lists.add(arr);
        }
        sc.close();

        List<Integer> merged = mergeK(lists);

        for (int i = 0; i < merged.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(merged.get(i));
        }
    }

    // 使用 Min-Heap 進行 K-way merge
    private static List<Integer> mergeK(List<int[]> lists) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Entry> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.time, b.time)
        );

        // 初始：各列表放入第一個元素
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).length > 0) {
                pq.offer(new Entry(lists.get(i)[0], i, 0));
            }
        }

        while (!pq.isEmpty()) {
            Entry e = pq.poll();
            result.add(e.time);
            int nextPos = e.pos + 1;
            if (nextPos < lists.get(e.listIdx).length) {
                pq.offer(new Entry(lists.get(e.listIdx)[nextPos], e.listIdx, nextPos));
            }
        }

        return result;
    }
}

/* Terminal Run Result 
ifangelinetosani@ifangelines-MacBook-Air midterm % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M12_MergeKTimeTables.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M12_MergeKTimeTables
3
3
10 40 90
2
15 35
4
20 25 30 100
10 15 20 25 30 35 40 90 100%    
 */