package models.club.create;

public record CreateClubBodyModel (
        String bookTitle,
        String bookAuthors,
        Integer publicationYear,
        String description,
        String telegramChatLink) {

}
