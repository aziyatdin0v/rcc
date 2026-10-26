package io.student.rcc.test;

import com.codeborne.selenide.Selenide;
import io.student.rcc.config.Config;
import io.student.rcc.jupiter.annotation.User;
import io.student.rcc.model.UserJson;
import io.student.rcc.page.MainPage;
import io.student.rcc.utils.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

    private static final Config CFG = Config.getInstance();

    @Test
    public void mainPageShouldBeDisplayed() {
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .checkMainPage();
    }

    @User
    @Test
    @DisplayName("Успешная авторизация")
    void shouldLoginSuccessfully(UserJson user) {
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickButtonSignIn()
                .checkButtonSubmit()
                .authentication(user.username(), user.password())
                .checkMainPage();
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    void shouldNotLoginWithBadCredentials() {
        String username = DataGenerator.generateLogin();
        String password = DataGenerator.generatePassword();
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickButtonSignIn()
                .incorrectAuthentication(username, password)
                .checkErrorLogin();
    }
}
