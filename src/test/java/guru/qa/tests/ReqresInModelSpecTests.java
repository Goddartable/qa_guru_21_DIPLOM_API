package guru.qa.tests;

import guru.qa.models.*;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

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
            assertThat(createUserResponseModel.getName()).isEqualTo(randomUtils.userName);
            assertThat(createUserResponseModel.getJob()).isEqualTo(randomUtils.userJob);
            assertThat(createUserResponseModel.getId()).isNotNull();
            assertThat(createUserResponseModel.getCreatedAt()).isGreaterThan(randomUtils.timeBeforeStartTest);
        });
    }

    @Test
    @Tag("reqres")
    @DisplayName("REGISTER - SUCCESSFUL с использованием Lombok, Model и Spec")
    void registerSuccessTest() {
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
            assertThat(registerUserResponseModel.getId()).isNotNull();
            assertThat(registerUserResponseModel.getToken()).isNotNull();
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
            assertThat(registerFailResponseModel.getError());
        });
    }

    @Test
    @Tag("reqres")
    @DisplayName("LIST USERS с использованием Lombok, Model и Spec")
    void getListUsersTest() {
        GetListUserModel getListUserModel = step("Запрос на просмотр списка пользователей", () ->
                given()
                        .spec(requestGetListUserSpec)
                        .when()
                        .get()
                        .then()
                        .spec(responseGetListUserSpec)
                        .extract().as(GetListUserModel.class));
        step("Проверка данных о количестве страниц", () -> {
            assertEquals(1, getListUserModel.getPage());
            assertEquals(6, getListUserModel.getPerPage());
            assertEquals(12, getListUserModel.getTotal());
            assertEquals(2, getListUserModel.getTotalPages());
        });
        step("Проверка данных пользователя", () -> {
            List<GetUserDataList> data = getListUserModel.getData();
            assertEquals(4, data.get(3).getId());
            assertEquals("eve.holt@reqres.in", data.get(3).getEmail());
            assertEquals("Eve", data.get(3).getFirstName());
            assertEquals("Holt", data.get(3).getLastName());
            assertEquals("https://reqres.in/img/faces/4-image.jpg", data.get(3).getAvatar());
        });
        step("Проверка данных о поддержке", () -> {
            GetUserSupportModel getUserSupport = getListUserModel.getSupport();
            assertEquals("https://reqres.in/#support-heading", getUserSupport.getUrl());
            assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", getUserSupport.getText());
        });
    }
}
