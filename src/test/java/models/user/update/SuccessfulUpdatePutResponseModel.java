package models.user.update;

public record SuccessfulUpdatePutResponseModel(Integer id, String username, String firstName, String lastName, String email, String remoteAddr) {
}
