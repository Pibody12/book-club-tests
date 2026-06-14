package models.registration.lombok;

import lombok.Data;

import java.util.List;

@Data
public class InvalidUserResponseLombokModel {
    List<String> username;
}
