package bookstore.model;

import lombok.Data;

@Data
public class Book {

    private String title;
    private String author;
    private double price;
    private String publisher;
    private ForeignLiterature foreignLiterature;
}
