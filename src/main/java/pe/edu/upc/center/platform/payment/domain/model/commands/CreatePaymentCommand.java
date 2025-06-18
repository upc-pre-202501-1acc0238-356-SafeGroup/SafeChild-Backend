package pe.edu.upc.center.platform.payment.domain.model.commands;

public record CreatePaymentCommand(Long userId, Long reservationId, Long cardId) {
}

