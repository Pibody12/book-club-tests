package models.reviews;

public record AddReviewBodyModel (
        Integer club,
        String review,
        Integer assessment,
        Integer readPages
) {
}
