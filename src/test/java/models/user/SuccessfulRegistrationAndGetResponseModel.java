package models.user;

public record SuccessfulRegistrationAndGetResponseModel(
        Integer id,
        String username,
        String firstName,
        String lastName,
        String email,
        String remoteAddr
) {
}
