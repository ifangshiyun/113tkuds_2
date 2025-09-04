class Solution {
   public int[] twoSum(int[] nums, int target) {
       // 使用 HashMap 儲存數值與索引，加速查找
       Map<Integer, Integer> map = new HashMap<>();
       for (int i = 0; i < nums.length; i++) {
           int complement = target - nums[i];
           if (map.containsKey(complement)) {
               // 找到符合條件的組合，回傳索引
               return new int[] { map.get(complement), i };
           }
           map.put(nums[i], i);
       }
       return new int[] {}; // 沒找到則回傳空陣列
   }
}
