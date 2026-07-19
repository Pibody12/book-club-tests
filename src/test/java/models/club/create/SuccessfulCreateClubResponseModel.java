package models.club.create;

import java.util.List;

public record SuccessfulCreateClubResponseModel (
        Integer id,
        String bookTitle,
        String bookAuthors,
        Integer publicationYear,
        String description,
        String telegramChatLink,
        Integer owner,
        List<String> members,
        List<String> reviews,
        String created,
        Boolean modified) {
}
