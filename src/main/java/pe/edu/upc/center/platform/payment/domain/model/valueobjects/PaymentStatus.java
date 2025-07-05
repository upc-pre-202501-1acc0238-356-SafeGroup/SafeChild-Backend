package pe.edu.upc.center.platform.payment.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentStatus {
    REQUIRES_PAYMENT_METHOD,
    REQUIRES_CONFIRMATION,
    REQUIRES_ACTION,
    PROCESSING,
    SUCCEEDED,
    CANCELED,
    REQUIRES_CAPTURE;
    @JsonCreator
    public static PaymentStatus fromString(String value) {
        return PaymentStatus.valueOf(value.toUpperCase());
    }
}
