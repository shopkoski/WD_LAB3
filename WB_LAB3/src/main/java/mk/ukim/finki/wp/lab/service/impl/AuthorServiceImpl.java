package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import mk.ukim.finki.wp.lab.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        // .findById враќа Optional, затоа мора да користиме orElseThrow
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with id " + id + " not found"));
    }

    @Override
    public void likeAuthor(Long id) {
        Author author = this.findById(id);
        author.addLike();
        authorRepository.save(author);
    }
}
