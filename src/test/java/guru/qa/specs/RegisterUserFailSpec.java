package guru.qa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static guru.qa.helpers.CustomAllureListener.withCustomTemplates;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;

public class RegisterUserFailSpec {
    public static RequestSpecification requestSpecificationRegisterFailed = with()
            .filter(withCustomTemplates())
            .basePath("/api/register")
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpecificationRegisterFailed = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("error", equalTo("Note: Only defined users succeed registration"))
            .build();
}
