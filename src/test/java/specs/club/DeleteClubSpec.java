package specs.club;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteClubSpec {

    public static ResponseSpecification successfulDeleteCLubResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(204)
                    .build();
}
