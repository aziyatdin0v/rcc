package io.student.rcc.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;

public class BaseTest {

    @AfterEach
    void clearSession() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Selenide.clearBrowserCookies();
            Selenide.clearBrowserLocalStorage();
        }
    }
}
