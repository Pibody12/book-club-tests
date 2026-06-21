package specs.logout;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class LogoutSpec {

    public static RequestSpecification logoutRequestSpec = with()
            .log().all()
            .contentType(JSON)
            .basePath("/api/v1");

    public static ResponseSpecification logoutSuccessfulResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/logout/successful_logout_response_schema.json"))
            .build();

    public static ResponseSpecification wrongRefreshTokenLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/logout/wrong_refresh_token_logout_response_schema.json"))
            .build();

    public static ResponseSpecification emptyBodyLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/logout/empty_body_logout_response_schema.json"))
            .build();

    public static ResponseSpecification listWithTwoTokenLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/logout/list_with_two_token_logout_response_schema.json"))
            .build();
}
