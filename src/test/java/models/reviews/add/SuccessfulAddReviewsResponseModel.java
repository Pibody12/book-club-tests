package models.reviews.add;

import models.reviews.UserModel;

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

