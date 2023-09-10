package library.IOFile;

import library.entity.Category;
import library.entity.Book;

import java.io.*;
import java.util.List;

import static library.run.LibraryManagement.bookList;
import static library.run.LibraryManagement.categoryList;

public class IOFile {
    private static final String CATEGORY_FILE = "categories.txt";
    private static final String BOOK_FILE = "books.txt";

    /**
     * @param fileName: name of file
     * @param dataList: list of category & book
     * @param <T>:      data type
     */
    public static <T> void writeDataToFile(String fileName, List<T> dataList) {

        File file = new File(fileName);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(dataList);
            oos.flush();
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("File không tồn tại");
        } catch (IOException ioException) {
            System.err.println("Lỗi khi ghi dữ liệu ra file");
        } catch (Exception exception) {
            System.err.println("Xảy ra lỗi trong quá trình ghi dữ liệu ra file");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException ioException) {
                System.err.println("Xảy ra lỗi khi đóng các stream");
            } catch (Exception exception) {
                System.err.println("Xảy ra lỗi trong quá trình đóng các stream");

            }
        }
    }

    /**
     * @param fileName: name of file
     * @param <T>       :data type
     * @return :return a list
     */
    public static <T> List<T> readDataFromFile(String fileName) {
        File file = new File(fileName);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<T> dataList = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            dataList = (List<T>) ois.readObject();
        } catch (FileNotFoundException fileNotFoundException) {
//            System.err.println("Không tồn tại file");
            if (fileName.equals(BOOK_FILE)) {
                writeDataToFileBooks(bookList);
            } else {
                writeDataToFileCategories(categoryList);
            }
        } catch (IOException ioException) {
            System.err.println("Lỗi khi đọc file");
        } catch (Exception exception) {
            System.err.println("Có lỗi trong quá trình đọc dữ liệu từ file");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ioException) {
                System.err.println("Có lỗi khi đóng stream");
            } catch (Exception exception) {
                System.err.println("Có lỗi trong quá trình đóng các stream");
            }
        }
        return dataList;
    }

    /**
     * @param categoryList: category
     */
    public static void writeDataToFileCategories(List<Category> categoryList) {
        writeDataToFile(CATEGORY_FILE, categoryList);
    }

    public static List<Category> readDataFromFileCategories() {
        return readDataFromFile(CATEGORY_FILE);
    }

    /**
     * @param bookList: book
     */
    public static void writeDataToFileBooks(List<Book> bookList) {
        writeDataToFile(BOOK_FILE, bookList);
    }

    public static List<Book> readDataFromFileBooks() {
        return readDataFromFile(BOOK_FILE);
    }


}
