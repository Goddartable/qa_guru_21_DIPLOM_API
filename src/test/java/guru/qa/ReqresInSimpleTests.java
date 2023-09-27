package guru.qa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInSimpleTests extends TestBase{
    @Test
    @DisplayName("CREATE")
    void createUser(){
        given().
                contentType(JSON)
                .body(randomUtils.jsonBodyCreate.toString())
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo(randomUtils.userName)
                        , "job", equalTo(randomUtils.userJob)
                        , "id", notNullValue()
                        , "createdAt", greaterThan(randomUtils.timeBeforeStartTest));
    }
    @Test
    @DisplayName("REGISTER - SUCCESSFUL")
    void registerSuccess(){
        given().
                contentType(JSON)
                .when()
                .body(randomUtils.jsonBodyRegister.toString())
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("id",notNullValue()
                , "token", notNullValue());
    }

    @Test
    @DisplayName("REGISTER - UNSUCCESSFUL")
    void registerUnSuccess(){
        given().
                contentType(JSON)
                .when()
                .body(randomUtils.jsonBodyRegisterFail.toString())
                .post("/api/register")
                .then()
                .statusCode(400)
                .body("error",equalTo("Note: Only defined users succeed registration"));
    }

    @Test
    @DisplayName("LOGIN - SUCCESSFUL")
    void loginSuccess(){
        given().
                contentType(JSON)
                .body(randomUtils.jsonBodyRegister.toString())
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));

    }

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
