package bookstore.utils;

import bookstore.exceptions.LoadingException;
import bookstore.exceptions.SavingException;
import bookstore.model.Book;
import bookstore.model.BookDetails;
import bookstore.model.ForeignLiterature;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class CSVUtils {

    private static final String SEPARATOR = ",";
    private static final String FILE_NAME = "bookstore.csv";

    public static List<BookDetails> load() throws LoadingException {
        List<BookDetails> books = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             Scanner scanner = new Scanner(bufferedInputStream)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                BookDetails book = createBookDetails(line);
                books.add(book);
            }
        } catch (IOException e) {
            throw new LoadingException();
        }
        return books;
    }

    public static void save(Collection<BookDetails> books) throws SavingException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
             PrintStream printStream = new PrintStream(bufferedOutputStream)) {

            books.forEach(e ->
            {
                String savedBook = saveBookData(e);
                printStream.println(savedBook);
            });
        } catch (IOException e) {
            throw new SavingException();
        }
    }

    private static BookDetails createBookDetails(String line) {
        String[] elements = line.split(SEPARATOR);
        Book book = new Book();
        BookDetails bookDetails = new BookDetails();
        bookDetails.setUniqueId(elements[0]);
        book.setTitle(elements[1]);
        book.setAuthor(elements[2]);
        book.setPrice(Double.parseDouble(elements[3]));
        book.setPublisher(elements[4]);
        book.setForeignLiterature(elements[5].equals("Foreign") ? ForeignLiterature.YES : ForeignLiterature.NO);
        bookDetails.setAvailability(Integer.parseInt(elements[6]));
        bookDetails.setBook(book);
        return bookDetails;
    }

    private static String saveBookData(BookDetails book) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(book.getUniqueId());
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(book.getBook().getTitle());
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(book.getBook().getAuthor());
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(book.getBook().getPrice());
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(book.getBook().getPublisher());
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(book.getBook().getForeignLiterature().equals(ForeignLiterature.YES) ? "Foreign" : "Local");
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(book.getAvailability());

        return stringBuilder.toString();
    }
}
