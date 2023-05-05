package pens;

import io.qameta.allure.Step;
import serialization.Ingredient;

import static io.restassured.RestAssured.given;

public class IngredientAPI {
    @Step("Получение информации об ингредиентах")
    public Ingredient getIngredient() {
        return given()
                .header("Content-type", "application/json")
                .get("/api/ingredients")
                .as(Ingredient.class);
    }
}
