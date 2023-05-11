package ordertests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.IngredientAPI;
import pens.OrderAPI;
import pens.UserAPI;
import serialization.Ingredient;
import serialization.Order;
import serialization.User;
import url.BaseURL;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrderTest {
    BaseURL baseURL = new BaseURL();
    UserAPI userAPI = new UserAPI();
    OrderAPI orderAPI = new OrderAPI();
    private String accessToken;
    private Ingredient ingredientList;
    private List<String> ingredients;
    private Order order;
    IngredientAPI ingredientAPI = new IngredientAPI();
    User user = new User("gkaverin98@yandex.ru", "Gleb", "pelmeshka");

    @Before
    public void setUp() {
        baseURL.setUp();
        ingredientList = ingredientAPI.getIngredient();
        ingredients = new ArrayList<>();
        ingredients.add(ingredientList.getData().get(1).getId());
        ingredients.add(ingredientList.getData().get(2).getId());
        ingredients.add(ingredientList.getData().get(3).getId());
        userAPI.creatingUser(user);
        accessToken = userAPI.loginUser(user).then().extract().path("accessToken");
        order = new Order(ingredients);
    }

    @Test
    @DisplayName("Check get order auth user")
    public void checkGetOrderAuthUser() {
        orderAPI.getOrdersAuthUser(accessToken)
                .then().assertThat().body("success", equalTo(true)).
                and().statusCode(200);
    }

    @Test
    @DisplayName("Check get order not auth user")
    public void checkGetOrderNotAuthUser() {
        orderAPI.getOrdersNotAuthUser()
                .then().assertThat().body("success", equalTo(false)).
                and().statusCode(401);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userAPI.deleteUser(accessToken);
        }
    }
}
