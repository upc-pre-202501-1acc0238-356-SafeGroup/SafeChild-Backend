package pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Phone(String number) {
    public Phone {
        if (number == null || !number.matches("^\\d{9}$")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}