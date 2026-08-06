package models.reviews.update;

public record UpdatePutReviewBodyModel (
        Integer club,
        String review,
        Integer assessment,
        Integer readPages
) {
}
