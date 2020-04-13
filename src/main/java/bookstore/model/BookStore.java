package bookstore.model;

import bookstore.exceptions.LoadingException;
import bookstore.exceptions.SavingException;
import bookstore.utils.*;

import java.util.ArrayList;
import java.util.List;

public class BookStore {

    private List<BookDetails> books;

    public BookStore() {
        books = new ArrayList<>();

        try {
            List<BookDetails> bookDetails = CSVUtils.load();
            books = new ArrayList<>(bookDetails);
        } catch (LoadingException e) {
            e.getStackTrace();
        }
    }

    public void addBook(BookDetails bookDetails) throws SavingException {
        books.add(bookDetails);
        save();
    }

    public void sellBook(BookDetails bookDetails, int count) throws SavingException {
        int availability = bookDetails.getAvailability() - count;
        bookDetails.setAvailability(availability);
        save();
    }

    public void removeSelectedBook(String uniqueId) throws SavingException {
        for (BookDetails book : books) {
            if (book.getUniqueId().equals(uniqueId)) {
                books.remove(book);
                save();
                return;
            }
        }
    }

    public List<BookDetails> search(String s) {
        List<BookDetails> allBooks = new ArrayList<>();

        for (BookDetails book : books) {
            if (book.getBook().getTitle().toLowerCase().contains(s.toLowerCase()) ||
                    book.getBook().getAuthor().toLowerCase().contains(s.toLowerCase())
            ) {
                allBooks.add(book);
            }
        }
        return allBooks;
    }

    public List<BookDetails> getAllBooks() {
        return this.books;
    }

    public boolean isSuchBookAdded(String title, String author) {
        for (BookDetails book : books) {
            if (book.getBook().getTitle().equalsIgnoreCase(title) && book.getBook().getAuthor().equalsIgnoreCase(author)
            ) {
                return true;
            }
        }
        return false;
    }

    public void save() throws SavingException {
        CSVUtils.save(books);
    }

    public BookDetails getBookAtIndex(String uniqueID) {
        for (BookDetails book : books) {
           if(book.getUniqueId().equals(uniqueID)){
               return book;
           }
        }
        return null;
    }
}
