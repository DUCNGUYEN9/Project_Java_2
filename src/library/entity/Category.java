package library.entity;

import library.border.Border;
import library.interfaceentity.IEntity;
import library.Color.Color;

import java.io.Serializable;
import java.text.DecimalFormat;

import static library.run.LibraryManagement.categoryList;
import static library.run.LibraryManagement.scanner;

public class Category implements IEntity, Serializable {
    private int id;
    private String name;
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * input(): add catalog
     */
    @Override
    public void input() {
        uniqueTypeBookId();
        uniqueTypeBookName();
        statusTrueFalse();
        System.out.print(Color.GREEN +
                "‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥\n" + Color.RESET);

    }

    /**
     * uniqueTypeBookId(): categoryId unique
     */
    public void uniqueTypeBookId() {
        System.out.print("Nhập mã thể loại sách: ");
        boolean checkId = true;
        do {
            try {
                this.id = Integer.parseInt(scanner.nextLine());
                boolean isExist = false;
                if (this.id <= 0) {
                    System.err.println("Mã Id phải lớn hơn 0");
                } else {
                    if (categoryList.size() > 0) {
                        for (Category category : categoryList) {
                            if (category.getId() == this.id) {
                                printErrRed("Mã bạn nhập đã tồn tại vui lòng nhập mã khác !");
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                            break;
                        }
                    } else {
                        System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                        checkId = false;
                    }
                }
            } catch (NumberFormatException numberFormatException) {
                printErrRed("Lỗi khi nhập mã id phải là số nguyên");
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập mã id");
            }
        } while (checkId);
    }

    /**
     * uniqueTypeBookName(): category name unique
     */

    public void uniqueTypeBookName() {
        System.out.print("Nhập tên thể loại sách (6 - 30 kí tự): ");
        boolean checkName = true;
        do {
            try {
                String nameCatalog = scanner.nextLine();
                boolean isExist = false;
                if (nameCatalog.length() < 6 || nameCatalog.length() > 30) {
                    printErrRed("Tên thể loại phải có từ 6 - 30 kí tự");
                } else {
                    if (categoryList.size() > 0) {
                        for (Category category : categoryList) {
                            if (category.getName().equals(nameCatalog)) {
                                printErrRed("Tên bạn nhập đã tồn tại vui lòng nhập tên khác");
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            this.name = nameCatalog;
                            System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                            break;
                        }
                    } else {
                        this.name = nameCatalog;
                        System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                        checkName = false;
                    }
                }
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } while (checkName);
    }

    /**
     * statusTrueFalse(): status of category
     */
    public void statusTrueFalse() {
        System.out.print("Nhập trạng thái thể loại sách (true/false): ");
        boolean checkStatus = true;
        do {
            String str = scanner.nextLine();
            if (str.trim().length() != 0) {
                if (str.equalsIgnoreCase("true") ||
                        str.equalsIgnoreCase("false")) {
                    this.status = Boolean.parseBoolean(str);
                    System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                    checkStatus = false;
                } else {
                    printErrRed("Trạng thái thể loại (true/false) vui lòng nhập lại:  ");
                }
            } else {
                printErrRed("Không được để trống, Vui lòng nhập lại !");
            }
        } while (checkStatus);
    }

    /**
     * update(): update a catalog
     */
    public void update() {
        while (true) {
            System.out.println(Color.BLUE_BOLD_BRIGHT
                    + "Bạn có muốn cập nhật tên thể loại sách không ?" + Color.RESET);
            System.out.printf("\t\t%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n == 1) {
                    uniqueTypeBookName();
                    break;
                } else if (n == 2) {
                    System.out.println(Color.YELLOW_BOLD + "Không thay đổi ◯" + Color.RESET);
                    break;
                } else {
                    printErrRed("Vui lòng nhập 1 hoặc 2\n");
                }
            } catch (NumberFormatException nfe) {
                printErrRed("Lỗi khi nhập kí tự không phải số !\n");
            }
        }
        while (true) {
            System.out.println(Color.BLUE_BOLD_BRIGHT
                    + "Bạn có muốn cập nhật trạng thái sách không ?" + Color.RESET);
            System.out.printf("\t\t%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n == 1) {
                    statusTrueFalse();
                    break;
                } else if (n == 2) {
                    System.out.println(Color.YELLOW_BOLD + "Không thay đổi ◯" + Color.RESET);
                    break;
                } else {
                    printErrRed("Vui lòng nhập 1 hoặc 2\n");
                }
            } catch (NumberFormatException nfe) {
                printErrRed("Lỗi khi nhập kí tự không phải số !\n");
            }
        }
    }
    /**
     * @param message print error
     */
    public static void printErrRed(String message) {
        System.out.println(Color.RED + message + Color.RESET);
    }

    /**
     * output(): print info
     */
    @Override
    public void output() {
        //format id to "0000"
        DecimalFormat df = new DecimalFormat("0000");
        boolean firstLine = true;
        String longType = this.name;
        String[] typeLines = longType.split("(?<=.{" + 15 + "})(?=\\s)", 2);
        if (longType.length() < 16) {
            System.out.printf(Color.WHITE_BRIGHT + "%-16s %-20s %-25s %-1s\n",
                    df.format(this.id), typeLines[0].trim(),
                    (this.status ? "Hoạt động" : "Không hoạt động") + Color.RESET, Border.shapeBorder());
        } else {
            for (String typeLine : typeLines) {
                if (firstLine) {
                    System.out.printf(Color.WHITE_BRIGHT + "%-16s %-20s %-25s %-1s\n",
                            df.format(this.id), typeLine.trim(),
                            (this.status ? "Hoạt động" : "Không hoạt động") + Color.RESET, Border.shapeBorder());
                    firstLine = false;
                } else {
                    System.out.print(Border.starBorder());
                    System.out.printf(Color.WHITE_BRIGHT + "%-9s %-16s %-20s %-25s %-1s\n",
                            " ", " ", typeLine.trim(), " " + Color.RESET, Border.shapeBorder());
                }
            }
        }
    }
}
