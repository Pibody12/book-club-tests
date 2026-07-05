package models.user.login;

import java.util.List;

public record EmptyBodyResponseModel(List<String> username, List<String> password) {
}
