package specs.club;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;

public class JoinClubSpec {

    public static ResponseSpecification successfulJoinClubResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(204)
                    .build();
}
