package pe.edu.upc.center.platform.card.domain.model.valueobjects;

public record Cvv(String code) {
    public  Cvv {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("CVV cannot be null or blank");
        }
        if (!code.matches("\\d{3}")) {
            throw new IllegalArgumentException("CVV must be 3 or 4 digits long");
        }
    }
}
