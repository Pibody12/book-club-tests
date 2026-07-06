package models.club.update;

public record UpdateClubBodyModel (
        String bookTitle,
        String bookAuthors,
        Integer publicationYear,
        String description,
        String telegramChatLink) {
}
