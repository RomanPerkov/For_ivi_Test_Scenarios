package pages.base;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static constants.Constants.Urls.URL_IVI_IN_GOOGLE_PLAY;


/**
 * Класс содержит константы и методы используемые в домашней стринице Google
 */
public class GoogleHomePage extends SuperPage {


    public GoogleHomePage(WebDriver driver) {
        super(driver);

    }


    public static final By RATE_ELEMENT_HREF = By.xpath("//div[@class='fG8Fp uo4vr']/parent::div/parent::div/div/div/a");// для поиска элемента ссылки (является родственником постоянной RATE_ELEMENT)
    public static final By RATE_ELEMENT = By.xpath("//div[@class='fG8Fp uo4vr']");                                       // для поиска элемента рейтинга в результатах поиска Google
    public static final By NEXT_BUTTON = By.xpath("//span[contains(text(),'Следующая')]/parent::a");                    // для пориска кнопки Следующая для перехода на след страницу результатов поиска

    public static final By GOOGLE_POLITIC = By.xpath("//div[@class='QS5gu sy4vM'][contains(text(),'Отклонить все')]"); // для поиска кнопки отмены предлагаемой политики Google
    public static final By IVIS_HREF_ON_WIKI_IN_SEARCH_RESULT = By.xpath("//a[@href='https://ru.wikipedia.org/wiki/Ivi.ru']");  // для поиска элементов содержащих ссылки на статью в вики по ivi
    public static final By SEARCH_LINE = By.xpath("//input[@class='gLFyf gsfi']"); // путь поисковой строки на главной странице гугл
    public static final By IMAGE_BUTTON = By.xpath("//a[contains(text(),'Картинки')]"); // путь кнопки картинки под поисковой строкой после поиска в гугл


    /**
     * Этот метод вводит в поисковую строку текст и нажимает ввод
     */
    public void textEnterAndClick(WebElement element, String value) {
        logger.info("Ввожу " + value);
        element.sendKeys(value);
        logger.info("Нажимаю ввод ");
        element.sendKeys(Keys.ENTER);
    }


    /**
     * Метод создан для того, что бы убирать окно политики Google
     * Если окно политики не высвечивается , ничего не происходит
     */
    public void cancelPoliticy() {
        try {

            driver.findElement(GOOGLE_POLITIC).click();
            logger.info("Обнаружено окно политики Google, отменяю окно");
        } catch (NoSuchElementException e) {

        }
    }


    /**
     * Этот метод ищет на первых 5 страницах результатов поиска
     * рейтинг ivi в кратком содержании страницы.
     * Принцип поиска заключается в поиске элементов рейтинга в кратком содержании и поиска элементов ссылок.
     * Элемент ссылок находится как родственный от элемента рейтинга( если нет элемента рейтинга, то и не будет найден элемент ссылка)
     * создается 2 коллекции, в коллекции rateElement хранятся найденные элементы ссылок
     * в коллекции rateElementHref хранятся элементы ссылок.
     * Если коллекции пусты, значит на странцие нет элементов рейтингов
     * Если коллекции не пусты, начинается цикл сверки
     * если елемент ведет в Google Play то происходит сравнение элемента рейтинга с значением переданным в параметр метода и логируется сообщение
     * о номере элемента рейтинга сверху, что его рейтинг совпадает с рейтингом переданным в параметр метода.
     * Если елемент не ведет в Google Play то логируется сообщение о том что номер элемента сверху не ведет в Google Play
     * (номер элемента - это порядковый номер элемента рейтинга на странице поиска , т е если на странице результатов поиска было выведено 2 ссылки
     * с рейтингом, то такие элементы нумеруются по порядку как 1 , 2 и т д в зависимости от количества элементов на странице)
     * Если найден элемент рейтинг ведущий в Google Play не совпадает по рейтингу то выбрасывается fail
     */

