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

public class CreateOrderTest {
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
    public void setUp(){
        baseURL.setUp();
        ingredientList = ingredientAPI.getIngredient();
        ingredients = new ArrayList<>();
        ingredients.add(ingredientList.getData().get(1).get_id());
        ingredients.add(ingredientList.getData().get(2).get_id());
        ingredients.add(ingredientList.getData().get(3).get_id());
        userAPI.creatingUser(user);
        accessToken = userAPI.loginUser(user).then().extract().path("accessToken");
        order = new Order(ingredients);
    }

    @Test
    @DisplayName("Checking the creation of an order by an authorized user")
    public void checkCreateOrderAuthUser(){
        orderAPI.createOrderAuthUser(order,accessToken)
                .then().assertThat().body("success", equalTo(true)).
                and().statusCode(200);
    }

    @Test
    @DisplayName("Check Creation of an order by an unauthorized user")
    public void checkCreateOrderNotAuthUser(){
        orderAPI.createOrderNotAuthUser(order)
                .then().assertThat().body("success", equalTo(true)).
                and().statusCode(200);
    }

    @Test
    @DisplayName("Verification Creation of an order by an authorized user without ingredients")
    public void checkCreateOrderNoIngredients(){
        ingredients.clear();
        order = new Order(ingredients);
        orderAPI.createOrderAuthUser(order,accessToken)
                .then().assertThat().body("success", equalTo(false)).
                and().statusCode(400);
    }

    @Test
    @DisplayName("Check Create an order with an invalid ingredient hash")
    public void checkCreateOrderWithWrongHash(){
        ingredients.clear();
        ingredients.add("wrong");
        order = new Order(ingredients);
        orderAPI.createOrderAuthUser(order,accessToken)
                .then().assertThat()
                .statusCode(500);
    }
    

    @After
    public void deleteUser(){
        if (accessToken!=null) {
            userAPI.deleteUser(accessToken);
        }
    }
}
