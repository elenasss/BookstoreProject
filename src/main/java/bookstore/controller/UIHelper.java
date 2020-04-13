package bookstore.controller;

import javax.swing.*;

public class UIHelper {
    protected static void showErrorMessage(String message){
        JOptionPane.showMessageDialog(null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    protected static void showInformationMessage(String message){
        JOptionPane.showMessageDialog(null,
                message,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    protected static void showWarningMessage(String message){
        JOptionPane.showMessageDialog(null,
                message,
                "Error",
                JOptionPane.WARNING_MESSAGE);
    }
}
