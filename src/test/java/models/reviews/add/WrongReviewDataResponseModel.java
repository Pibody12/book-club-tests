package models.reviews.add;

import java.util.List;

public record WrongReviewDataResponseModel(List<String> club, List<String> review, List<String> assessment) {
}
