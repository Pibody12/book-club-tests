package api;


import api.club.*;
import api.reviews.AddReviewApiClient;
import api.reviews.DeleteReviewApiClient;
import api.reviews.GetReviewApiClient;
import api.reviews.UpdateReviewApiClient;
import api.user.*;

/**
 * Общий API-клиент — единая точка доступа к клиентам эндпоинтов.
 */
public class ApiClient {
    public final RegistreApiClient registre = new RegistreApiClient();
    public final LoginApiClient login = new LoginApiClient();
    public final LogoutApiClient logout = new LogoutApiClient();
    public final UpdateApiClient update = new UpdateApiClient();
    public final CreateClubApiClient createClub = new CreateClubApiClient();
    public final UpdateClubApiClient updateClub = new UpdateClubApiClient();
    public final DeleteClubApiClient deleteClub = new DeleteClubApiClient();
    public final JoinClubApiClient joinClub = new JoinClubApiClient();
    public final AddReviewApiClient addReviews = new AddReviewApiClient();
    public final UpdateReviewApiClient updateReviews = new UpdateReviewApiClient();
    public final DeleteReviewApiClient deleteReviews = new DeleteReviewApiClient();
    public final GetReviewApiClient getReviews = new GetReviewApiClient();
    public final GetClubApiClient getClub = new GetClubApiClient();
    public final GetUserApiClient getUser = new GetUserApiClient();

}
