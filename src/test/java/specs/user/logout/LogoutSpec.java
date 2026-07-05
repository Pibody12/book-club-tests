package specs.user.logout;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class LogoutSpec {

    public static ResponseSpecification logoutSuccessfulResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/user/logout/successful_logout_response_schema.json"))
            .build();

    public static ResponseSpecification wrongRefreshTokenLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/user/logout/wrong_refresh_token_logout_response_schema.json"))
            .build();

    public static ResponseSpecification emptyBodyLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/user/logout/empty_body_logout_response_schema.json"))
            .build();

    public static ResponseSpecification listWithTwoTokenLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/user/logout/list_with_two_token_logout_response_schema.json"))
            .build();
}
