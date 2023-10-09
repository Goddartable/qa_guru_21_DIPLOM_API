package guru.qa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserSpec {
    public static RequestSpecification requestSpecificationCreate = with()
            .basePath("/api/users")
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpecificationCreate = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("name", notNullValue())
            .expectBody("job", notNullValue())
            .expectBody("id", notNullValue())
            .expectBody("createdAt", notNullValue())
            .build();
}