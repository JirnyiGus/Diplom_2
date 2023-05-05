package pens;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.Order;

import static io.restassured.RestAssured.given;


public class OrderAPI {
    @Step("Создание заказа авторизованным пользователем")
    public Response createOrderAuthUser(Order order, String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .body(order)
                .when()
                .post("/api/orders");
    }

    @Step("Создание заказа неавторизованным пользователем")
    public Response createOrderNotAuthUser(Order order){
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/orders");
    }

    @Step("Получение заказов авторизованного пользователя")
    public Response getOrdersAuthUser(String accessToken){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .get("/api/orders");
    }

    @Step("Получение заказов неавторизованного пользователя")
    public Response getOrdersNotAuthUser(){
        return given()
                .header("Content-type", "application/json")
                .get("/api/orders");
    }
}
