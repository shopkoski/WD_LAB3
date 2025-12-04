package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookReservation")
public class BookReservationController {

    private final BookReservationService bookReservationService;

    public BookReservationController(BookReservationService bookReservationService) {
        this.bookReservationService = bookReservationService;
    }

    @PostMapping
    public String placeReservation(@RequestParam String bookTitle,
                                   @RequestParam String readerName,
                                   @RequestParam String readerAddress,
                                   @RequestParam int numberOfCopies,
                                   HttpServletRequest request,
                                   Model model) {

        // 1. Create the reservation
        BookReservation bookReservation = bookReservationService.placeReservation(bookTitle, readerName, readerAddress, numberOfCopies);

        // 2. FIXED: Pass the WHOLE object with the key "reservation"
        // This matches ${reservation.readerName} in your HTML
        model.addAttribute("reservation", bookReservation);

        // 3. FIXED: Change key to "clientIp"
        // This matches ${clientIp} in your HTML
        model.addAttribute("clientIp", request.getRemoteAddr());

        // Ensure your HTML file is named "reservationConfirmation.html" inside templates
        return "reservationConfirmation";
    }
}