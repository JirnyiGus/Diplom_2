package pens;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.User;

import static io.restassured.RestAssured.given;

public class UserAPI {

    @Step("Создания пользователя")

    public Response creatingUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/register");

    }

    @Step("Логин пользователя")

    public Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/login");

    }

    @Step("Изменение пользователя")

    public Response changeUser(User user, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .body(user)
                .patch("/api/auth/user");
    }

    @Step("Удаление пользователя")

    public Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");

    }
}



