package test_data;

public class TestData {

    // credentials
    public static final String USERNAME = "Tim42";
    public static final String PASSWORD = "qweasd";

    // variable
    public static final String PREFIX_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
    public static final String ipAddrRegexp = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
            + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
    public static final String WRONG_REFRESH_TOKEN = "wrongRefrshToken";

    // message
    public static final String BLANK_FIELD = "This field may not be blank.";
    public static final String INVALIDE_CREDLS = "Invalid username or password.";
    public static final String REQUIRED_FIELD_MESSAGE = "This field is required.";
    public static final String EXIST_USER_MESSAGE = "A user with that username already exists.";
    public static final String INVALIDE_USERNAME = "Enter a valid username. This value may contain only letters, numbers, and @/./+/-/_ characters.";
    public static final String INVALIDE_REFRESH_TOKEN_MESSAGE = "JSON parse error - unexpected character: line 1 column 1 (char 0)";
    public static final String INVALIDE_DATA_MESSAGE = "Invalid data. Expected a dictionary, but got list.";
    public static final String NO_CLUB_MESSAGE = "No Club matches the given query.";
    public static final String INVALIDE_ID_CLUB_MESSAGE = "Invalid pk \"0\" - object does not exist.";
    public static final String EMPTY_FIELD_REVIEWS_MESSAGE = "This field may not be blank.";
    public static final String INVALIDE_ASSESSMENT_MESSAGE = "Ensure this value is greater than or equal to 1.";
}
