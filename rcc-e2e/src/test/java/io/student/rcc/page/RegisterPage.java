package io.student.rcc.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {

    private final SelenideElement
            userNameInput = $("input[id='username']"),
            passwordInput = $("input[id='password']"),
            passwordSubmitInput = $("input[id='passwordSubmit']"),
            registerButton = $("button[type='submit']"),
            signUpButton = $(".form__submit"),
            successRegistrationTitle = $("p.form__subheader"),
            errorPasswordMessage = $(".form__error.error__password"),
            userAlreadyExistMessage =$(".form__error.error__username");


    public RegisterPage registerNewUser(String username, String password, String confirmPassword) {
        userNameInput.setValue(username);
        passwordInput.setValue(password);
        passwordSubmitInput.setValue(confirmPassword);
        registerButton.shouldBe(visible).click();
        return this;
    }

    public RegisterPage checkSuccessRegistrationTitle(String successRegistrationText) {
        successRegistrationTitle.shouldBe(visible).shouldHave(exactText(successRegistrationText));
        return this;
    }

    public RegisterPage checkErrorPasswordMessage(String errorPasswordText) {
        errorPasswordMessage.shouldBe(visible).shouldHave(text(errorPasswordText));
        return this;
    }

    public RegisterPage checkUserAlreadyExistMessage(String userAlreadyExistText) {
        userAlreadyExistMessage.shouldBe(visible).shouldHave(text(userAlreadyExistText));
        return this;
    }

}
