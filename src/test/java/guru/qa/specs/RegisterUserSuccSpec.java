package guru.qa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.notNullValue;

public class RegisterUserSuccSpec {
    public static RequestSpecification requestSpecificationRegister = with()
            .basePath("/api/register")
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpecificationRegister = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("id", notNullValue())
            .expectBody("token", notNullValue())
            .build();
}
