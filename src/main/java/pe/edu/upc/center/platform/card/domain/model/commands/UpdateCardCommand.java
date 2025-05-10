package pe.edu.upc.center.platform.card.domain.model.commands;

public record UpdateCardCommand(Long cardId, String number, String holder, int year,int month, String code) {
}
