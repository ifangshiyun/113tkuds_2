import java.util.*;

public class NumberArrayProcessor {
    public static void main(String[] args) {
        int[] array1 = {3, 1, 4, 4, 2, 3, 5, 1};
        int[] sortedArray1 = {1, 3, 5, 7};
        int[] sortedArray2 = {2, 4, 6, 8};

        // 1. 移除重複元素
        System.out.println("1. 移除重複元素：");
        int[] unique = removeDuplicates(array1);
        System.out.println(Arrays.toString(unique));

        // 2. 合併兩個已排序陣列
        System.out.println("\n2. 合併兩個已排序陣列：");
        int[] merged = mergeSortedArrays(sortedArray1, sortedArray2);
        System.out.println(Arrays.toString(merged));

        // 3. 找出出現頻率最高的元素
        System.out.println("\n3. 陣列中出現最多次的元素：");
        int mostFrequent = findMostFrequent(array1);
        System.out.println("出現最多次的數字是：" + mostFrequent);

        // 4. 將陣列分成兩個相等或接近的子陣列
        System.out.println("\n4. 將陣列平均分組：");
        splitArrayEvenly(array1);
    }

    // 1️⃣ 移除重複元素
    public static int[] removeDuplicates(int[] array) {
        Set<Integer> set = new LinkedHashSet<>();
        for (int num : array) {
            set.add(num);
        }
        return set.stream().mapToInt(Integer::intValue).toArray();
    }

    // 2️⃣ 合併兩個排序好的陣列
    public static int[] mergeSortedArrays(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];
        return result;
    }

    // 3️⃣ 找出最多次的元素
    public static int findMostFrequent(int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : array) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int maxFreq = 0;
        int result = array[0];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                result = entry.getKey();
            }
        }
        return result;
    }

    // 4️⃣ 平均分配陣列成兩組
    public static void splitArrayEvenly(int[] array) {
        Arrays.sort(array);
        List<Integer> group1 = new ArrayList<>();
        List<Integer> group2 = new ArrayList<>();
        int sum1 = 0, sum2 = 0;

        for (int i = array.length - 1; i >= 0; i--) {
            if (sum1 <= sum2) {
                group1.add(array[i]);
                sum1 += array[i];
            } else {
                group2.add(array[i]);
                sum2 += array[i];
            }
        }

        System.out.println("子陣列 1：" + group1 + " ➤ 總和 = " + sum1);
        System.out.println("子陣列 2：" + group2 + " ➤ 總和 = " + sum2);
    }
}
