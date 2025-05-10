package pe.edu.upc.center.platform.payment.interfaces.rest.resources;

public record CreatePaymentResource(Long userId,
                                    Long reservationId,
                                    Long cardId) {
}

