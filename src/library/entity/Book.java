package library.entity;

import library.Color.Color;
import library.border.Border;
import library.interfaceentity.IEntity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;

import static library.run.LibraryManagement.*;

public class Book implements IEntity, Serializable {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String description;
    private int categoryId;

    public Book() {
    }

    public Book(String id, String title,
                String author,
                String publisher, int year,
                String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * input(): add info of a Book
     */
    @Override
    public void input() {
        choiceCategoryId();
        uniqueBookId();
        uniqueBookTitle();
        authorName();
        publisherName();
        yearBook();
        desName();
        System.out.print(Color.GREEN +
                "‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥‥\n" + Color.RESET);
    }

    /**
     * choiceCategoryId(): display categoryList to choice type book
     */

    public void choiceCategoryId() {
        boolean checkChoice = true;
        do {
            //print table catalog
            int stt = 1;
            DecimalFormat df = new DecimalFormat("0000");
            Border.borderTable(46);
            System.out.print(Border.starBorder());
            System.out.printf(Color.GREEN_BOLD_BRIGHT + "\t%-5s %-12s %-20s %s\n", "STT", "MÃ ID",
                    "TÊN THỂ LOẠI" + Color.RESET, Border.starBorder());
            for (Category ca : categoryList) {
                boolean firstLine = true;
                String longType = ca.getName();
                String[] typeLines = longType.split("(?<=.{" + 13 + "})(?=\\s)", 2);
                if (longType.length() < 16) {
                    System.out.print(Border.starBorder());
                    System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-5d %-12s %-20s %s\n",
                            stt++, df.format(ca.getId()), typeLines[0].trim() + Color.RESET, Border.starBorder());
                } else {
                    for (String typeLine : typeLines) {
                        System.out.print(Border.starBorder());
                        if (firstLine) {
                            System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-5d %-12s %-20s %s\n",
                                    stt++, df.format(ca.getId()), typeLine.trim()
                                            + Color.RESET, Border.starBorder());
                            firstLine = false;
                        } else {
                            System.out.printf(Color.WHITE_BOLD_BRIGHT + "\t%-5s %-12s %-20s %s\n",
                                    " ", " ", typeLine.trim()
                                            + Color.RESET, Border.starBorder());
                        }
                    }
                }
            }
            Border.borderTable(46);
            try {
                System.out.print(Color.WHITE_BRIGHT +
                        "Hãy chọn STT theo danh sách thể loại sách: " + Color.RESET);
                int number = Integer.parseInt(scanner.nextLine());
                if (number > categoryList.size()) {
                    printErrRed("Số bạn chọn không có trong danh sách !");
                } else {
                    this.categoryId = categoryList.get(number - 1).getId();
                    checkChoice = false;
                }
            } catch (NumberFormatException nfe) {
                printErrRed("Lỗi khi nhập kí tự không phải số !");
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } while (checkChoice);
    }

