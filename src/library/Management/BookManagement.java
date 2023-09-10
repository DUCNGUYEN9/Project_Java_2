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
                        printErrRed("Vui lòng chọn số trong khoảng 1 - 3");
                }
            } catch (NumberFormatException numberFormatException) {
                printErrRed("Lỗi khi nhập kí tự không phải số");
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào");
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
                System.out.println(Color.GREEN_UNDERLINED
                        + "Nhập thông tin sách thứ " + (i + 1) + Color.RESET);
                book.input();
                bookList.add(book);
            }
        } catch (NumberFormatException nfe) {
            printErrRed("Lỗi khi nhập kí tự không phải số !");
        } catch (Exception exception) {
            printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
        }
        //write data to file
        IOFile.writeDataToFileBooks(bookList);
    }

    /**
     * updateBook(): update book from bookId
     */
    public static void updateBook() {
        if (bookList.size() > 0) {
            while (true) {
                try {
                    //print table book
                    tableChoose();
                    System.out.print(Color.WHITE_BOLD_BRIGHT + "Nhập mã sách bạn muốn cập nhập: " + Color.RESET);
                    String editId = scanner.nextLine();
                    boolean checkId = false;
                    for (Book book : bookList) {
                        if (book.getId().equals(editId)) {
                            Border.borderTable(140);
                            System.out.print(Border.starBorder());
                            System.out.printf(Color.GREEN_BOLD_BRIGHT +
                                            "\t%-15s %-10s %-20s %-20s %-16s %-15s %-22s %s\n", "Cập nhật",
                                    "Mã sách", "Tiêu đề sách", "Tác giả", "Nhà xuất bản",
                                    "Năm xuất bản", "Mô tả sách" + Color.RESET, Border.starBorder());
                            book.output();
                            //draw border footer
                            Border.borderTable(140);
                            book.update();
                            checkId = true;
                            break;
                        }
                    }
                    if (!checkId) {
                        printErrRed("Mã thể loại bạn nhập không tồn tại !\n");
                    } else {
                        System.out.println(Color.GREEN_BOLD_BRIGHT + "Đã cập nhật thành công ✓" + Color.RESET);
                        break;
                    }
                } catch (Exception e) {
                    printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
                }
            }
        } else {
            printErrRed(" Danh Sách trống không thể cập nhật !");
        }
        //write data to file
        IOFile.writeDataToFileBooks(bookList);
    }

    /**
     * deleteBook():delete book to id
     */
    public static void deleteBook() {
        if (bookList.size() > 0) {
            while (true) {
                //print table book
                tableChoose();
                System.out.print(Color.WHITE_BOLD_BRIGHT + "Nhập mã sách bạn muốn xóa: " + Color.RESET);
                String deleteId = scanner.nextLine();
                boolean isExist = false;
                for (int i = 0; i < bookList.size(); i++) {
                    if (bookList.get(i).getId().equals(deleteId)) {
                        boolean exit = true;
                        do {
                            System.out.println(Color.BLUE_BOLD_BRIGHT
                                    + "Bạn có chắc chắn xóa không ?" + Color.RESET);
                            System.out.printf("\t%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
                            try {
                                int number = Integer.parseInt(scanner.nextLine());
                                switch (number) {
                                    case 1:
                                        bookList.remove(i);
                                        System.out.println(Color.GREEN_BOLD_BRIGHT + "Đã xóa mã " + Color.RESET
                                                + Color.YELLOW_BOLD_BRIGHT + deleteId + Color.RESET
                                                + Color.GREEN_BOLD_BRIGHT + " thành công ✓" + Color.RESET);
                                        isExist = true;
                                        exit = false;
                                        break;
                                    case 2:
                                        System.out.println(Color.YELLOW_BOLD + "Đã hủy xóa ✖️" + Color.RESET);
                                        isExist = true;
                                        exit = false;
                                        break;
                                    default:
                                        printErrRed("Vui lòng nhập 1 hoặc 2");
                                }
                            } catch (NumberFormatException nfe) {
                                printErrRed("Lỗi khi nhập kí tự không phải số !");
                            } catch (Exception exception) {
                                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
                            }
                        } while (exit);
                    }
                }
                if (!isExist) {
                    printErrRed("Mã sách bạn nhập không tồn tại !\n");
                } else {
                    break;
                }
            }
        } else {
            printErrRed(" Danh Sách trống không thể xóa !");
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
                while (true) {
                    System.out.print("Nhập từ khóa bạn muốn tìm kiếm: ");
                    String keyName = scanner.nextLine();
                    List<Book> bookList1 = bookList.stream()
                            .filter(book -> book.getTitle().toLowerCase().contains(keyName.toLowerCase())
                                    || book.getAuthor().toLowerCase().contains(keyName.toLowerCase())
                                    || book.getPublisher().toLowerCase().contains(keyName.toLowerCase()))
                            .collect(Collectors.toList());
                    if (bookList1.isEmpty()) {
                        System.out.println(Color.RED +
                                " Không có kết quả cho từ khóa " + Color.RESET
                                + Color.WHITE_BOLD_BRIGHT + keyName + Color.RESET);
                        System.out.println();
                    } else {
                        //draw border header
                        Border.borderTable(140);
                        printTitleTable("Key Name");
                        System.out.print(Border.starBorder());
                        System.out.printf(Color.YELLOW_BOLD_BRIGHT +
                                        "\t%-19s %-11s %-21s %-21s %-17s %-16s %-20s %s\n"
                                , keyName + Color.RESET, "⁎⁎⁎", "⁎⁎⁎", "⁎⁎⁎", "⁎⁎⁎⁎", "⁎⁎⁎⁎", "⁎⁎⁎⁎⁎⁎",
                                Border.starBorder());
                        bookList1.forEach(Book::output);
                        //draw border footer
                        Border.borderTable(140);
                        break;
                    }
                }
            } else {
                printErrRed(" Danh Sách trống không thể tìm kiếm !");
            }
        } catch (Exception e) {
            printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
        }
    }


    /**
     * displayBookToTypeBooks(): display info book ang category
     */

    public static void displayBookToTypeBooks() {
        System.out.println(Color.CYAN_BOLD_BRIGHT
                + "\t\t\t\t\t\t\t\t\t\t ◯◯◯ BẢNG HIỆN THỊ THEO NHÓM THỂ LOẠI ◯◯◯" + Color.RESET);
        //draw border header
        Border.borderTable(140);
        //print title table
        printTitleTable("Tên Thể loại");
        for (Category category : categoryList) {
            String longType = category.getName();
            String[] typeLines = longType.split("(?<=.{" + 13 + "})(?=\\s)", 2);
            if (longType.length() < 16) {
                //category book name
                printTableTypeBook(typeLines[0]);
            } else {
                for (String typeLine : typeLines) {
                    //category book name
                    printTableTypeBook(typeLine);
                }
            }
            bookList.stream()
                    .filter(book -> book.getCategoryId() == category.getId()).forEach(Book::output);
        }
        //draw border footer
        Border.borderTable(140);
    }

    /**
     * print name title
     */
    public static void printTitleTable(String name) {
        System.out.print(Border.starBorder());
        System.out.printf(Color.GREEN_BOLD_BRIGHT +
                        "\t%-15s %-10s %-20s %-20s %-16s %-15s %-22s %s \n", name,
                "Mã sách", "Tiêu đề sách", "Tác giả", "Nhà xuất bản",
                "Năm xuất bản", "Mô tả sách" + Color.RESET, Border.starBorder());
    }

    /**
     * @param typeLine line of category book name
     */
    public static void printTableTypeBook(String typeLine) {
        System.out.print(Border.starBorder());
        System.out.printf(Color.YELLOW_BOLD_BRIGHT +
                        "\t%-19s %-11s %-21s %-21s %-17s %-16s %-20s %s\n"
                , typeLine.toUpperCase().trim() + Color.RESET,
                "⁎⁎⁎", "⁎⁎⁎", "⁎⁎⁎", "⁎⁎⁎⁎", "⁎⁎⁎⁎", "⁎⁎⁎⁎⁎⁎", Border.starBorder());
    }

    /**
     * tableChoose(): print choose id in table
     */
    public static void tableChoose() {
        //draw border
        Border.borderChooseTable(42);
        System.out.print(Border.starBorder());
        System.out.printf(Color.GREEN_BOLD_BRIGHT + "\t%-12s %-23s %s\n", "MÃ ID",
                "TÊN SÁCH" + Color.RESET, Border.starBorder());
        for (Book book : bookList) {
            String longTitle = book.getTitle();
            String[] titleLines = longTitle.split("(?<=.{" + 14 + "})(?=\\s)", 2);
            boolean firstLine = true;
            System.out.print(Border.starBorder());
            System.out.printf(Color.WHITE_BOLD + "\t%-12s %-23s %s\n",
                    "----", "----------------" + Color.RESET, Border.starBorder());
            if (book.getTitle().length() < 16) {
                System.out.print(Border.starBorder());
                System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-12s %-23s %s\n",
                        book.getId(), book.getTitle() + Color.RESET, Border.starBorder());
            } else {
                for (String titleLine : titleLines) {
                    System.out.print(Border.starBorder());
                    if (firstLine) {
                        System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-12s %-23s %s\n",
                                book.getId(), titleLine.trim() + Color.RESET, Border.starBorder());
                        firstLine = false;
                    } else {
                        System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-12s %-23s %s\n",
                                " ", titleLine.trim() + Color.RESET, Border.starBorder());
                    }
                }
            }
        }
        //draw border
        Border.borderChooseTable(42);
    }

    /**
     * @param message print error
     */
    public static void printErrRed(String message) {
        System.out.println(Color.RED + message + Color.RESET);
    }
}
