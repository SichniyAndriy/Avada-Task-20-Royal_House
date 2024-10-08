package edu.avada.course.model.dto;

import edu.avada.course.model.entity.CompanyService.ServiceStatus;
import java.time.LocalDate;

public record CompanyServiceDto(
        String title,
        String description,
        LocalDate date,
        ServiceStatus status,
        String imagePath
) {
    public CompanyServiceDto(
            String title,
            String description,
            LocalDate date,
            ServiceStatus status,
            String imagePath
    ) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
        this.imagePath = imagePath;
    }
}