    /**
     * uniqueBookId(): book Id only one
     */
    public void uniqueBookId() {
        System.out.print("Nhập mã sách (4 ký tự bắt đầu bằng 'B'): ");
        boolean checkId = true;
        do {
            try {
                this.id = scanner.nextLine();
                boolean isExist = false;
                if (this.id.length() != 4) {
                    printErrRed("Mã Id phải có độ dài 4 kí tự !");
                } else if (!this.id.startsWith("B")) {
                    printErrRed("Mã Id phải bắt đầu bằng 'B' !");
                } else {
                    if (categoryList.size() > 0) {
                        for (Book book : bookList) {
                            if (book.getId().equals(this.id)) {
                                printErrRed("Mã bạn nhập đã tồn tại, vui lòng nhập mã khác !");
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
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } while (checkId);
    }

    /**
     * uniqueBookTitle(): book title only one
     */
    public void uniqueBookTitle() {
        System.out.print("Nhập tiêu đề sách (6-50 kí tự): ");
        boolean checkTitle = true;
        do {
            try {
                String nameBook = scanner.nextLine();
                boolean isExist = false;
                if (nameBook.length() < 6 || nameBook.length() > 50) {
                    printErrRed("Tiêu đề sách phải có từ 6 - 50 kí tự !");
                } else {
                    if (categoryList.size() > 0) {
                        for (Book book : bookList) {
                            if (book.getTitle().equals(nameBook)) {
                                printErrRed("Tiêu đề sách bạn nhập đã tồn tại !");
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            this.title = nameBook;
                            System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                            break;
                        }
                    } else {
                        this.title = nameBook;
                        System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                        checkTitle = false;
                    }
                }
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } while (checkTitle);
    }

    /**
     * authorName(): author of book
     */
    public void authorName() {
        System.out.print("Nhập tên tác giả: ");
        boolean checkAuthor = true;
        do {
            try {
                this.author = scanner.nextLine();
                if (this.author.trim().length() < 1) {
                    printErrRed("Tên tác giả không được bỏ trống !");
                } else {
                    System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                    checkAuthor = false;
                }
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } while (checkAuthor);
    }

    /**
     * publisherName(): publisher of book
     */
    public void publisherName() {
        System.out.print("Nhập tên nhà xuất bản: ");
        boolean checkpublisher = true;
        do {
            try {
                this.publisher = scanner.nextLine();
                if (this.publisher.trim().length() < 1) {
                    printErrRed("Tên nhà xuất bản không được để trống !");
                } else {
                    System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                    checkpublisher = false;
                }
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } while (checkpublisher);
    }

    /**
     * yearBook(): Publication year of book
     */
    public void yearBook() {
        System.out.print("Nhập năm xuất bản: ");
        LocalDate localDate = LocalDate.now();
        int yearCurrent = localDate.getYear();
        boolean checkYear = true;
        do {
            try {
                this.year = Integer.parseInt(scanner.nextLine());
                if (this.year < 1970 || this.year > yearCurrent) {
                    System.out.printf(Color.RED + "Năm xuất bản từ năm 1970 - %d\n" + Color.RESET, yearCurrent);
                } else {
                    System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                    checkYear = false;
                }
            } catch (NumberFormatException numberFormatException) {
                printErrRed("Lỗi khi nhập kí tự không phải số !");
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");
            }
        } while (checkYear);
    }

    /**
     * desName():  description a book
     */
    public void desName() {
        System.out.print("Nhập mô tả sách: ");
        boolean checkdescription = true;
        do {
            try {
                this.description = scanner.nextLine();
                if (this.description.trim().length() < 1) {
                    printErrRed("Mô tả sách không được để trống !");
                } else {
                    System.out.println(Color.GREEN_BOLD_BRIGHT + "Ok ✓" + Color.RESET);
                    checkdescription = false;
                }
            } catch (Exception exception) {
                printErrRed("Lỗi khi nhập đầu vào, vui lòng liên hệ hệ thống !");

            }
        } while (checkdescription);
    }

    /**
     * update(): update a book
     */
    public void update() {
        while (true) {
            System.out.println(Color.BLUE_BOLD_BRIGHT + "Bạn có muốn cập nhật tiêu đề sách không ?" + Color.RESET);
            System.out.printf("\t\t%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n == 1) {
                    uniqueBookTitle();
                    break;
                } else if (n == 2) {
                    System.out.println(Color.YELLOW_BOLD + "Không thay đổi ◯" + Color.RESET);
                    break;
                } else {
                    printErrRed("Vui lòng nhập 1 hoặc 2");
                }
            } catch (NumberFormatException nfe) {
                printErrRed("Lỗi khi nhập kí tự không phải số !\n");
            }
        }
        while (true) {
            System.out.println(Color.BLUE_BOLD_BRIGHT
                    + "Bạn có muốn cập nhật tên tác giả sách không ?" + Color.RESET);
            System.out.printf("\t\t%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n == 1) {
                    authorName();
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
                    + "Bạn có muốn cập nhật nhà xuất bản không ?" + Color.RESET);
            System.out.printf("\t\t%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n == 1) {
                    publisherName();
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
                    + "Bạn có muốn cập nhật năm xuất bản không ?" + Color.RESET);
            System.out.printf("\t\t%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n == 1) {
                    yearBook();
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
                    + "Bạn có muốn cập nhật mô tả không ?" + Color.RESET);
            System.out.printf("\t\t%-20s %s\n", Color.RED_BACKGROUND + "1.Có" + Color.RESET,
                    Color.GREEN_BACKGROUND + "2.Không" + Color.RESET);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n == 1) {
                    desName();
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
     * output(): display info book
     * title and description wrap line
     */
    @Override
    public void output() {
        String longTitle = this.title;
        String longDes = this.description;
        int maxLineLength = 14;
//        String[] titleLines = longTitle.split("(?<=\\G.{" + maxLineLength + "})");
        String[] titleLines = longTitle.split("(?<=.{" + maxLineLength + "})(?=\\s)", 2);
        String[] desLines = longDes.split("(?<=.{" + maxLineLength + "})(?=\\s)", 2);
        if (longTitle.length() < 16 && longDes.length() < 16) {
            System.out.print(Border.starBorder());
            System.out.printf(Color.WHITE_BRIGHT + "\t%-15s %-10s %-20s %-20s %-16s %-15d %-22s %s\n",
                    "↳", this.id, titleLines[0], this.author, this.publisher
                    , this.year, desLines[0] + Color.RESET, Border.starBorder());
        } else {
            //title and description wrap lines
            boolean firstLine = true;
            if (titleLines.length < desLines.length) {
                for (int i = 0; i < desLines.length; i++) {
                    System.out.print(Border.starBorder());
                    if (firstLine) {
                        System.out.printf(Color.WHITE_BRIGHT + "\t%-15s %-10s %-20s %-20s %-16s %-15s %-22s %s\n",
                                "↳", this.id, (i < titleLines.length ? titleLines[i] : "").trim()
                                , this.author, this.publisher
                                , this.year, desLines[i].trim() + Color.RESET, Border.starBorder());
                        firstLine = false;
                    } else {
                        System.out.printf(Color.WHITE_BRIGHT + "\t%-15s %-10s %-20s %-20s %-16s %-15s %-22s %s\n",
                                " ", " ", (i < titleLines.length ? titleLines[i] : "").trim(),
                                " ", " ", " ", desLines[i].trim() + Color.RESET, Border.starBorder());
                    }
                }
            } else {
                for (int i = 0; i < titleLines.length; i++) {
                    System.out.print(Border.starBorder());
                    if (firstLine) {
                        System.out.printf(Color.WHITE_BRIGHT
                                        + "\t%-15s %-10s %-20s %-20s %-16s %-15s %-22s %s\n",
                                "↳", this.id, titleLines[i].trim(), this.author, this.publisher
                                , this.year, (i < desLines.length ? desLines[i] : "").trim()
                                        + Color.RESET, Border.starBorder());
                        firstLine = false;
                    } else {
                        System.out.printf(Color.WHITE_BRIGHT + "\t%-15s %-10s %-20s %-20s %-16s %-15s %-22s %s\n",
                                " ", " ", titleLines[i].trim(), " ", " "
                                , " ", (i < desLines.length ? desLines[i] : "").trim()
                                        + Color.RESET, Border.starBorder());
                    }
                }
            }
        }
    }
}
