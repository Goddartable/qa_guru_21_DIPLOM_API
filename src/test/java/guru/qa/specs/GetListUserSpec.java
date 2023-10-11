package guru.qa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class GetListUserSpec {
    public static RequestSpecification requestGetListUserSpec = with()
            .basePath("/api/users?page=2")
            .log().all()
            .contentType(JSON);

    public static ResponseSpecification responseGetListUserSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .build();
}
