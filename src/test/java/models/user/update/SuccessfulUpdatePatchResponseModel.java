package models.user.update;

public record SuccessfulUpdatePatchResponseModel(Integer id, String username, String firstName, String lastName, String email, String remoteAddr) {
}
