package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Book;

import java.util.List;

public interface BookService {
    List<Book> listAll();
    List<Book> searchBooks(String text, double rating);

    Book findById(Long id);
    Book add(String title, String genre, double rating, Long authorId);
    Book edit(Long id, String title, String genre, double rating, Long authorId);
    void delete(Long id);
}