package guru.qa.tests;

import guru.qa.models.CreateUserModel;
import guru.qa.models.CreateUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static guru.qa.specs.CreateUserSpec.requestSpecificationCreate;
import static guru.qa.specs.CreateUserSpec.responseSpecificationCreate;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReqresInSimpleTests extends TestBase {
    @Test
    @DisplayName("CREATE wit Lombok Model")
    void createUser() {
        CreateUserModel createUserData = new CreateUserModel();
        createUserData.setName(randomUtils.userName);
        createUserData.setJob(randomUtils.userJob);

        CreateUserResponseModel createUserResponseModel = given()
                .contentType(JSON)
                .body(createUserData)
                .log().all()
                .when()
                .post("/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().as(CreateUserResponseModel.class);
        assertThat(createUserResponseModel.getName()).isEqualTo(randomUtils.userName);
        assertThat(createUserResponseModel.getJob()).isEqualTo(randomUtils.userJob);
        assertNotNull(createUserResponseModel.getId());
        assertThat(createUserResponseModel.getCreatedAt()).isGreaterThan(randomUtils.timeBeforeStartTest);

    }

   /* @Test
    @DisplayName("REGISTER - SUCCESSFUL")
    void registerSuccess() {
        given().
                contentType(JSON)
                .when()
                .body(randomUtils.jsonBodyRegister.toString())
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("id", notNullValue()
                        , "token", notNullValue());
    }

    @Test
    @DisplayName("REGISTER - UNSUCCESSFUL")
    void registerUnSuccess() {
        given().
                contentType(JSON)
                .when()
                .body(randomUtils.jsonBodyRegisterFail.toString())
                .post("/api/register")
                .then()
                .statusCode(400)
                .body("error", equalTo("Note: Only defined users succeed registration"));
    }

    @Test
    @DisplayName("LOGIN - SUCCESSFUL")
    void loginSuccess() {
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
    void loginFailed() {
        given().
                contentType(JSON)
                .body(randomUtils.jsonUserEmail.toString())
                .when()
                .post("/api/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));

    }*/

}
