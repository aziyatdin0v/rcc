package io.student.rcc.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement
            usernameInput = $("input[name='username']"),
            passwordInput = $("input[name='password']"),
            submitButton = $("button[class='form__submit']"),
            registerButton = $("a[href='/register']"),
            errorLogin = $("p[class='form__error login__error']");


    public MainPage authentication(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickSubmitButton();
        return new MainPage();
    }

    public LoginPage incorrectAuthentication(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickSubmitButton();
        return this;
    }

    public void inputUsername(String username) {
        usernameInput.setValue(username);
    }

    public void inputPassword(String pass) {
        passwordInput.setValue(pass);
    }

    public void clickSubmitButton() {
        submitButton.click();
        new MainPage();
    }

    public LoginPage checkButtonSubmit() {
        submitButton.shouldBe(visible);
        return this;
    }

    public void checkErrorLogin() {
        errorLogin.shouldBe(visible)
                .shouldHave(text("Неверные учетные данные пользователя"));
    }

    public RegisterPage clickButtonRegister() {
        registerButton.click();
        return new RegisterPage();
    }

}
