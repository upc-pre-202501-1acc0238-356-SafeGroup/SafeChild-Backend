package pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record CompleteName(String completeName) {
    public CompleteName(){
        this(null);
    }

    public CompleteName {
        if(completeName == null || completeName.isBlank()) {
            throw new IllegalArgumentException("The complete name cannot be null or blank");
        }
    }
}
