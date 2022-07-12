package tests.base;

import common.Clients_Factory;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pages.base.GoogleHomePage;
import pages.base.ImagePage;
import pages.base.ivisGooglePlayMarketPage;
import pages.base.ivisInwikiPage;

import static constants.Constants.IMAGE_BUTTON;
import static constants.Constants.SEARCH_LINE;
import static constants.Constants.Texts.GOOGLE_SEARCH_TEXT;
import static constants.Constants.Urls.*;

public class GoogleHomeTest {

    protected final static Logger logger = Logger.getLogger(GoogleHomeTest.class);

    protected WebDriver driver = Clients_Factory.createDriver();
    protected GoogleHomePage googleHomePage = new GoogleHomePage(driver);
    protected ImagePage imagePage = new ImagePage(driver);
    protected ivisGooglePlayMarketPage ivisGooglePlayMarketPage = new ivisGooglePlayMarketPage(driver);
    protected ivisInwikiPage ivisInwikiPage = new ivisInwikiPage(driver);


    @AfterEach
    void closeDriver() {                // закрытие драйвера после теста
        if (driver != null) {
            driver.quit();
        }
    }


    /**
     * Тестовый сценарий 1
     * неавторизованный пользователь заходит в https://www.google.com/
     * ищет ivi
     * переходит в картинки
     * выбирает большие
     * убеждается, что не менее 3 картинок в выдаче ведут на сайт ivi.ru
     */
    @Test
    public void oneCase() {
        logger.info("Старт певрого сценария");
        googleHomePage.goToUrl(GOOGLE_HOME_PAGE);                                                               // переход на Google
        googleHomePage.textEnterAndClick(googleHomePage.checkElementIsVisible(SEARCH_LINE), GOOGLE_SEARCH_TEXT); // ввод запроса и ввод
        googleHomePage.clickElement(googleHomePage.checkElementIsVisible(IMAGE_BUTTON));                        // клик по элементу картинки
        imagePage.findThreeImagesAndThreeHrefInSearchForm();                                                    // анализ картинок
    }

    /**
     * неавторизованный пользователь заходит в https://www.google.com/
     * ищет ivi
     * на первых 5 страницах находит ссылки на приложение ivi в play.google.com
     * убеждается, что рейтинг приложения на кратком контенте страницы совпадает с рейтингом при переходе
     */
    @Test
    public void twoCase() {                                                                 //ВНИМАНИЕ: запускать с включенным ВПН
        logger.info("Старт второго сценария");
        String originalRaiting = ivisGooglePlayMarketPage.originalRaiting();                                               //переход на страницу приложения ivi в Google
        googleHomePage.goToUrl(GOOGLE_HOME_PAGE);                                                                   // переход на страницу Google
        googleHomePage.cancelPoliticy();                                                                            //  (если нет окна политики строка пропускается методом)
        googleHomePage.textEnterAndClick(googleHomePage.checkElementIsVisible(SEARCH_LINE), GOOGLE_SEARCH_TEXT);    // ввод запроса и ввод
        googleHomePage.searchRateInPlayGoogle(originalRaiting);                                                     // просматривает первые 5 страницй поиска и ищет ссылки на приложение в ivi , сравнивает
    }


    /**
     * неавторизованный пользователь заходит в https://www.google.com/
     * ищет ivi
     * на первых 5 страницах находит ссылку на статью в wikipedia об ivi
     * убеждается, что в статье есть ссылка на официальный сайт ivi.ru
     */
    @Test
    public void threeCase() {
        logger.info("Старт третьего сценария");
        googleHomePage.goToUrl(GOOGLE_HOME_PAGE);                                                               //переход на страницу Google
        googleHomePage.textEnterAndClick(googleHomePage.checkElementIsVisible(SEARCH_LINE), GOOGLE_SEARCH_TEXT); // ввод запроса и ввод
        googleHomePage.searchIVIsHrefInGoogleSearch();                                                          // поиск ссылок на сатью в вики про ivi
        googleHomePage.goToUrl(URL_IVI_IN_WIKI);                                                                // переход на статью в ivi из константы
        ivisInwikiPage.isThereALinkOnIvi();                                                                     // проверка на наличие ссылки на сайт ivi
    }


}
