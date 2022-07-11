package pages.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static constants.Constants.Urls.URL_IVI_IN_GOOGLE_PLAY;

public class GoogleHomePage {

    public WebDriver driver;

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
    }

    protected final static Logger logger = Logger.getLogger(GoogleHomePage.class);

    public static final By RATE_ELEMENT_HREF = By.xpath("//div[@class='fG8Fp uo4vr']/parent::div/parent::div/div/div/a");// для поиска элемента ссылки (является родственником постоянной RATE_ELEMENT)
    public static final By RATE_ELEMENT = By.xpath("//div[@class='fG8Fp uo4vr']");                                       // для поиска элемента рейтинга в результатах поиска Google
    public static final By NEXT_BUTTON = By.xpath("//span[contains(text(),'Следующая')]/parent::a");                    // для пориска кнопки Следующая для перехода на след страницу результатов поиска
    public static final By RATE_IN_GOOGLE_PLAY = By.xpath("//div[@class='TT9eCd']");                                    // для поиска элемента с рейтингом на страницу Google Play приложения ivi
    public static final By GOOGLE_POLITIC = By.xpath("//div[@class='QS5gu sy4vM'][contains(text(),'Отклонить все')]"); // для поиска кнопки отмены предлагаемой политики Google
    public static final By IVIS_HREF_ON_WIKI_IN_SEARCH_RESULT = By.xpath("//a[@href='https://ru.wikipedia.org/wiki/Ivi.ru']");  // для поиска элементов содержащих ссылки на стать в вики по ivi
//"//a[@href='https://ru.wikipedia.org/wiki/Ivi.ru']/parent::div/parent::div/parent::div/parent::div/parent::div[@class='yuRUbf']"
    /**
     * Переходит на URL
     */
    public void goToUrl(String url) {
        driver.get(url);
    }


    /**
     * проверяет , есть ли элемент на странице
     */
    public WebElement checkElementIsVisible(By element) {
        return driver.findElement(element);

    }

    /**
     * Этот метод вводит в поисковую строку текст и нажимает ввод
     */
    public void textEnterAndClick(WebElement element, String value) {
        element.sendKeys(value);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Кликает по элементу
     */
    public void clickElement(WebElement element) {
        element.click();
    }


    /**
     * Метод убирает окно политики Google
     * Для выполнения тестового сценария 2 необходимо зайти на страницу приложения  ivi в Google Play
     * для нормального подключения требуется использовать ВПН , если на страницу можно зайти без ВПН
     * и окно политики не высвечивается, метод в тесте необходимо отключить(закоментить)
     */
    public void cancelPoliticy() {
        driver.findElement(GOOGLE_POLITIC).click();
    }





    /**
     * Этот метод ищет на первых 5 страницах результатов поиска
     * рейтинг ivi в кратком содержании страницы.
     * Принцип поиска заключается в поиске элементов рейтинга в кратком содержании и посика элементов ссылок.
     * Элемент ссылок находится как родительский от элемента рейтинга( если нет элемента рейтинга, то и не будет найден элемент ссылка)
     * создается 2 коллекции, в коллекции rateElement хранятся найденные элементы ссылок
     * в коллекции rateElementHref хранятся элементы ссылок
     * Создается цикл c проходом по коллекциям,сравниваются пары элементов, внутри  цикла стоит if
     * если ссылка элемента рейтинга совпадает с адресом с Google Play приложения ivi
     * то происходит сравнение рейтинга переданного в параметры метода с рейтингом полученного из элемента краткого содержания страницы
     * далее логируются результаты сравнения
     * цикл завершается, жмется кнопка перехода на след страницу результатов
     */

    public void searchRateInPlayGoogle(String raiting) {
        for (int i = 0; i < 4; i++) {                                               // цикл перелистывания страниц результатаа поиска
            List<WebElement> rateElement = driver.findElements(RATE_ELEMENT);       //ищет элемент рейтинг в результатх поиска
            List<WebElement> rateElementHref = driver.findElements(RATE_ELEMENT_HREF);  //ищет элемент ссылку
            for (int j = 0; j < rateElement.size() - 1; j++) {                          // начинает цикл прохода по коллекции
                if (rateElementHref.get(j).getAttribute("href").equals(URL_IVI_IN_GOOGLE_PLAY)) {       // берется элемент ссылки и извлекается из него ссылка, сравнивается с адресом ivi в Google Play
                    // если есть совпадение, то начинается сравнение рейтингв краткого содержанияи и рейтинга переданного в параметры метода
                    String rate = rateElement.get(j).getAttribute("outerText");   // извлечение значения элемента рейтинга
                    // так как в извлеченном значении присутствует и другая информация, необходимо спарсить значение рейтинга
                    Pattern pattern = Pattern.compile("(.)\\d\\W\\d\\b");    // используем регулярное выражение , что бы извлечь значение
                    Matcher matcher = pattern.matcher(rate);                 // передаем в объект совпадатель
                    matcher.find();                                          // ищем совпадение
                    String matcher1 = matcher.group().replace(",", ".").trim(); // заменяем запятую на точку для возможности в будущем преобразовать в Double  так же обрезаем пробелы в начале и конце
                    if (matcher1.equals(raiting)) {   // производим сравнение и выводим на экран результаты сравнения по элементам с кратким рейтингом которые ведут в  Google Play на страницу ivi
                        logger.info("Рейтинг по " + (rateElement.size() + 1) + " - му " + " элементу на странице " + (i + 1) + " совпадает");
                    } else {
                        logger.info("Рейтинг по " + (rateElement.size() + 1) + " - му " + " элементу на странице " + (i + 1) + "не совпадает");
                    }
                }
            }
            System.out.println(i);
            driver.findElement(NEXT_BUTTON).click();          // нажимаем на кнопку следущая  для повторения цикла на след странице
        }

    }






    /**
     * Этот метод проверяет есть ли в результатах поиска ссылки ведущие на статью d Wiki про ivi
     */
    public void searchIVIsHrefInGoogleSearch() {
        for (int i = 0; i < 4; i++) {                   // в цикле ведем поиск на каждой странице результата
            List<WebElement> rateElementHref = driver.findElements(IVIS_HREF_ON_WIKI_IN_SEARCH_RESULT);                 //если элементов не найдено, то коллекция будет пуста
            logger.info("На странице " + (i + 1) + " найдено " + rateElementHref.size() + " ссылок на статью в Wiki");  // если будут найдены элементы , то выведет на какой странице и сколько найдено
            driver.findElement(NEXT_BUTTON).click();                                                    // нажимает на кнопку след страницы
        }
    }

}
