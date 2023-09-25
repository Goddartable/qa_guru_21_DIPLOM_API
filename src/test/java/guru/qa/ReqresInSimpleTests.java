package guru.qa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInSimpleTests extends TestBase{
    @Test
    @DisplayName("LOGIN - UNSUCCESSFUL")
    void loginFailed(){
        given().
                contentType(JSON)
                .body(randomUtils.jsonUserEmail.toString())
                .when()
                .post("/api/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));

    }

}
