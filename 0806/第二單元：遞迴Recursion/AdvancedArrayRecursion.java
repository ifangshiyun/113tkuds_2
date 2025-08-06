import java.util.Arrays;

public class AdvancedArrayRecursion {
    public static void main(String[] args) {
        int[] arr = {7, 2, 1, 6, 8, 5, 3, 4};
        int[] sorted1 = {1, 3, 5, 7};
        int[] sorted2 = {2, 4, 6, 8};

        // 1. 遞迴實作快速排序
        System.out.println("1. 快速排序結果：");
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

        // 2. 遞迴合併兩個已排序的陣列
        System.out.println("\n2. 合併排序陣列：");
        int[] merged = mergeSortedArrays(sorted1, sorted2, 0, 0, new int[sorted1.length + sorted2.length], 0);
        System.out.println(Arrays.toString(merged));

        // 3. 遞迴尋找第 k 小元素
        System.out.println("\n3. 陣列中第 4 小的元素：");
        int kth = findKthSmallest(arr.clone(), 0, arr.length - 1, 4);
        System.out.println(kth);

        // 4. 遞迴檢查是否存在子序列總和為 target
        System.out.println("\n4. 是否存在子序列和為 15：");
        boolean exists = subsetSum(arr, 0, 15);
        System.out.println(exists ? "存在" : "不存在");
    }

    // 1. 快速排序
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(arr, low, high);
            quickSort(arr, low, pivotIdx - 1);
            quickSort(arr, pivotIdx + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 2. 合併已排序陣列（遞迴）
    public static int[] mergeSortedArrays(int[] a, int[] b, int i, int j, int[] result, int k) {
        if (i == a.length && j == b.length) {
            return result;
        }

        if (i == a.length) {
            result[k] = b[j];
            return mergeSortedArrays(a, b, i, j + 1, result, k + 1);
        }

        if (j == b.length) {
            result[k] = a[i];
            return mergeSortedArrays(a, b, i + 1, j, result, k + 1);
        }

        if (a[i] < b[j]) {
            result[k] = a[i];
            return mergeSortedArrays(a, b, i + 1, j, result, k + 1);
        } else {
            result[k] = b[j];
            return mergeSortedArrays(a, b, i, j + 1, result, k + 1);
        }
    }

    // 3. 遞迴找第 k 小元素 (利用 quickselect)
    public static int findKthSmallest(int[] arr, int left, int right, int k) {
        if (left <= right) {
            int pivotIndex = partition(arr, left, right);
            int rank = pivotIndex - left + 1;

            if (rank == k)
                return arr[pivotIndex];
            else if (rank > k)
                return findKthSmallest(arr, left, pivotIndex - 1, k);
            else
                return findKthSmallest(arr, pivotIndex + 1, right, k - rank);
        }
        return -1;
    }

    // 4. 檢查是否有子序列總和為 target
    public static boolean subsetSum(int[] arr, int index, int target) {
        if (target == 0) return true;
        if (index == arr.length || target < 0) return false;

        return subsetSum(arr, index + 1, target - arr[index]) || subsetSum(arr, index + 1, target);
    }
}
