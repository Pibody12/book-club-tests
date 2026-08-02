package models.reviews;

import java.time.OffsetDateTime;

public record SuccessfulAddReviewsResponseModel(
        Integer id,
        Integer club,
        UserModel user,
        String review,
        Integer assessment,
        Integer readPages,
        String created,
        Boolean modified
) {
}

