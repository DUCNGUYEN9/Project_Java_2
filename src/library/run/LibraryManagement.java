package library.run;

import library.Color.Color;
import library.IOFile.IOFile;
import library.Management.BookManagement;
import library.Management.CategoryManagement;
import library.border.Border;
import library.entity.Book;
import library.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagement {
    public static List<Category> categoryList = new ArrayList<>();
    public static List<Book> bookList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        categoryList = IOFile.readDataFromFileCategories();
        bookList = IOFile.readDataFromFileBooks();
        do {
            System.out.print(Color.PURPLE_BOLD_BRIGHT +
                    "\n⁜⁜⁜⁜⁜⁜ QUẢN LÝ THƯ VIỆN ⁜⁜⁜⁜⁜⁜\n" + Color.CYAN_BOLD_BRIGHT +
                    "⁜  \t1. Quản lý Thể loại         ⁜\n" +
                    "⁜  \t2. Quản lý Sách             ⁜\n" +
                    "⁜  \t3. Thoát                    ⁜\n" + Color.RESET);
            Border.maRuBorder(38);
            System.out.print(Color.WHITE_BRIGHT + "Lựa chọn của bạn: " + Color.RESET);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        CategoryManagement.categoryManagement();
                        break;
                    case 2:
                        if (categoryList.size() > 0) {
                            BookManagement.bookManagement();
                        } else {
                            System.out.println(Color.RED_BOLD +
                                    " Hiện tại danh sách thể loại trống\n  Vui lòng thêm thể loại trước !"
                                    + Color.RESET);
                        }
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println(Color.RED + "Vui lòng chọn số trong khoảng 1 - 3 !\n" + Color.RESET);
                }
            } catch (NumberFormatException numberFormatException) {
                System.out.println(Color.RED + "Lỗi khi nhập kí tự không phải số !\n" + Color.RESET);
            } catch (Exception exception) {
                System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } while (true);
    }
}
