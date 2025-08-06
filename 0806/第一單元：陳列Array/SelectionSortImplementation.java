import java.util.Arrays;

public class SelectionSortImplementation {
    public static void main(String[] args) {
        int[] data1 = {64, 25, 12, 22, 11};
        int[] data2 = data1.clone(); // 另一份給泡泡排序用

        System.out.println("🔷 原始資料：" + Arrays.toString(data1));

        // ➤ 選擇排序
        System.out.println("\n=== 選擇排序過程 ===");
        selectionSort(data1);

        // ➤ 泡泡排序（比較用）
        System.out.println("\n=== 泡泡排序過程 ===");
        bubbleSort(data2);
    }

    // === 選擇排序 ===
    public static void selectionSort(int[] arr) {
        int comparisons = 0;
        int swaps = 0;
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }

            // 交換
            if (minIdx != i) {
                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
                swaps++;
            }

            System.out.println("第 " + (i + 1) + " 輪結果：" + Arrays.toString(arr));
        }

        System.out.println("總比較次數：" + comparisons);
        System.out.println("總交換次數：" + swaps);
    }

    // === 泡泡排序 ===
    public static void bubbleSort(int[] arr) {
        int comparisons = 0;
        int swaps = 0;
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    // 交換
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }

            System.out.println("第 " + (i + 1) + " 輪結果：" + Arrays.toString(arr));
            if (!swapped) break;
        }

        System.out.println("總比較次數：" + comparisons);
        System.out.println("總交換次數：" + swaps);
    }
}
