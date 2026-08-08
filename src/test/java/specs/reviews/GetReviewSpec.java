package specs.reviews;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class GetReviewSpec {
    public static ResponseSpecification getDeletedReviewsResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(404)
                    .expectBody(matchesJsonSchemaInClasspath(
                            "schemas/reviews/get_deleted_reviews_schema.json"))
                    .expectBody("detail", notNullValue())
                    .build();
}
