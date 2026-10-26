package io.student.rcc.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    public static final String TITLE = "Ваши любимые картины и художники всегда рядом";

    private final SelenideElement
            loginInput = $(By.xpath("//button[contains(text(), 'Войти')]")),
            pageContent = $("main[id='page-content']"),
            paintingButton = $("[href='/painting']"),
            artistButton = $("a[href='/artist']"),
            museumButton = $("a[href='/museum']");

    public void checkMainPage() {
        pageContent.shouldBe(text(TITLE));
        paintingButton.shouldBe(visible);
        artistButton.shouldBe(visible);
        museumButton.shouldBe(visible);
    }

    public LoginPage clickButtonSignIn() {
        loginInput.click();
        return new LoginPage();
    }

}