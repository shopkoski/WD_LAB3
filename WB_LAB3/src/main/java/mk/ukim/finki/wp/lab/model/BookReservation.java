package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter @Setter @AllArgsConstructor

public class BookReservation {
    @Id
    @GeneratedValue
    private Long id;

    private String bookTitle;
    private String readerName;
    private String readerAddress;
    private int numberOfCopies;

    public BookReservation(String bookTitle, String readerName, String readerAddress, int numberOfCopies) {
        this.bookTitle = bookTitle;
        this.readerName = readerName;
        this.readerAddress = readerAddress;
        this.numberOfCopies = numberOfCopies;
    }
    public BookReservation(){}
}
