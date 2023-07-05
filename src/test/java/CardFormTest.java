import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;



public class CardFormTest {
    private String date(int days, String pattern){
       return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void fillCardForm () {
        open("http://localhost:9999/");
        String date = date(7, "dd.MM.yyyy");
        $("[class='input__control']").setValue("Нижний Новгород");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("Галанин Евгений");
        $("[data-test-id='phone'] [class='input__control']").setValue("+79087892345");
        $("[data-test-id='agreement'] ").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));
    }
}
