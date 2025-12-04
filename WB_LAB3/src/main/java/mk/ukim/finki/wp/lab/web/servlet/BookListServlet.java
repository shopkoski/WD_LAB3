package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.service.BookService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="BookListServlet", urlPatterns = "")
public class BookListServlet extends HttpServlet {

    private final BookService bookService;
    private final SpringTemplateEngine springTemplateEngine;

    public BookListServlet(BookService bookService, SpringTemplateEngine springTemplateEngine) {
        this.bookService = bookService;
        this.springTemplateEngine = springTemplateEngine;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange= JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        List<Book> books = new ArrayList<>();

        String searchText = req.getParameter("searchText");
        String searchRating = req.getParameter("searchRating");

        Double rating = null;
        if (searchRating != null && !searchRating.trim().isEmpty()) {
            rating = Double.parseDouble(searchRating);
        }

        if (searchText != null && !searchText.trim().isEmpty() && rating != null) {
            books = bookService.searchBooks(searchText, rating);
        } else {
            books = bookService.listAll();
        }
        context.setVariable("books", books);
        springTemplateEngine.process("listBooks", context, resp.getWriter());

    }
}

