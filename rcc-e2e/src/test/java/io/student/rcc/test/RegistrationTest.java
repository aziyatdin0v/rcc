package io.student.rcc.test;

import com.codeborne.selenide.Selenide;
import io.student.rcc.config.Config;
import io.student.rcc.jupiter.annotation.User;
import io.student.rcc.model.UserJson;
import io.student.rcc.page.MainPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.student.rcc.utils.DataGenerator.generateLogin;
import static io.student.rcc.utils.DataGenerator.generatePassword;


public class RegistrationTest extends BaseTest{

    private static final Config CFG = Config.getInstance();

    @User
    @Test
    @DisplayName("Регистрация нового пользователя")
    public void shouldRegisterNewUser() {
        String userName = generateLogin();
        String password = generatePassword();
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickButtonSignIn()
                .clickButtonRegister()
                .registerNewUser(userName, password, password)
                .checkSuccessRegistrationTitle("Добро пожаловать в Rococo");
    }

    @User
    @Test
    @DisplayName("Нельзя зарегистрировать пользователя с уже занятым username")
    public void shouldNotRegisterUserWithExistingUsername(UserJson user) {
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickButtonSignIn()
                .clickButtonRegister()
                .registerNewUser(user.username(), user.password(), user.password())
                .checkUserAlreadyExistMessage(String.format("Username `%s` already exists", user.username()));
    }

    @Test
    @DisplayName("Пароли не совпадают")
    public void shouldShowErrorWhenPasswordsDoNotMatch() {
        String userName = generateLogin();
        String password = generatePassword();
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickButtonSignIn()
                .clickButtonRegister()
                .registerNewUser(userName, password, password + "123")
                .checkErrorPasswordMessage("Passwords should be equal");
    }

    @Test
    @DisplayName("Пароль не удовлетворяет требованию по длине (короткий)")
    public void shouldShowErrorWhenPasswordIsTooShort() {
        String userName = generateLogin();
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickButtonSignIn()
                .clickButtonRegister()
                .registerNewUser(userName, "ab", "ab")
                .checkErrorPasswordMessage("Allowed password length should be from 3 to 12 characters");
    }


    @Test
    @DisplayName("Пароль не удовлетворяет требованию по длине (длинный)")
    public void shouldShowErrorWhenPasswordIsTooLong() {
        String userName = generateLogin();
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickButtonSignIn()
                .clickButtonRegister()
                .registerNewUser(userName, "abcdefghijklm", "abcdefghijklm")
                .checkErrorPasswordMessage("Allowed password length should be from 3 to 12 characters");
    }
}
