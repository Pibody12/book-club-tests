package specs.club;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateClubSpec {

    public static ResponseSpecification successfulUpdateCLubResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(200)
                    .expectBody(matchesJsonSchemaInClasspath(
                            "schemas/club/successful_update_club_response_ schema.json"))
                    .expectBody("id", notNullValue())
                    .expectBody("bookTitle", notNullValue())
                    .expectBody("bookAuthors", notNullValue())
                    .expectBody("publicationYear", notNullValue())
                    .expectBody("description", notNullValue())
                    .expectBody("telegramChatLink", notNullValue())
                    .expectBody("owner", notNullValue())
                    .expectBody("members", notNullValue())
                    .build();
}
