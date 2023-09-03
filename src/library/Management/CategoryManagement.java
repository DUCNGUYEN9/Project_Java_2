package library.Management;

import library.Color.Color;
import library.IOFile.IOFile;
import library.border.Border;
import library.entity.Book;
import library.entity.Category;

import java.io.Serializable;
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
                        System.err.println("Vui lòng chọn số trong khoảng 1 - 3");
                }
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Lỗi khi nhập kí tự không phải số");
            } catch (Exception exception) {
                System.err.println("Lỗi khi nhập đầu vào, vui long liên hệ hệ thống!");
            }
        } while (exitCategory);
    }

    /**
     * addCategory(): add a/an category to categoryList
     */
    public static void addCategory() {
        System.out.print("Nhập số lượng thể loại bạn muốn thêm: ");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                Category category = new Category();
                category.input();
                categoryList.add(category);
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Lỗi khi nhập kí tự không phải số !");
        } catch (Exception exception) {
            System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
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
                System.out.printf(Color.WHITE_BRIGHT + "%-17d %-20s %-10d %-1s\n",
                        category.getId(), category.getName(), count, " "
                                + Color.RESET + Border.shapeBorder());
            }
            //draw border footer
            Border.borderTable(70);
        } else {
            //Notification list empty
            Border.tableEmpty();
        }

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
            System.out.print(Border.starBorder());
            System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-12d %-21s %s\n", ca.getId(),
                    ca.getName() + Color.RESET, Border.starBorder());
        }
        //draw border
        Border.borderChooseTable(40);
    }

    /**
     * updateCategory(): update category from categoryId
     */

    public static void updateCategory() {
        if (categoryList.size() > 0) {
            //print choose id in table
            tableChoose();
            System.out.print(Color.WHITE_BOLD_BRIGHT + "Nhập mã thể loại bạn muốn cập nhật: " + Color.RESET);
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
                    System.err.println("Mã thể loại bạn nhập không tồn tại !");
                } else {
                    System.out.println(Color.GREEN_BOLD_BRIGHT + "Đã cập nhật thành công ✓" + Color.RESET);
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Lỗi khi nhập kí tự không phải số !");
            } catch (Exception exception) {
                System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } else {
            //Notification list empty
            Border.tableEmpty();
        }
        //write data to file
        IOFile.writeDataToFileCategories(categoryList);
    }

    /**
     * deleteCategory(): delete category from categoryId
     */
    public static void deleteCategory() {
        if (categoryList.size() > 0) {
            try {
                //print choose id in table
                tableChoose();
                System.out.print(Color.WHITE_BOLD_BRIGHT + "Nhập mã thể loại bạn muốn xóa: " + Color.RESET);
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
                                System.out.printf("%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                                        Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
                                try {
                                    int number = Integer.parseInt(scanner.nextLine());
                                    switch (number) {
                                        case 1:
                                            categoryList.remove(i);
                                            System.out.println(Color.GREEN_BOLD_BRIGHT + "Mã "
                                                    + deleteId + " Đã xóa thành công ✓" + Color.RESET);
                                            exit = false;
                                            break;
                                        case 2:
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
                        } else {
                            System.err.println("Mã thể loại này đang có sách không thể xóa!");
                        }
                    }
                }
                if (!checkId) {
                    System.err.println("Mã thể loại bạn nhập không tồn tại");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Lỗi khi nhập kí tự không phải số !");
            } catch (Exception exception) {
                System.err.println("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } else {
            //Notification list empty
            Border.tableEmpty();
        }
        //write data to file
        IOFile.writeDataToFileCategories(categoryList);
    }
}