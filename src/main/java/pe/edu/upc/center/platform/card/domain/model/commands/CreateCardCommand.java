package pe.edu.upc.center.platform.card.domain.model.commands;

public record CreateCardCommand(Long userId, String number, String holder, int year,int month, String code) {
}
