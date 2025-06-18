package pe.edu.upc.center.platform.card.domain.model.valueobjects;


public record ExpirationDate(int year, int month) {

    public ExpirationDate {
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
    }
}

