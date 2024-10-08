package edu.avada.course.model.dto;

import edu.avada.course.model.entity.Bid.BidStatus;
import java.time.LocalDate;

public record BidDto(
        String name,
        String phone,
        String email,
        String comment,
        LocalDate date,
        BidStatus status
) {
    public BidDto(
            String name,
            String phone,
            String email,
            String comment,
            LocalDate date,
            BidStatus status
    ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.comment = comment;
        this.date = date;
        this.status = status;
    }
}
