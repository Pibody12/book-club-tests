package models.reviews.update;

import models.reviews.UserModel;

public record SuccessfulUpdateReviewResponseModel(
        Integer id,
        Integer club,
        UserModel user,
        String review,
        Integer assessment,
        Integer readPages,
        String created,
        String modified
) {
}
