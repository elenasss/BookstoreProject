package bookstore.controller;

import javax.swing.*;

public class LoginForm {

    private static JFrame loginFrame;
    private static boolean isAdmin;
    private UIController uiController;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
    }

    public void createLoginForm() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setBounds(500, 300, 400, 250);
        loginFrame.setLayout(null);

        addLabel("Username", 50);
        addLabel("Password", 100);

        usernameField = new JTextField();
        usernameField.setBounds(120, 50, 200, 30);
        loginFrame.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 100, 200, 30);
        loginFrame.add(passwordField);

        JButton signInButton = new JButton("Sign in");
        signInButton.setBounds(180, 150, 100, 30);
        loginFrame.add(signInButton);

        signInButton.addActionListener(e -> {
            loginAttempt();
        });
    }

    public void show() {
        loginFrame.setVisible(true);
    }

    private void loginAttempt() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.equals("bookstore") && password.equals("admin")) {
            isAdmin = true;
            openBookstore();
        } else if (username.equals("bookstore") && password.equals("user")) {
            JOptionPane.showMessageDialog(loginFrame, "You are logged in as USER");
            openBookstore();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "The username or password is invalid!");
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    private void openBookstore() {
        uiController = new UIController(isAdmin);
        uiController.createUI();
        uiController.show();
        loginFrame.dispose();
    }

    private void addLabel(String title, int y) {
        JLabel jLabel = new JLabel(title);
        jLabel.setBounds(50, y, 60, 30);
        loginFrame.add(jLabel);
    }
}
