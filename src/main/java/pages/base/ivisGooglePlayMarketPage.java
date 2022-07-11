package pages.base;

import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.Constants.Urls.URL_IVI_IN_GOOGLE_PLAY;

public class ivisGooglePlayMarketPage extends GoogleHomePage {


    public ivisGooglePlayMarketPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Метод считывает рейтинг с Google Play для последующего сравнению с этим значением
     */
    public String originalRaiting() {
        goToUrl(URL_IVI_IN_GOOGLE_PLAY);                                                            // переход в Google Play может потребоваться ВПН
        String raiting = driver.findElement(RATE_IN_GOOGLE_PLAY).getAttribute("textContent");   // поиск элемента с значением рейтинга и считывание значения
        Pattern pattern1 = Pattern.compile("\\d\\W\\d");                                                // использование регулярного выражения для отрезки ненужных символов
        Matcher matcher = pattern1.matcher(raiting);
        matcher.find();                                                                                 // применения регулярного выражения на ранее считыыанное значения
        String forReturn = matcher.group();                                                             // присвоение "чистого" значения переменной
        return forReturn.replace(",", ".");                                             // возврат значения с заменой запятой на точку для возможности в дальнейшем преобразовать в Double
    }

}






