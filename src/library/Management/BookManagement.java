package library.Management;

import library.Color.Color;
import library.IOFile.IOFile;
import library.border.Border;
import library.entity.Book;
import library.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

import static library.run.LibraryManagement.*;

public class BookManagement {
    public static void bookManagement() {

        boolean exitBook = true;
        do {
            System.out.print(Color.PURPLE_BOLD_BRIGHT +
                    "\n⁜⁜⁜⁜⁜⁜⁜ QUẢN LÝ SÁCH ⁜⁜⁜⁜⁜⁜⁜\n" + Color.CYAN_BOLD_BRIGHT +
                    "⁜\t1. Thêm mới sách                ⁜\n" +
                    "⁜\t2. Cập nhật thông tin sách      ⁜\n" +
                    "⁜\t3. Xóa sách                     ⁜\n" +
                    "⁜\t4. Tìm kiếm sách                ⁜\n" +
                    "⁜\t5. Hiển thị theo nhóm thể loại  ⁜\n" +
                    "⁜\t6. Quay lại                     ⁜\n");
            Border.maRuBorder(38);
            System.out.print(Color.WHITE_BRIGHT + "Lựa chọn của bạn: " + Color.RESET);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        updateBook();
                        break;
                    case 3:
                        deleteBook();
                        break;
                    case 4:
                        searchBook();
                        break;
                    case 5:
                        displayBookToTypeBooks();
                        break;
                    case 6:
                        exitBook = false;
                        break;
                    default:
                        System.err.println("Vui lòng chọn số trong khoảng 1 - 3");
                }
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Lỗi khi nhập kí tự không phải số");
            } catch (Exception exception) {
                System.err.println("Lỗi khi nhập đầu vào");
            }
        } while (exitBook);
    }

    /**
     * addBook(): add book to bookList
     */
    public static void addBook() {
        System.out.print("Nhập số lượng bạn muốn thêm: ");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                Book book = new Book();
                book.input();
                bookList.add(book);
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Lỗi khi nhập kí tự không phải số !");
        } catch (Exception exception) {
            System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
        }
        //write data to file
        IOFile.writeDataToFileBooks(bookList);
    }

    /**
     * updateBook(): update book from bookId
     */
    public static void updateBook() {
        if (bookList.size() > 0) {
            try {
                //print table book
                tableChoose();
                System.out.print(Color.WHITE_BOLD_BRIGHT + "Nhập mã sách bạn muốn cập nhập: " + Color.RESET);
                String editId = scanner.nextLine();
                boolean checkId = false;
                for (Book book : bookList) {
                    if (book.getId().equals(editId)) {
                        book.update();
                        checkId = true;
                        break;
                    }
                }
                if (!checkId) {
                    System.err.println("Mã thể loại bạn nhập không tồn tại");
                } else {
                    System.out.println(Color.GREEN_BOLD_BRIGHT + "Đã cập nhật thành công ✓" + Color.RESET);
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } else {
            //Notification list empty
            Border.tableEmpty();
        }
        //write data to file
        IOFile.writeDataToFileBooks(bookList);
    }

    /**
     * deleteBook():delete book to id
     */
    public static void deleteBook() {
        if (bookList.size() > 0) {
            //print table book
            tableChoose();
            try {
                System.out.print("Nhập mã sách bạn muốn xóa: ");
                String deleteId = scanner.nextLine();
                boolean isExist = false;
                for (int i = 0; i < bookList.size(); i++) {
                    if (bookList.get(i).getId().equals(deleteId)) {
//                        bookList.remove(i);
//                        System.out.println(Color.GREEN_BOLD_BRIGHT + "Mã " + deleteId +
//                                " Đã xóa thành công ✓" + Color.RESET);
//                        isExist = true;
                        boolean exit = true;
                        do {
                            System.out.println(Color.BLUE_BOLD_BRIGHT + "Bạn có chắc chắn xóa không ?" + Color.RESET);
                            System.out.printf("%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
                            try {
                                int number = Integer.parseInt(scanner.nextLine());
                                switch (number) {
                                    case 1:
                                        bookList.remove(i);
                                        System.out.println(Color.GREEN_BOLD_BRIGHT + "Mã " + deleteId +
                                                " Đã xóa thành công ✓" + Color.RESET);
                                        isExist = true;
                                        exit = false;
                                        break;
                                    case 2:
                                        isExist = true;
                                        exit = false;
                                        break;
                                    default:
                                        System.err.println("Vui lòng nhập 1 hoặc 2");
                                }
                            } catch (NumberFormatException nfe) {
                                System.err.println("Lỗi khi nhập kí tự không phải số !");
                            } catch (Exception exception) {
                                System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
                            }
                        } while (exit);
                    }
                }
                if (!isExist) {
                    System.err.println("Mã sách bạn nhập không tồn tại");
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } else {
            //Notification list empty
            Border.tableEmpty();
        }
        //write data to file
        IOFile.writeDataToFileBooks(bookList);
    }

    /**
     * searchBook(): search book to key name
     */
    public static void searchBook() {
        try {
            if (bookList.size() > 0) {
                System.out.print("Nhập từ khóa bạn muốn tìm kiếm: ");
                String keyName = scanner.nextLine();
                List<Book> bookList1 = bookList.stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(keyName.toLowerCase())
                                || book.getAuthor().toLowerCase().contains(keyName.toLowerCase())
                                || book.getPublisher().toLowerCase().contains(keyName.toLowerCase()))
                        .collect(Collectors.toList());
                if (bookList1.size() == 0) {
//                    System.out.println(Color.RED +
//                            "\t\t\t\t\tTiêu đề sách bạn nhập không tồn tại !" + Color.RESET);
                    Border.isExistTable();
                } else {
                    //draw border header
                    Border.borderTable(135);
                    System.out.print(Border.starBorder());
                    System.out.printf(Color.GREEN_BOLD_BRIGHT +
                                    "\t%-15s %-10s %-20s %-15s %-16s %-15s %-22s %s\n", "Key Name",
                            "Mã sách", "Tiêu đề sách", "Tác giả", "Nhà xuất bản",
                            "Năm xuất bản", "Mô tả sách" + Color.RESET, Border.starBorder());
                    System.out.print(Border.starBorder());
                    System.out.printf(Color.YELLOW_BOLD_BRIGHT +
                            "\t%-119s %s\n ", keyName + Color.RESET, Border.starBorder());
                    bookList1.forEach(Book::output);
                    //draw border footer
                    Border.borderTable(135);
                }
            } else {
                //Notification list empty
                Border.tableEmpty();
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
        }
    }

    /**
     * displayBookToTypeBooks(): display info book ang category
     */

    public static void displayBookToTypeBooks() {
        //draw border header
        Border.borderTable(135);
        System.out.print(Border.starBorder());
        System.out.printf(Color.GREEN_BOLD_BRIGHT +
                        "\t%-15s %-10s %-20s %-15s %-16s %-15s %-22s %s \n", "Tên Thể loại",
                "Mã sách", "Tiêu đề sách", "Tác giả", "Nhà xuất bản",
                "Năm xuất bản", "Mô tả sách" + Color.RESET, Border.starBorder());
        int n = categoryList.size() - 1;
        for (Category category : categoryList) {
            System.out.print(Border.starBorder());
            //category book name
            System.out.printf(Color.YELLOW_BOLD_BRIGHT +
                    "\t%-119s %-1s\n", category.getName().toUpperCase() + Color.RESET, Border.starBorder());
            bookList.stream()
                    .filter(book -> book.getCategoryId() == category.getId()).forEach(Book::output);
//            System.out.print(Border.starBorder());
//            if (n > 0) {
//                System.out.println(Color.WHITE_BOLD_BRIGHT+">>>>>>>>>>>"+Color.RESET);
//                n--;
//            }

        }
        //draw border footer
        Border.borderTable(135);

    }

    /**
     * tableChoose(): print choose id in table
     */
    public static void tableChoose() {
        //draw border
        Border.borderChooseTable(40);
        System.out.print(Border.starBorder());
        System.out.printf(Color.GREEN_BOLD_BRIGHT + "\t%-12s %-21s %s\n", "MÃ ID",
                "TÊN SÁCH" + Color.RESET, Border.starBorder());
        for (Book book : bookList) {
            System.out.print(Border.starBorder());
            System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-12s %-21s %s\n",
                    book.getId(), book.getTitle() + Color.RESET, Border.starBorder());
        }
        //draw border
        Border.borderChooseTable(40);
    }

}
