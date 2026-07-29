package models.localstorage;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import models.user.SuccessfulRegistrationAndGetResponseModel;

public record LocalStorageAuthModel(
        @JsonProperty("user") SuccessfulRegistrationAndGetResponseModel registrationResponse,
        @JsonProperty("accessToken") String accessToken,
        @JsonProperty("refreshToken") String refreshToken,
        @JsonProperty("isAuthenticated") boolean isAuthenticated
) {

    private static final JsonMapper JSON = JsonMapper.builder().build();

    public String toJson() {
        try {
            return JSON.writeValueAsString(this);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
