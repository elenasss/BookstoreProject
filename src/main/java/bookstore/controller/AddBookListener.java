package bookstore.controller;

import bookstore.model.BookDetails;

public interface AddBookListener {
    void onBookCreated(BookDetails book);
}
