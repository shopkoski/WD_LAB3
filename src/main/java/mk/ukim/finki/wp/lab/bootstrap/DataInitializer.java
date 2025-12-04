package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void initData(){
        if (authorRepository.count() > 0) {
            return;
        }

        // 1. Креирање и зачувување на Автори
        Author a1 = new Author("George", "Orwell", "UK", "Author of 1984");
        Author a2 = new Author("J.K.", "Rowling", "UK", "Harry Potter Creator");
        Author a3 = new Author("Stephen", "King", "USA", "Horror King");

        // Мора да ги зачуваме во база за да добијат ID
        authorRepository.saveAll(List.of(a1, a2, a3));

        // 2. Креирање и зачувување на Книги
        // Важно: Користиме 'save' наместо листи
        bookRepository.save(new Book("1984", "Dystopian", 4.8, a1));
        bookRepository.save(new Book("Animal Farm", "Satire", 4.5, a1));
        bookRepository.save(new Book("Harry Potter 1", "Fantasy", 5.0, a2));
        bookRepository.save(new Book("The Shining", "Horror", 4.7, a3));

        System.out.println("Базата е иницијализирана со податоци!");
    }
}
