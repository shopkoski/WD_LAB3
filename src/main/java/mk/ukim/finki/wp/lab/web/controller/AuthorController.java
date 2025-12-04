package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAuthorsPage(Model model){
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "listAuthors";
    }

    @PostMapping("/like/{id}")
    public String likeAuthor(@PathVariable Long id){
        authorService.likeAuthor(id);
        return "redirect:/authors";
    }
}