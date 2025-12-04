package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.service.AuthorService;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getBooksPage(@RequestParam(required = false) String error,
                               @RequestParam(required = false) String searchText,
                               @RequestParam(required = false) String searchRating,
                               Model model) {
        if(error != null) {
            model.addAttribute("error", error);
        }

        List<Book> books= bookService.listAll();

        if(searchText != null && !searchText.isEmpty() &&
                searchRating != null && !searchRating.isEmpty()) {
            books = bookService.searchBooks(searchText, Double.parseDouble(searchRating));
        } else {
            books = bookService.listAll();
        }

        model.addAttribute("books", books);
        model.addAttribute("searchText", searchText);
        model.addAttribute("searchRating", searchRating);

        return "listBooks";
    }
    @PostMapping("/add")
    public String saveBook(
            @RequestParam(required = false) Long id, // Ова е клучно: ID може да биде null (за нова книга)
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam Double averageRating,
            @RequestParam Long authorId) {

        if (id != null) {
            // Ако имаме ID, значи правиме АЖУРИРАЊЕ (Edit)
            bookService.edit(id, title, genre, averageRating, authorId);
        } else {
            // Ако немаме ID, значи правиме НОВА книга (Create)
            bookService.add(title, genre, averageRating, authorId);
        }
        return "redirect:/books";
    }

    @PostMapping("/edit/{bookId}")
    public String  editBook(@PathVariable Long bookId,
                            @RequestParam String title,
                            @RequestParam String genre,
                            @RequestParam Double averageRating,
                            @RequestParam Long authorId) {
        bookService.edit(bookId, title, genre, averageRating, authorId);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/book-form")
    public String getAddBookPage(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("book", null);
        return "book-form";
    }

    @GetMapping("/book-form/{id}")
    public String getEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        if(id == null) {
            return "redirect:/books?error=BookNotFound";
        }
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }

}