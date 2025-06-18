package pe.edu.upc.center.platform.user.domain.model.valueobjects;


import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String email) {

    public Email {
        if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
