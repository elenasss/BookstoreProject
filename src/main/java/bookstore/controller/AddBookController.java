package bookstore.controller;

import bookstore.model.Book;
import bookstore.model.BookDetails;
import bookstore.model.ForeignLiterature;
import bookstore.utils.Helper;

import javax.swing.*;

import static bookstore.controller.UIHelper.showErrorMessage;
import static bookstore.utils.Helper.isTextLengthValid;

public class AddBookController {

    private JTextField bookTitleField;
    private JTextField bookAuthorField;
    private JTextField bookPriceField;
    private JTextField bookPublisherFiled;
    private JTextField bookAvailabilityField;
    private JCheckBox isForeignLiteratureCheckBox;
    private JFrame frame;
    private AddBookListener listener;
    private BookDetails bookDetails;

    public void setListener(AddBookListener listener){
        this.listener = listener;
    }

    public void addBook() {
        frame = new JFrame("Add New Book");
        frame.setBounds(150, 150, 500, 500);
        frame.setLayout(null);

        JLabel titleLabel = addLabel("Title", 20);
        bookTitleField = addTextFiled(20);

        JLabel authorLabel = addLabel("Author", 80);
        bookAuthorField = addTextFiled(80);

        JLabel priceLabel = addLabel("Price", 140);
        bookPriceField = addTextFiled(140);

        JLabel PublisherLabel = addLabel("Publisher", 200);
        bookPublisherFiled = addTextFiled(200);

        JLabel bookAvailabilityLabel = addLabel("Count", 260);
        bookAvailabilityField = addTextFiled(260);

        JLabel foreignLiteratureLabel = addLabel("Foreign Literature:", 320);
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBounds(120, 320, 80, 40);
        isForeignLiteratureCheckBox = new JCheckBox("", false);
        checkBoxPanel.add(isForeignLiteratureCheckBox);
        frame.add(checkBoxPanel);

        JButton saveButton = new JButton("Save Book");
        frame.add(saveButton);
        saveButton.setBounds(100, 400, 100, 30);
        saveButton.addActionListener(l -> {
            save();
            bookTitleField.setText(null);
            bookAuthorField.setText(null);
            bookPublisherFiled.setText(null);
            bookAvailabilityField.setText("");
            bookPriceField.setText("");
            isForeignLiteratureCheckBox.setSelected(false);
        });

        JButton closeButton = new JButton("Close");
        frame.add(closeButton);
        closeButton.setBounds(280, 400, 100, 30);
        closeButton.addActionListener(l -> {
            frame.dispose();
        });

        frame.setVisible(true);
    }


    private void save() {
        Book book = new Book();
        bookDetails = new BookDetails();

        if (checkValues()) {
            book.setTitle(bookTitleField.getText());
            book.setAuthor(bookAuthorField.getText());
            book.setPrice(Double.parseDouble(bookPriceField.getText()));
            book.setPublisher(bookPublisherFiled.getText());
            book.setForeignLiterature(isForeignLiteratureCheckBox.isSelected() ? ForeignLiterature.YES : ForeignLiterature.NO);
            bookDetails.setBook(book);
            bookDetails.getID();
            bookDetails.setAvailability(Integer.parseInt(bookAvailabilityField.getText()));
            if (listener!=null){
                listener.onBookCreated(bookDetails);
            }
        } else {
            showErrorMessage("Please enter correct values");
        }
    }

    private boolean checkValues() {
        return isTextLengthValid(bookTitleField.getText().length()) &&
                isTextLengthValid(bookAuthorField.getText().length()) &&
                isTextLengthValid(bookPublisherFiled.getText().length()) &&
                !bookPriceField.getText().equals("") &&
                Helper.isStringDouble(bookPriceField.getText()) &&
                Helper.isPriceValid(Double.parseDouble(bookPriceField.getText())) &&
                Helper.isStringInt(bookAvailabilityField.getText()) &&
                !bookAvailabilityField.getText().equals("") &&
                Helper.isAvailabilityValid(Integer.parseInt(bookAvailabilityField.getText()));
    }

    private JTextField addTextFiled(int y) {
        JTextField textField = new JTextField();
        textField.setBounds(150, y, 300, 40);
        frame.add(textField);
        return textField;
    }

    private JLabel addLabel(String title, int y) {
        JLabel jLabel = new JLabel(title);
        jLabel.setBounds(20, y, 110, 40);
        frame.add(jLabel);
        return jLabel;
    }
}
