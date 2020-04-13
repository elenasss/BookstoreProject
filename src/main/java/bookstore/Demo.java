package bookstore;

import bookstore.controller.LoginForm;

public class Demo {
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        loginForm.createLoginForm();
        loginForm.show();
    }
}
