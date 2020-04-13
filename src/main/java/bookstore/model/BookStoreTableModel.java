package bookstore.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookStoreTableModel extends AbstractTableModel {

    private List<BookDetails> books;

    @Override
    public int getRowCount() {
        return books == null ? 0 : books.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BookDetails bookList = books.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bookList.getUniqueId();
            case 1:
                return bookList.getBook().getTitle();
            case 2:
                return bookList.getBook().getAuthor();
            case 3:
                return bookList.getBook().getPrice();
            case 4:
                return bookList.getBook().getPublisher();
            case 5:
                return bookList.getBook().getForeignLiterature().equals(ForeignLiterature.YES) ? "Foreign" : "Local";
            case 6:
                return bookList.getAvailability();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Title";
            case 2:
                return "Author";
            case 3:
                return "Price";
            case 4:
                return "Publisher";
            case 5:
                return "Foreign Literature";
            case 6:
                return "Availability";
            default:
                return null;
        }
    }

    public void setBooks(List<BookDetails> books) {
        this.books = books;
    }

    public String getBookID(int rowIndex) {
        return books.get(rowIndex).getUniqueId();
    }

}
