package bookstore.controller;

import bookstore.exceptions.LoadingException;
import bookstore.exceptions.SavingException;
import bookstore.model.BookDetails;
import bookstore.model.BookStore;
import bookstore.model.BookStoreTableModel;
import bookstore.utils.CSVUtils;

import javax.swing.*;
import java.util.List;

import static bookstore.controller.UIHelper.*;
import static bookstore.utils.Helper.isTextLengthValid;

public class UIController {
    private static JFrame frame;
    private List<BookDetails> books;
    private BookStore bookStore;
    private JTextField titleField;
    private JTextField authorField;
    private JTable table;
    private JScrollPane scrollPane;
    private BookStoreTableModel tableModel;
    private boolean isTableVisible;
    private boolean isAdmin;

    public UIController(boolean isAdmin) {
        bookStore = new BookStore();
        tableModel = new BookStoreTableModel();
        isTableVisible = false;
        this.isAdmin = isAdmin;
    }

    public void createUI() {
        frame = new JFrame("BookStore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(50, 100, 1000, 600);
        frame.setLayout(null);

        addLabel("Title", 20);
        titleField = addTextFiled(70);

        addLabel("Author", 380);
        authorField = addTextFiled(430);

        JButton searchButton = addButton("Search", 20);
        searchButton.addActionListener(e -> showSearchResults());

        JButton showAllBooksButton = addButton("Show All Books", 95);
        showAllBooksButton.addActionListener(e -> showAllBooks());

        JButton addBookButton = addButton("Add New Book", 170);
        addBookButton.addActionListener(e -> {
            checkPermission("add");
        });

        JButton editBookButton = addButton("Edit Book", 245);
        editBookButton.addActionListener(e -> {
            checkPermission("edit");
        });

        JButton sellBookButton = addButton("Sell Book", 320);
        sellBookButton.addActionListener(e -> sellBook());

        JButton removeBookButton = addButton("Remove Book", 395);
        removeBookButton.addActionListener(e -> {
            checkPermission("remove");
        });

        JButton closeButton = addButton("Close", 480);
        closeButton.addActionListener(e -> frame.dispose());
    }

    public void show() {
        frame.setVisible(true);
    }

    private void addBook() {
        AddBookController addBookController = new AddBookController();
        addBookController.addBook();
        addBookController.setListener(newBook -> {
            createNewBook(newBook);
            if (isTableVisible) {
                showAllBooks();
            }
        });
    }

    private void createNewBook(BookDetails book) {
        if (bookStore.isSuchBookAdded(book.getBook().getTitle(), book.getBook().getAuthor())) {
            showErrorMessage("This book is already added in the Bookstore");
        } else {
            try {
                bookStore.addBook(book);
                showInformationMessage("Book was added");
            } catch (SavingException e) {
                showErrorMessage("Book was not saved");
            }
        }
    }

    private void editBook() {
        if (table != null && table.getSelectedRow() >= 0) {
            int selectedRow = table.getSelectedRow();
            String uniqueId = tableModel.getBookID(selectedRow);
            BookDetails book = bookStore.getBookAtIndex(uniqueId);
            EditBookController editBookController = new EditBookController();
            editBookController.editBook(book);
            editBookController.setListener(editedBook -> {
                try {
                    bookStore.save();
                    tableModel.fireTableDataChanged();
                    showInformationMessage("Book was edited");
                } catch (SavingException e) {
                    showErrorMessage("Book was not edited");
                }
            });
        } else {
            showInformationMessage("Please choose a book");
        }
    }

    private void sellBook() {
        if (table != null && table.getSelectedRow() >= 0) {
            int selectedRow = table.getSelectedRow();
            String uniqueId = tableModel.getBookID(selectedRow);
            SellBookController sellBookController = new SellBookController();
            sellBookController.sellBook();
            sellBookController.setListener(booksCount -> {
                updateAvailability(uniqueId, booksCount);
                tableModel.fireTableDataChanged();
            });
        } else {
            showInformationMessage("Please choose a book");
        }
    }

    private void updateAvailability(String uniqueId, int booksCount) {
        try {
            BookDetails book = bookStore.getBookAtIndex(uniqueId);
            if (booksCount > book.getAvailability()) {
                showWarningMessage("You do not have enough books in the bookstore");
                return;
            }
            bookStore.sellBook(book, booksCount);
            showInformationMessage("The availability is updated");
        } catch (SavingException e) {
            e.fillInStackTrace();
            showErrorMessage("The availability was not updated");
        }
    }

    private void removeBook() {
        if (table != null && table.getSelectedRowCount() >= 0) {
            try {
                int selectedRow = table.getSelectedRow();
                String uniqueId = tableModel.getBookID(selectedRow);
                bookStore.removeSelectedBook(uniqueId);
                tableModel.setBooks(bookStore.getAllBooks());
                tableModel.fireTableDataChanged();
                showInformationMessage("The book was deleted");
            } catch (SavingException e) {
                showErrorMessage("The book was not removed");
                e.fillInStackTrace();
            }
        } else {
            showErrorMessage("Please choose a book");
        }
    }

    private void showSearchResults() {
        if (!isTextLengthValid(titleField.getText().length()) &&
                !isTextLengthValid(authorField.getText().length())) {
            showWarningMessage("Please enter a title or an author");
            return;
        } else if (isTextLengthValid(titleField.getText().length())) {
            tableModel.setBooks(bookStore.search(titleField.getText()));
        } else if (isTextLengthValid(authorField.getText().length())) {
            tableModel.setBooks(bookStore.search(authorField.getText()));
        }
        if (tableModel.getRowCount() == 0) {
            showInformationMessage("No books found");
        } else {
            loadTable();
        }
        authorField.setText("");
        titleField.setText("");
    }

    private void showAllBooks() {
        loadTable();
        try {
            tableModel.setBooks(bookStore.getAllBooks());
            tableModel.fireTableDataChanged();
            books = CSVUtils.load();
            isTableVisible = true;
        } catch (LoadingException e) {
            e.getStackTrace();
            showErrorMessage("List with books cannot be opened");
        }
    }

    private void loadTable() {
        if (table == null) {
            scrollPane = new JScrollPane();
            scrollPane.setBounds(20, 80, 780, 430);
            frame.add(scrollPane);
            table = new JTable(tableModel);
        }
        scrollPane.setViewportView(table);
    }

    private void checkPermission(String action) {
        if (isAdmin) {
            action(action);
        } else {
            userHasNoRights(action);
        }
    }

    private void action(String action) {
        switch (action) {
            case "remove":
                removeBook();
                break;
            case "add":
                addBook();
                break;
            case "edit":
                editBook();
                break;
        }
    }

    private void userHasNoRights(String action) {
        JOptionPane.showMessageDialog(frame, "You do not have permission to " + action + " books");
    }

    private JTextField addTextFiled(int x) {
        JTextField textField = new JTextField();
        textField.setBounds(x, 20, 290, 40);
        frame.add(textField);
        return textField;
    }

    private void addLabel(String title, int x) {
        JLabel jLabel = new JLabel(title);
        jLabel.setBounds(x, 20, 40, 40);
        frame.add(jLabel);
    }

    private JButton addButton(String title, int y) {
        JButton button = new JButton(title);
        frame.add(button);
        button.setBounds(830, y, 130, 40);
        return button;
    }
}
