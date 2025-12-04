package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private double averageRating;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;


    public Book(String title, String genre, double averageRating) {
        this.title = title;
        this.genre = genre;
        this.averageRating = averageRating;
    }

    public Book(String title, String genre, double averageRating, Author author) {
        this.title = title;
        this.genre = genre;
        this.averageRating = averageRating;
        this.author = author;
    }

    public Book(){}
}