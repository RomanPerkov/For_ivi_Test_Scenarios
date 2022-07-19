package pages.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SuperPage {
    protected WebDriver driver;

    public SuperPage(WebDriver driver) {
        this.driver = driver;
    }

    protected final static Logger logger = Logger.getLogger(SuperPage.class);

    public static final By RATE_IN_GOOGLE_PLAY = By.xpath("//div[@class='TT9eCd']");        // для поиска элемента с рейтингом на страницу Google Play приложения ivi








    /**
     * Переходит на URL
     */
    public void goToUrl(String url) {
        logger.info("Перехожу по " + url);
        driver.get(url);
    }


    /**
     * Кликает по элементу
     */
    public void clickElement(WebElement element) {
        element.click();
        logger.info("Кликаю по элементу " + element);
    }


    /**
     * проверяет , есть ли элемент на странице
     */
    public WebElement checkElementIsVisible(By element) {
        return driver.findElement(element);

    }
}
