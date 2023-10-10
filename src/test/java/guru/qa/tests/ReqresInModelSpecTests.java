package guru.qa.tests;

import guru.qa.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static guru.qa.specs.CreateUserSpec.requestSpecificationCreate;
import static guru.qa.specs.CreateUserSpec.responseSpecificationCreate;
import static guru.qa.specs.RegisterUserFailSpec.requestSpecificationRegisterFailed;
import static guru.qa.specs.RegisterUserFailSpec.responseSpecificationRegisterFailed;
import static guru.qa.specs.RegisterUserSuccSpec.requestSpecificationRegister;
import static guru.qa.specs.RegisterUserSuccSpec.responseSpecificationRegister;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReqresInModelSpecTests extends TestBase {
    @Test
    @DisplayName("CREATE with Lombok Model and Spec")
    void createUser() {
        CreateUserModel createUserData = new CreateUserModel();
        createUserData.setName(randomUtils.userName);
        createUserData.setJob(randomUtils.userJob);

        CreateUserResponseModel createUserResponseModel = given()
                .spec(requestSpecificationCreate)
                .body(createUserData)
                .when()
                .post()
                .then()
                .spec(responseSpecificationCreate)
                .extract().as(CreateUserResponseModel.class);
        assertThat(createUserResponseModel.getName()).isEqualTo(randomUtils.userName);
        assertThat(createUserResponseModel.getJob()).isEqualTo(randomUtils.userJob);
        assertNotNull(createUserResponseModel.getId());
        assertThat(createUserResponseModel.getCreatedAt()).isGreaterThan(randomUtils.timeBeforeStartTest);

    }

    @Test
    @DisplayName("REGISTER - SUCCESSFUL with Lombok Model and Spec")
    void registerSuccess() {
        RegisterModel registerUserData = new RegisterModel();
        registerUserData.setEmail("eve.holt@reqres.in");
        registerUserData.setPassword(randomUtils.userPassword);

        RegisterSuccessfulResponseModel registerUserResponseModel = given()
                .spec(requestSpecificationRegister)
                .body(registerUserData)
                .when()
                .post()
                .then()
                .spec(responseSpecificationRegister)
                .extract().as(RegisterSuccessfulResponseModel.class);
        assertNotNull(registerUserResponseModel.getId());
        assertNotNull(registerUserResponseModel.getToken());
    }

   @Test
    @DisplayName("REGISTER - UNSUCCESSFUL with Lombok Model and Spec")
    void registerUnSuccess() {
       RegisterModel registerUserData = new RegisterModel();
       registerUserData.setEmail(randomUtils.userEmail);
       registerUserData.setPassword(randomUtils.userPassword);

       RegisterFailResponseModel registerFailResponseModel = given()
               .spec(requestSpecificationRegisterFailed)
               .body(registerUserData)
               .when()
               .post()
               .then()
               .spec(responseSpecificationRegisterFailed)
               .extract().as(RegisterFailResponseModel.class);
       assertThat(registerFailResponseModel.getError());
    }

    @Test
    @DisplayName("LIST USERS with Lombok Model and Spec")
    void getListUsers(){
        GetListUserModel getListUserModel = new GetListUserModel();
        given().
                contentType(JSON)
                .body()
                .when("/users?page=2")
                .post()
                .then()
                .statusCode()
                .body();
    }

    }

   /* @Test
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
