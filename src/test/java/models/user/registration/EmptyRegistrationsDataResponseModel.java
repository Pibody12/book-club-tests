package models.user.registration;

import java.util.List;

public record EmptyRegistrationsDataResponseModel (List<String> username, List<String> password) {
}
