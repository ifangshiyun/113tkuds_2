import java.util.*;

// 複雜度：O(n log K)，空間 O(K)
public class M03_TopKConvenience {
    static class Item {
        String name; int qty;
        Item(String n, int q) { name = n; qty = q; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), K = sc.nextInt();
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int qty = sc.nextInt();
            items.add(new Item(name, qty));
        }
        sc.close();

        PriorityQueue<Item> pq = new PriorityQueue<>((a, b) -> {
            if (a.qty != b.qty) return a.qty - b.qty; // 小量在前
            return a.name.compareTo(b.name);
        });

        for (Item it : items) {
            if (pq.size() < K) pq.offer(it);
            else if (better(it, pq.peek())) {
                pq.poll(); pq.offer(it);
            }
        }

        List<Item> result = new ArrayList<>(pq);
        result.sort((a, b) -> {
            if (a.qty != b.qty) return b.qty - a.qty;
            return a.name.compareTo(b.name);
        });

        for (Item it : result) {
            System.out.println(it.name + " " + it.qty);
        }
    }

    private static boolean better(Item a, Item b) {
        if (a.qty != b.qty) return a.qty > b.qty;
        return a.name.compareTo(b.name) < 0;
    }
}

/* Terminal Run Result
ifangelinetosani@ifangelines-MacBook-Air midterm % cd /Users/ifangelinetosani/Documents/GitHub/113tkuds_2/midterm
ifangelinetosani@ifangelines-MacBook-Air midterm % javac M03_TopKConvenience.java
ifangelinetosani@ifangelines-MacBook-Air midterm % java M03_TopKConvenience
6 3
茶葉蛋 120
關東煮 80
御飯糰 150
可樂 90
便當 150
咖啡 110
便當 150
御飯糰 150
茶葉蛋 120
 */