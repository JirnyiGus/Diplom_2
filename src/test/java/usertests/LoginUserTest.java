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

public class LoginUserTest {
    UserAPI userAPI = new UserAPI();
    private String accessToken;
    User user = new User("gkaverin98@yandex.ru", "Gleb", "pelmeshka");
    BaseURL baseURL = new BaseURL();

    @Before
    public void setUp() {
        baseURL.setUp();
        userAPI.creatingUser(user);
        accessToken = userAPI.loginUser(user).then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Authorization user")
    @Description("Checking that the user can login and that the request returns the correct response code and correct response body")
    public void authorizationUser() {
        userAPI.loginUser(user).then().assertThat().body("success", equalTo(true)).
                and().statusCode(200);
    }
    @Test
    @DisplayName("Authorization with incorrect email")
    @Description("Checking that the user cannot login with an invalid email and that the request returns the correct response code and correct response body")
    public void authorizationWithIncorrectEmail() {
        user.setEmail("gkaverin98@.ru");
        userAPI.loginUser(user).then().assertThat().body("success", equalTo(false)).
                and().statusCode(401);
    }
    @Test
    @DisplayName("Authorization with incorrect password")
    @Description("Checking that the user cannot login with an invalid password and that the request returns the correct response code and correct response body")
    public void authorizationWithIncorrectPassword() {
        user.setPassword("pelmen");
        userAPI.loginUser(user).then().assertThat().body("success", equalTo(false)).
                and().statusCode(401);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userAPI.deleteUser(accessToken);
        }
    }
}
