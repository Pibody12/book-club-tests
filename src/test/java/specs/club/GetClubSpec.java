package specs.club;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class GetClubSpec {

    public static ResponseSpecification getDeletedCLubResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(404)
                    .expectBody(matchesJsonSchemaInClasspath(
                            "schemas/club/get_deleted_club_schema.json"))
                    .expectBody("detail", notNullValue())
                    .build();
}
