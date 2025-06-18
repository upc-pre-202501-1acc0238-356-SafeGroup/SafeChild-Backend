package pe.edu.upc.center.platform.user.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Document(String doc) {
    public Document{
        if (doc == null || !doc.matches("^\\d{8}$")) {
            throw new IllegalArgumentException("The document must have exactly 8 digits.");
        }
    }
}
