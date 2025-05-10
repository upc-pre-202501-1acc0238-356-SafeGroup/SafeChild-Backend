package pe.edu.upc.center.platform.card.domain.model.valueobjects;

public record CardHolder(String holder) {
    public CardHolder {
        if (holder == null || holder.isBlank()) {
            throw new IllegalArgumentException("Card holder name cannot be null or blank");
        }
        if (holder.length() > 50) {
            throw new IllegalArgumentException("Card holder name cannot exceed 50 characters");
        }
    }
}
