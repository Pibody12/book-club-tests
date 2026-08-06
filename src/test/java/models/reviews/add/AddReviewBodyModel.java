package models.reviews.add;

public record AddReviewBodyModel (
        Integer club,
        String review,
        Integer assessment,
        Integer readPages
) {
}
