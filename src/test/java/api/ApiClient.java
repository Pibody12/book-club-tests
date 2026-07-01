package api;


/**
 * Общий API-клиент — единая точка доступа к клиентам эндпоинтов.
 */
public class ApiClient {
    public final RegistreApiClient registre = new RegistreApiClient();
    public final LoginApiClient login = new LoginApiClient();
    public final LogoutApiClient logout = new LogoutApiClient();
    public final UpdateApiClient update = new UpdateApiClient();

}