    public String searchRateInPlayGoogle(String raiting) {

        double finalRate = 0;

        for (int i = 0; i < 5; i++) {                                               // цикл перелистывания страниц результатаа поиска
            logger.info("Захожу на страницу " + (i + 1));
            List<WebElement> rateElement = driver.findElements(RATE_ELEMENT);       //ищет элемент рейтинг в результатх поиска
            List<WebElement> rateElementHref = driver.findElements(RATE_ELEMENT_HREF);  //ищет элемент ссылку

            if (rateElement.size() == 0) {
                System.out.println("На странице " + (i + 1) + " не найдено никаких элементов рейтингов");   // если на странице нет никаких элементов рейтингов
            }

            for (int j = 0; j < rateElement.size(); j++) {                          // начинает цикл прохода по коллекции

                if (rateElementHref.get(j).getAttribute("href").equals(URL_IVI_IN_GOOGLE_PLAY)) {       // берется элемент ссылки и извлекается из него ссылка, сравнивается с адресом ivi в Google Play
                    // если есть совпадение, то начинается сравнение рейтингв краткого содержанияи и рейтинга переданного в параметры метода
                    String rate = rateElement.get(j).getAttribute("outerText");   // извлечение значения элемента рейтинга
                    // так как в извлеченном значении присутствует и другая информация, необходимо спарсить значение рейтинга
                    Pattern pattern = Pattern.compile("(.)\\d\\W\\d\\b");    // используем регулярное выражение , что бы извлечь значение
                    Matcher matcher = pattern.matcher(rate);                 // передаем в объект совпадатель
                    matcher.find();                                          // ищем совпадение
                    String matcher1 = matcher.group().replace(",", ".").trim(); // заменяем запятую на точку для возможности в будущем преобразовать в Double  так же обрезаем пробелы в начале и конце
                    if (matcher1.equals(raiting)) {   // производим сравнение и выводим на экран результаты сравнения по элементам с кратким рейтингом которые ведут в  Google Play на страницу ivi
                        logger.info("Рейтинг по " + (j + 1) + " - му " + " элементу на странице " + (i + 1) + " совпадает");
                        finalRate = Double.parseDouble(matcher1);
                    } else {
                        logger.info("Рейтинг по " + (j + 1) + " - му " + " элементу на странице " + (i + 1) + " не совпадает");
                        Assertions.fail("Рейтинг по " + (j + 1) + " - му " + " элементу на странице " + (i + 1) + " не совпадает");
                    }
                } else {
                    logger.info("На странице " + (i + 1) + " элемент " + (j + 1) + " не ведет в Google Play");
                }
            }

            driver.findElement(NEXT_BUTTON).click();  // нажимаем на кнопку следущая  для повторения цикла на след странице

        }

        return String.valueOf(finalRate);

    }


    /**
     * Этот метод проверяет есть ли в результатах поиска ссылки ведущие на статью d Wiki про ivi
     */
    public int searchIVIsHrefInGoogleSearch() {
        int countHfref = 0;
        for (int i = 0; i < 5; i++) {                   // в цикле ведем поиск на каждой странице результата
            logger.info("Захожу на страницу " + (i + 1));

            List<WebElement> rateElementHref = driver.findElements(IVIS_HREF_ON_WIKI_IN_SEARCH_RESULT);//если элементов не найдено, то коллекция будет пуста
            if (rateElementHref.size() > 0) {
                countHfref++;
                logger.info("На странице " + (i + 1) + " найдено " + rateElementHref.size() + " ссылок на статью в Wiki");  // если будут найдены элементы , то выведет на какой странице и сколько найдено
            } else {
                logger.info("На странице " + (i + 1) + " не найдено  ссылок на статью в Wiki");
            }
            driver.findElement(NEXT_BUTTON).click();                                                    // нажимает на кнопку след страницы
        }
        if (countHfref == 0) {
            Assertions.fail();
        }
        return countHfref;
    }

}

