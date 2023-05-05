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

public class ChangeUserTest {
    BaseURL baseURL = new BaseURL();
    UserAPI userAPI = new UserAPI();
    private String accessToken;
    User user = new User("gkaverin98@yandex.ru", "Gleb", "pelmeshka");
    private String updateEmail = "gkaverin98@mail.ru";
    private String updateName = "Igor";

    @Before
    public void setUp() {
        baseURL.setUp();
        userAPI.creatingUser(user);
        accessToken = userAPI.loginUser(user).then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Change authorized user email")
    @Description("Checking that the authorized user's email can be changed")
    public void changeAuthorizedUserEmail() {
        user.setEmail(updateEmail);
        userAPI.changeUser(user, accessToken).then().assertThat().body("success", equalTo(true)).
                and().statusCode(200);

    }

    @Test
    @DisplayName("Change authorized user name")
    @Description("Checking that the authorized user's name can be changed")
    public void changeAuthorizedUserName() {
        user.setName(updateName);
        userAPI.changeUser(user, accessToken).then().assertThat().body("success", equalTo(true)).
                and().statusCode(200);
    }

    @Test
    @DisplayName("Change not authorized user name")
    @Description("Checking that the authorized user's name can`t be changed")
    public void changeNotAuthorizedUserName() {
        accessToken = "";
        user.setName(updateName);
        userAPI.changeUser(user, accessToken).then().assertThat().body("success", equalTo(false)).
                and().statusCode(401);
    }

    @Test
    @DisplayName("Change authorized user email")
    @Description("Checking that the authorized user's email can be changed")
    public void changeNotAuthorizedUserEmail() {
        accessToken = "";
        user.setEmail(updateEmail);
        userAPI.changeUser(user, accessToken).then().assertThat().body("success", equalTo(false)).
                and().statusCode(401);

    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userAPI.deleteUser(accessToken);
        }
    }
}
