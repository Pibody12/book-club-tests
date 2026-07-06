package api;


import api.club.CreateClubApiClient;
import api.club.UpdateClubApiClient;
import api.user.LoginApiClient;
import api.user.LogoutApiClient;
import api.user.RegistreApiClient;
import api.user.UpdateApiClient;

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

}
