package bookstore.controller;

import bookstore.model.BookDetails;
import bookstore.model.ForeignLiterature;
import bookstore.utils.Helper;

import javax.swing.*;

import static bookstore.controller.UIHelper.showErrorMessage;
import static bookstore.utils.Helper.isTextLengthValid;

public class EditBookController {
    private JTextField bookTitleField;
    private JTextField bookAuthorField;
    private JTextField bookPriceField;
    private JTextField bookPublisherFiled;
    private JTextField bookAvailabilityField;
    private JCheckBox isForeignLiteratureCheckBox;
    private JFrame frame;
    private EditBookListener listener;

    public void setListener(EditBookListener listener) {
        this.listener = listener;
    }

    public void editBook(BookDetails bookDeatils) {
        frame = new JFrame("Edit Book");
        frame.setBounds(150, 150, 500, 500);
        frame.setLayout(null);

        JLabel titleLabel = addLabel("Title", 20);
        bookTitleField = addTextFiled(20);
        bookTitleField.setText(bookDeatils.getBook().getTitle());

        JLabel authorLabel = addLabel("Author", 80);
        bookAuthorField = addTextFiled(80);
        bookAuthorField.setText(bookDeatils.getBook().getAuthor());

        JLabel priceLabel = addLabel("Price", 140);
        bookPriceField = addTextFiled(140);
        bookPriceField.setText(String.valueOf(bookDeatils.getBook().getPrice()));

        JLabel PublisherLabel = addLabel("Publisher", 200);
        bookPublisherFiled = addTextFiled(200);
        bookPublisherFiled.setText(bookDeatils.getBook().getPublisher());

        JLabel bookAvailabilityLabel = addLabel("Count", 260);
        bookAvailabilityField = addTextFiled(260);
        bookAvailabilityField.setText(String.valueOf(bookDeatils.getAvailability()));

        JLabel foreignLiteratureLabel = addLabel("Foreign Literature:", 320);
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBounds(120, 320, 80, 40);
        if (bookDeatils.getBook().getForeignLiterature().equals(ForeignLiterature.NO)) {
            isForeignLiteratureCheckBox = new JCheckBox("", false);
        } else {
            isForeignLiteratureCheckBox = new JCheckBox("", true);
        }
        checkBoxPanel.add(isForeignLiteratureCheckBox);
        frame.add(checkBoxPanel);

        JButton editButton = new JButton("Edit Book");
        frame.add(editButton);
        editButton.setBounds(100, 400, 100, 30);
        editButton.addActionListener(l -> {
            edit(bookDeatils);
        });

        JButton closeButton = new JButton("Close");
        frame.add(closeButton);
        closeButton.setBounds(280, 400, 100, 30);
        closeButton.addActionListener(l -> {
            frame.dispose();
        });

        frame.setVisible(true);
    }

    private void edit(BookDetails bookDetails) {
        if (checkValues()) {
            bookDetails.getBook().setTitle(bookTitleField.getText());
            bookDetails.getBook().setAuthor(bookAuthorField.getText());
            bookDetails.getBook().setPrice(Double.parseDouble(bookPriceField.getText()));
            bookDetails.getBook().setPublisher(bookPublisherFiled.getText());
            bookDetails.getBook().setForeignLiterature(isForeignLiteratureCheckBox.isSelected() ? ForeignLiterature.YES : ForeignLiterature.NO);
            bookDetails.setBook(bookDetails.getBook());
            bookDetails.getID();
            bookDetails.setAvailability(Integer.parseInt(bookAvailabilityField.getText()));
            if (listener != null) {
                listener.bookEdited(bookDetails);
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
