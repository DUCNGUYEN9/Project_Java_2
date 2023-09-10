package library.Management;

import library.Color.Color;
import library.IOFile.IOFile;
import library.border.Border;
import library.entity.Book;
import library.entity.Category;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Comparator;

import static library.run.LibraryManagement.*;

public class CategoryManagement implements Serializable {
    public static void categoryManagement() {

        boolean exitCategory = true;
        do {
            System.out.print(Color.PURPLE_BOLD_BRIGHT +
                    "\n⁜⁜⁜⁜⁜⁜ QUẢN LÝ THỂ LOẠI ⁜⁜⁜⁜⁜⁜\n" + Color.CYAN_BOLD_BRIGHT +
                    "⁜\t1. Thêm mới thể loại            ⁜\n" +
                    "⁜\t2. Hiển thị DS theo tên A – Z   ⁜\n" +
                    "⁜\t3. Thống kê Thể loại & Sách     ⁜\n" +
                    "⁜\t4. Cập nhật thể loại            ⁜\n" +
                    "⁜\t5. Xóa thể loại                 ⁜\n" +
                    "⁜\t6. Quay lại                     ⁜\n");
            Border.maRuBorder(38);
            System.out.print(Color.WHITE_BRIGHT + "Lựa chọn của bạn: " + Color.RESET);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addCategory();
                        break;
                    case 2:
                        displayCategoryAZ();
                        break;
                    case 3:
                        statisticsCategory();
                        break;
                    case 4:
                        updateCategory();
                        break;
                    case 5:
                        deleteCategory();
                        break;
                    case 6:
                        exitCategory = false;
                        break;
                    default:
                        printErrRed("Vui lòng chọn số trong khoảng 1 - 3");
                }
            } catch (NumberFormatException numberFormatException) {
                printErrRed("Lỗi khi nhập kí tự không phải số !");
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui long liên hệ hệ thống!");
            }
        } while (exitCategory);
    }

    /**
     * addCategory(): add a/an category to categoryList
     */
    public static void addCategory() {
        while (true) {
            System.out.print("Nhập số lượng thể loại bạn muốn thêm: ");
            try {
                int n = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < n; i++) {
                    Category category = new Category();
                    System.out.println(Color.GREEN_UNDERLINED
                            + "Nhập thông tin sách thứ " + (i + 1) + Color.RESET);
                    category.input();
                    categoryList.add(category);
                }
                break;
            } catch (NumberFormatException nfe) {
                printErrRed("Lỗi khi nhập kí tự không phải số !");
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        }
        //write data to file
        IOFile.writeDataToFileCategories(categoryList);
    }

    /**
     * displayCategoryAZ(): show category sorted from A to Z
     */
    public static void displayCategoryAZ() {
        categoryList.sort(new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        if (categoryList.size() > 0) {
            System.out.println(Color.CYAN_BOLD_BRIGHT
                    + "\t\t\t\t\t ◯◯◯ DANH SÁCH THEO TÊN A - Z ◯◯◯" + Color.RESET);
            //draw border header
            Border.borderTable(80);
            int stt = 1;
            System.out.print(Border.shapeBorder());
            System.out.printf(Color.GREEN_BOLD_BRIGHT +
                            "\t%-6s %-16s %-20s %-25s %-1s\n", "STT", "Mã thể loại",
                    "Tên thể loại", "Trạng thái" + Color.RESET, Border.shapeBorder());
            for (Category category : categoryList) {
                System.out.printf("%s\t%-7d", Border.shapeBorder(), stt++);
                category.output();
            }
            //draw border footer
            Border.borderTable(80);
        } else {
            //Notification list empty
            Border.tableEmpty();
        }
    }

    /**
     * statisticsCategory(): statistics category and book in category
     */
    public static void statisticsCategory() {
        if (categoryList.size() > 0) {
            System.out.println(Color.CYAN_BOLD_BRIGHT
                    + "\t\t\t ◯◯◯ BẢNG THỐNG KÊ THỂ LOẠI & SÁCH ◯◯◯" + Color.RESET);
            //draw border header
            Border.borderTable(70);
            int stt = 1;
            System.out.print(Border.shapeBorder());
            System.out.printf(Color.GREEN_BOLD_BRIGHT +
                            "\t%-6s %-17s %-20s %-15s %-1s\n", "STT", "Mã thể loại",
                    "Tên thể loại", "Số sách" + Color.RESET, Border.shapeBorder());
            for (Category category : categoryList) {
                int count = 0;
                for (Book book : bookList) {
                    if (category.getId() == book.getCategoryId()) {
                        count++;
                    }
                }
                System.out.printf("%s\t%-7d", Border.shapeBorder(), stt++);
                DecimalFormat df = new DecimalFormat("0000");
                boolean firstLine = true;
                String longType = category.getName();
                String[] typeLines = longType.split("(?<=.{" + 13 + "})(?=\\s)", 2);
                if (longType.length() < 16) {
                    System.out.printf(Color.WHITE_BRIGHT + "%-17s %-20s %-10d %-1s\n",
                            df.format(category.getId()), typeLines[0].trim(), count, " "
                                    + Color.RESET + Border.shapeBorder());
                } else {
                    for (String typeLine : typeLines) {
                        if (firstLine) {
                            System.out.printf(Color.WHITE_BRIGHT + "%-17s %-20s %-10d %-1s\n",
                                    df.format(category.getId()), typeLine.trim(), count, " "
                                            + Color.RESET + Border.shapeBorder());
                            firstLine = false;
                        } else {
                            System.out.print(Border.starBorder());
                            System.out.printf(Color.WHITE_BRIGHT + "\t%-6s %-17s %-20s %-10s %-1s\n",
                                    " ", " ", typeLine.trim(), " ", " "
                                            + Color.RESET + Border.shapeBorder());
                        }
                    }
                }
            }
            //draw border footer
            Border.borderTable(70);
        } else {
            //Notification list empty
            Border.tableEmpty();
        }
    }

    /**
     * updateCategory(): update category from categoryId
     */

    public static void updateCategory() {
        if (categoryList.size() > 0) {
            while (true) {
                //print choose id in table
                tableChoose();
                System.out.print(Color.WHITE_BOLD_BRIGHT
                        + "Nhập mã thể loại bạn muốn cập nhật: " + Color.RESET);
                try {
                    int editId = Integer.parseInt(scanner.nextLine());
                    boolean checkId = false;
                    for (Category category : categoryList) {
                        if (category.getId() == editId) {
                            checkId = true;
                            category.update();
                            break;
                        }
                    }
                    if (!checkId) {
                        printErrRed("Mã thể loại bạn nhập không tồn tại !\n");
                    } else {
                        System.out.println(Color.GREEN_BOLD_BRIGHT
                                + "Đã cập nhật thành công ✓" + Color.RESET);
                        break;
                    }
                } catch (NumberFormatException nfe) {
                    printErrRed("Lỗi khi nhập kí tự không phải số !\n");
                } catch (Exception exception) {
                    printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
                }
            }
        } else {
            printErrRed(" Danh Sách trống không thể cập nhật !");
        }
        //write data to file
        IOFile.writeDataToFileCategories(categoryList);
    }

    /**
     * deleteCategory(): delete category from categoryId
     */
    public static void deleteCategory() {
        if (categoryList.size() > 0) {
            while (true) {
                try {
                    //print choose id in table
                    tableChoose();
                    System.out.print(Color.WHITE_BOLD_BRIGHT
                            + "Nhập mã thể loại bạn muốn xóa: " + Color.RESET);
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    boolean checkId = false;
                    boolean isExist = false;
                    for (int i = 0; i < categoryList.size(); i++) {
                        if (categoryList.get(i).getId() == deleteId) {
                            checkId = true;
                            for (Book book : bookList) {
                                if (book.getCategoryId() == deleteId) {
                                    isExist = true;
                                    break;
                                }
                            }
                            if (!isExist) {
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
                                                categoryList.remove(i);
                                                System.out.println(Color.GREEN_BOLD_BRIGHT + "Đã xóa mã " + Color.RESET
                                                        + Color.YELLOW_BOLD_BRIGHT + deleteId + Color.RESET
                                                        + Color.GREEN_BOLD_BRIGHT + " thành công ✓" + Color.RESET);
                                                exit = false;
                                                break;
                                            case 2:
                                                System.out.println(Color.YELLOW_BOLD + "Đã hủy xóa ✖️" + Color.RESET);
                                                exit = false;
                                                break;
                                            default:
                                                System.out.println(Color.RED + "Vui lòng nhập 1 hoặc 2" + Color.RESET);
                                        }
                                    } catch (NumberFormatException nfe) {
                                        printErrRed("Lỗi khi nhập kí tự không phải số !\n");
                                    } catch (Exception exception) {
                                        printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !\n");
                                    }
                                } while (exit);
                            } else {
                                printErrRed("Mã thể loại này đang có sách không thể xóa !\n");
                            }
                        }
                    }
                    if (!checkId) {
                        printErrRed("Mã thể loại bạn nhập không tồn tại !\n");
                    } else {
                        break;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println(Color.RED + "Lỗi khi nhập kí tự không phải số !\n" + Color.RESET);
                } catch (Exception exception) {
                    System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
                }
            }
        } else {
            System.out.println(Color.RED +
                    " Danh Sách trống không thể xóa !" + Color.RESET);
        }
        //write data to file
        IOFile.writeDataToFileCategories(categoryList);
    }

    /**
     * tableChoose(): print choose id in table
     */
    public static void tableChoose() {
        //draw border
        Border.borderChooseTable(40);
        System.out.print(Border.starBorder());
        System.out.printf(Color.GREEN_BOLD_BRIGHT + "\t%-12s %-21s %s\n", "MÃ ID",
                "TÊN THỂ LOẠI" + Color.RESET, Border.starBorder());
        for (Category ca : categoryList) {
            DecimalFormat df = new DecimalFormat("0000");
            boolean firstLine = true;
            String longType = ca.getName();
            String[] typeLines = longType.split("(?<=.{" + 15 + "})(?=\\s)", 2);
            if (longType.length() < 16) {
                System.out.print(Border.starBorder());
                System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-12s %-21s %s\n", df.format(ca.getId()),
                        typeLines[0].trim() + Color.RESET, Border.starBorder());
            } else {
                for (String typeLine : typeLines) {
                    System.out.print(Border.starBorder());
                    if (firstLine) {
                        System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-12s %-21s %s\n", df.format(ca.getId()),
                                typeLine.trim() + Color.RESET, Border.starBorder());
                        firstLine = false;
                    } else {
                        System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-12s %-21s %s\n", " ",
                                typeLine.trim() + Color.RESET, Border.starBorder());
                    }
                }
            }
        }
        //draw border
        Border.borderChooseTable(40);
    }

    /**
     * @param message print error
     */
    public static void printErrRed(String message) {
        System.out.println(Color.RED + message + Color.RESET);
    }
}
