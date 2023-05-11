package usertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.UserAPI;
import serialization.User;
import url.BaseURL;

import static org.hamcrest.CoreMatchers.equalTo;


public class CreatingAUserTest {
    BaseURL baseURL = new BaseURL();
    UserAPI userAPI = new UserAPI();
    private String accessToken;
    User user = new User("gkaverin98@yandex.ru", "Gleb", "pelmeshka");

    @Before
    public void setUp() {
        baseURL.setUp();
    }

    @Test
    @DisplayName("Creating a user")
    @Description("Checking if a user can be created and that the request returns the correct response code and correct response body")
    public void creatingUser() {
        userAPI.creatingUser(user).then().assertThat().body("success", equalTo(true)).
                and().statusCode(200);
    }

    @Test
    @DisplayName("Creating a user twice")
    @Description("Checking that two identical user cannot be created")
    public void creatingAUserTwice() {
        userAPI.creatingUser(user);
        userAPI.creatingUser(user).then().assertThat().body("success", equalTo(false)).
                and().statusCode(403);
    }

    @Test
    @DisplayName("Create a user without a password")
    @Description("Trying to create a user without a password")
    public void creatingAUserWithoutAPassword() {
        user.setPassword("");
        userAPI.creatingUser(user).then().assertThat().body("success", equalTo(false)).
                and().statusCode(403);
    }

    @Test
    @DisplayName("Create  user without a email")
    @Description("Trying to create  user without a email")
    public void creatingAUserWithoutAEmail() {
        user.setEmail("");
        userAPI.creatingUser(user).then().assertThat().body("success", equalTo(false)).
                and().statusCode(403);
    }

    @Test
    @DisplayName("Creating an unnamed user")
    @Description("Trying to create  user without name")
    public void creatingAnUnnamedUser() {
        user.setName("");
        userAPI.creatingUser(user).then().assertThat().body("success", equalTo(false)).
                and().statusCode(403);
    }

    @After
    public void deleteUser() {
        accessToken = userAPI.loginUser(user).then().extract().path("accessToken");
        if (accessToken != null) {
            userAPI.deleteUser(accessToken);
        }

    }
}
