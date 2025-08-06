import java.util.*;

public class RecursiveTreePreview {
    public static void main(String[] args) {
        // 1. 模擬檔案樹
        FileNode root = new FileNode("root", false);
        root.children.add(new FileNode("file1.txt", true));
        FileNode subFolder = new FileNode("folder1", false);
        subFolder.children.add(new FileNode("file2.txt", true));
        subFolder.children.add(new FileNode("file3.txt", true));
        root.children.add(subFolder);

        System.out.println("1. 總檔案數量：" + countFiles(root));

        // 2. 多層選單
        Menu menu = new Menu("Main");
        Menu sub1 = new Menu("File");
        Menu sub2 = new Menu("Edit");
        sub1.submenus.add(new Menu("New"));
        sub1.submenus.add(new Menu("Open"));
        sub2.submenus.add(new Menu("Cut"));
        sub2.submenus.add(new Menu("Paste"));
        menu.submenus.add(sub1);
        menu.submenus.add(sub2);

        System.out.println("\n2. 多層選單列印：");
        printMenu(menu, 0);

        // 3. 巢狀陣列展平
        List<Object> nested = Arrays.asList(1, Arrays.asList(2, Arrays.asList(3, 4)), 5);
        List<Integer> flat = new ArrayList<>();
        flatten(nested, flat);
        System.out.println("\n3. 展平後的陣列：" + flat);

        // 4. 最大深度計算
        List<Object> nestedDepth = Arrays.asList(1, Arrays.asList(2, Arrays.asList(3, Arrays.asList(4))));
        System.out.println("\n4. 最大巢狀深度：" + maxDepth(nestedDepth));
    }

    // 1. 計算所有檔案數（非資料夾）
    static class FileNode {
        String name;
        boolean isFile;
        List<FileNode> children = new ArrayList<>();

        FileNode(String name, boolean isFile) {
            this.name = name;
            this.isFile = isFile;
        }
    }

    public static int countFiles(FileNode node) {
        if (node.isFile) return 1;
        int count = 0;
        for (FileNode child : node.children) {
            count += countFiles(child);
        }
        return count;
    }

    // 2. 遞迴列印多層選單
    static class Menu {
        String title;
        List<Menu> submenus = new ArrayList<>();

        Menu(String title) {
            this.title = title;
        }
    }

    public static void printMenu(Menu menu, int indent) {
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println(menu.title);
        for (Menu submenu : menu.submenus) {
            printMenu(submenu, indent + 1);
        }
    }

    // 3. 巢狀陣列展平
    public static void flatten(List<?> input, List<Integer> output) {
        for (Object element : input) {
            if (element instanceof Integer) {
                output.add((Integer) element);
            } else if (element instanceof List<?>) {
                flatten((List<?>) element, output);
            }
        }
    }

    // 4. 計算巢狀陣列最大深度
    public static int maxDepth(List<?> input) {
        int max = 1;
        for (Object element : input) {
            if (element instanceof List<?>) {
                max = Math.max(max, 1 + maxDepth((List<?>) element));
            }
        }
        return max;
    }
}
