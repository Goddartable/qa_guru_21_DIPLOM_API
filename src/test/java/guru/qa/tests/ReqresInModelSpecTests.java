package guru.qa.tests;

import guru.qa.models.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static guru.qa.specs.CreateUserSpec.requestSpecificationCreate;
import static guru.qa.specs.CreateUserSpec.responseSpecificationCreate;
import static guru.qa.specs.RegisterUserFailSpec.requestSpecificationRegisterFailed;
import static guru.qa.specs.RegisterUserFailSpec.responseSpecificationRegisterFailed;
import static guru.qa.specs.RegisterUserSuccSpec.requestSpecificationRegister;
import static guru.qa.specs.RegisterUserSuccSpec.responseSpecificationRegister;
import static guru.qa.specs.GetListUserSpec.requestGetListUserSpec;
import static guru.qa.specs.GetListUserSpec.responseGetListUserSpec;
import static guru.qa.specs.SuccLoginSpec.requestSpecificationSuccLogin;
import static guru.qa.specs.SuccLoginSpec.responseSpecificationSuccLogin;

public class ReqresInModelSpecTests extends TestBase {

    @Test
    @Tag("reqres")
    @DisplayName("LOGIN - SUCCESSFUL с использованием Lombok, Model и Spec")
    void loginSuccessTest() {
        LoginModel loginModel = new LoginModel();
        loginModel.setEmail("eve.holt@reqres.in");
        loginModel.setPassword("cityslicka");

        LoginResponseModel succLoginResponseModel = step("Запрос на авторизацию пользователя", () ->
                given()
                        .spec(requestSpecificationSuccLogin)
                        .body(loginModel)
                        .when()
                        .post()
                        .then()
                        .spec(responseSpecificationSuccLogin)
                        .extract().as(LoginResponseModel.class));
        step("Проверка ответа об успешной авторизации пользователя", () ->
                assertThat(succLoginResponseModel.getToken()).isNotNull());
    }

    @Test
    @Tag("reqres")
    @DisplayName("CREATE с использованием Lombok, Model и Spec")
    void createUserTest() {
        SoftAssertions softly = new SoftAssertions();
        CreateUserModel createUserData = new CreateUserModel();
        createUserData.setName(randomUtils.userName);
        createUserData.setJob(randomUtils.userJob);

        CreateUserResponseModel createUserResponseModel = step("Запрос на создание пользователя", () ->
                given()
                        .spec(requestSpecificationCreate)
                        .body(createUserData)
                        .when()
                        .post()
                        .then()
                        .spec(responseSpecificationCreate)
                        .extract().as(CreateUserResponseModel.class));
        step("Проверка ответа об успешном создании пользователя", () -> {
            softly.assertThat(createUserResponseModel.getName()).isEqualTo(randomUtils.userName);
            softly.assertThat(createUserResponseModel.getJob()).isEqualTo(randomUtils.userJob);
            softly.assertThat(createUserResponseModel.getId()).isNotNull();
            softly.assertThat(createUserResponseModel.getCreatedAt()).isGreaterThan(randomUtils.timeBeforeStartTest);
        });
    }

    @Test
    @Tag("reqres")
    @DisplayName("REGISTER - SUCCESSFUL с использованием Lombok, Model и Spec")
    void registerSuccessTest() {
        SoftAssertions softly = new SoftAssertions();
        RegisterModel registerUserData = new RegisterModel();
        registerUserData.setEmail("eve.holt@reqres.in");
        registerUserData.setPassword(randomUtils.userPassword);

        RegisterSuccessfulResponseModel registerUserResponseModel = step("Запрос на регистрацию пользователя", () ->
                given()
                        .spec(requestSpecificationRegister)
                        .body(registerUserData)
                        .when()
                        .post()
                        .then()
                        .spec(responseSpecificationRegister)
                        .extract().as(RegisterSuccessfulResponseModel.class));
        step("Проверка ответа об успешной регистрации пользователя", () -> {
            softly.assertThat(registerUserResponseModel.getId()).isNotNull();
            softly.assertThat(registerUserResponseModel.getToken()).isNotNull();
        });
    }

    @Test
    @Tag("reqres")
    @DisplayName("REGISTER - UNSUCCESSFUL с использованием Lombok, Model и Spec")
    void registerUnSuccessTest() {
        RegisterModel registerUserData = new RegisterModel();
        registerUserData.setEmail(randomUtils.userEmail);
        registerUserData.setPassword(randomUtils.userPassword);

        RegisterFailResponseModel registerFailResponseModel = step("Запрос на регистрацию пользователя", () ->
                given()
                        .spec(requestSpecificationRegisterFailed)
                        .body(registerUserData)
                        .when()
                        .post()
                        .then()
                        .spec(responseSpecificationRegisterFailed)
                        .extract().as(RegisterFailResponseModel.class));
        step("Проверка ответа о неудачной регистрации пользователя", () -> {
            assertThat("Note: Only defined users succeed registration").isEqualTo(registerFailResponseModel.getError());
        });
    }

    @Test
    @Tag("reqres")
    @DisplayName("LIST USERS с использованием Lombok, Model и Spec")
    void getListUsersTest() {
        SoftAssertions softly = new SoftAssertions();
        GetListUserModel getListUserModel = step("Запрос на просмотр списка пользователей", () ->
                given()
                        .spec(requestGetListUserSpec)
                        .when()
                        .get()
                        .then()
                        .spec(responseGetListUserSpec)
                        .extract().as(GetListUserModel.class));
        step("Проверка данных о количестве страниц", () -> {
            softly.assertThat(1).isEqualTo(getListUserModel.getPage());
            softly.assertThat(6).isEqualTo(getListUserModel.getPerPage());
            softly.assertThat(12).isEqualTo(getListUserModel.getTotal());
            softly.assertThat(2).isEqualTo(getListUserModel.getTotalPages());
        });
        step("Проверка данных пользователя", () -> {
            List<GetUserDataList> data = getListUserModel.getData();
            softly.assertThat(4).isEqualTo(data.get(3).getId());
            softly.assertThat("eve.holt@reqres.in").isEqualTo(data.get(3).getEmail());
            softly.assertThat("Eve").isEqualTo(data.get(3).getFirstName());
            softly.assertThat("Holt").isEqualTo(data.get(3).getLastName());
            softly.assertThat("https://reqres.in/img/faces/4-image.jpg").isEqualTo(data.get(3).getAvatar());
        });
        step("Проверка данных о поддержке", () -> {
            GetUserSupportModel getUserSupport = getListUserModel.getSupport();
            softly.assertThat("https://reqres.in/#support-heading").isEqualTo(getUserSupport.getUrl());
            softly.assertThat("To keep ReqRes free, contributions towards server costs are appreciated!").isEqualTo(getUserSupport.getText());
        });
    }
}
