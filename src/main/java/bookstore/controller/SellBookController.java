package bookstore.controller;

import bookstore.utils.Helper;

import javax.swing.*;

import static bookstore.controller.UIHelper.showErrorMessage;
import static bookstore.controller.UIHelper.showInformationMessage;

public class SellBookController {

    private JFrame sellFrame;
    private JTextField countField;
    private SellBookListener sellListener;


    public void setListener(SellBookListener sellListener) {
        this.sellListener = sellListener;
    }

    public void sellBook() {
        sellFrame = new JFrame("Sell Book");
        sellFrame.setBounds(200, 200, 400, 300);
        sellFrame.setLayout(null);
        sellFrame.setVisible(true);

        JLabel jLabel = new JLabel("Please enter the count of sold books");
        jLabel.setBounds(20, 20, 250, 40);
        sellFrame.add(jLabel);

        countField = new JTextField();
        countField.setBounds(20, 80, 150, 40);
        sellFrame.add(countField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(20, 150, 100, 40);
        sellFrame.add(confirmButton);
        confirmButton.addActionListener(e -> {
            checkCount();
            countField.setText(null);
        });

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(200, 150, 100, 40);
        sellFrame.add(closeButton);
        closeButton.addActionListener(e -> {
            sellFrame.dispose();
        });
    }

    private void checkCount() {
        if (countField.getText().isEmpty()) {
            showInformationMessage("Please enter a count of sold books");
        } else {
            if (Helper.isStringInt(countField.getText()) && Integer.parseInt(countField.getText()) > 0) {
                if (sellListener != null) {
                    sellListener.booksSold(Integer.parseInt(countField.getText()));
                }
            } else {
                showErrorMessage("Please enter a correct value");
            }
        }
    }
}