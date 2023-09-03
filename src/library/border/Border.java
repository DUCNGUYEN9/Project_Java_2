package library.border;

import library.Color.Color;

public class Border {
    public static void borderTable(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(shapeBorder());
        }
        System.out.println();
    }

    public static void borderChooseTable(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(starBorder());
        }
        System.out.println();
    }

    public static void maRuBorder(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(Color.PURPLE_BOLD_BRIGHT + "◯" + Color.RESET);
        }
        System.out.println();
    }

    public static String shapeBorder() {
        return Color.PURPLE_BOLD_BRIGHT + "⊹" + Color.RESET;
    }

    public static String starBorder() {
        return Color.PURPLE_BOLD_BRIGHT + "⊹" + Color.RESET;
    }

    public static void tableEmpty() {
        int n = 38;
        for (int i = 0; i < n; i++) {
            System.out.print(Color.WHITE_BOLD_BRIGHT + "‡");
        }
        System.out.println();
        System.out.println("‡" + Color.RESET + Color.YELLOW_BOLD_BRIGHT +
                "\t\tDanh sách trống !🤷‍♂️\t\t\t " + Color.RESET +
                Color.WHITE_BOLD_BRIGHT + "‡");
//        System.out.println("‡" + Color.RESET + Color.YELLOW_BOLD_BRIGHT +
//                " Vui lòng thêm trước khi sử dụng chức năng này ! " +
//                Color.RESET + Color.WHITE_BOLD_BRIGHT + "‡");
        for (int i = 0; i < n; i++) {
            System.out.print("‡");
        }
        System.out.print(Color.RESET);
        System.out.println();
    }

    public static void isExistTable() {
        int n = 53;
        for (int i = 0; i < n; i++) {
            System.out.print(Color.WHITE_BOLD_BRIGHT + "‡");
        }
        System.out.println();
        System.out.printf("%-1s %-49s %-1s\n", "‡", " ", "‡");
        System.out.println("‡" + Color.RESET + Color.YELLOW_BOLD_BRIGHT +
                "\t\tKhông có kết quả nào cho từ khóa này !🤷‍♂️\t\t" + Color.RESET +
                Color.WHITE_BOLD_BRIGHT + "‡");
        System.out.printf("%-1s %-49s %-1s\n", "‡", " ", "‡");
        for (int i = 0; i < n; i++) {
            System.out.print("‡");
        }
        System.out.print(Color.RESET);
        System.out.println();
    }

}
