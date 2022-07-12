package pages.base;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import java.util.List;

import static constants.Constants.Size.*;
import static constants.Constants.Urls.URL_COMPARSION;


/**
 * Класс содержит константы и методы используемые в результатах поиска, вкладка картинки, Google
 */
public class ImagePage extends GoogleHomePage {

    protected final static Logger logger = Logger.getLogger(ImagePage.class);

    public static final By ALL_IMAGE_LIST = By.xpath("//div[@jsname='N9Xkfe'][not(@jscontroller='ewR3bd')]"); // путь к каждой картинке в выдаче

    public final static By HREF = By.xpath("//div[@jsname='N9Xkfe'][not(@jscontroller='ewR3bd')]/a[2]");//путь к каждому элементу содержащему ссылку(является родственником ALL_IMAGE_LIST)


    public ImagePage(WebDriver driver) {
        super(driver);
    }


    /**
     * Этот метод прокручивает раздел картинок вниз до потенциально неинтересных картинок,
     * создает коллекции с разрешением картинок и с ссылками
     * в цикле ищет элемент который соотвествует разрешению картинки ( можно менять в Constants)
     * и который ведет на сайт ivi(образец для сравнения так же задается в Constants).
     * Если находится элемент который отвечает критерию поиска
     * то счетчик совпадений count увеличивается на 1.
     * В случае , если нужное количесвто элементов не было найдено , выбрасывается Assertion.fail()
     */
    public void findThreeImagesAndThreeHrefInSearchForm() {

        int count = 0;                                          // счетчик совпадений

        for (int i = 0; i < 5; i++) {                                                       // цикл прокручивает страницу вниз для загрузки дополнительных картинок
            try {
                Thread.sleep(1000);
                JavascriptExecutor jse = (JavascriptExecutor) driver;                               // используем объект ДжаваСкрипт для скролла страницы вниз
                jse.executeScript("window.scrollBy(0,50000)", "");
            } catch (Exception e) {
                break;                          // если кнопки нет , цикл прекращается
            }

        }

        List<WebElement> allImage = driver.findElements(ALL_IMAGE_LIST);                    //добавление в коллекцию элементов картинок
        List<WebElement> allHref = driver.findElements(HREF);                               // добпавление в коллекцию элементов ссылок
        /**
         * Цикл проходится по коллекциям размеров картинок и ссылок , если в обеих индексах соответсвющих номеру цикла находятся картинка
         * соотвествующая константе и ссылка удовлетворяющая условию поиска , счетчик совпадений увеличивается на 1
         */
        for (int i = 0; i < allImage.size(); i++) {
            int resolution = Integer.parseInt(allImage.get(i).getAttribute("data-ow")) * Integer.parseInt(allImage.get(i).getAttribute("data-oh"));// извлечение размеров высоты и ширины картинки
            if (resolution > BIG_SIZE) {                                     //сравнивание размеров картинки с константой если размер картинки больше константы , то происходит сравнение ссылки этой картинки с константой
                String href = allHref.get(i).getAttribute("href");      // присвоение переменной ссылки
                if (href.equals(URL_COMPARSION)) {                              // сравненеие с константой , если успешно счетчик увеличивается на 1
                    count++;
                    logger.info("есть совпадение");
                }
            }

            if (count > 2) {                                                    // проверка количества совпадений, если совпадений 3 и более происходит остановка цикла
                logger.info(" Найдено 3 совпадения");
                break;

            }

        }


        if (count < 3) {                                                        // если по завершению цикла не найдено 3 совпадений выбрасывается провал теста
            logger.info("Не найдено нужного количества элементов");
            Assertions.fail("Не найдено нужного количества элементов на странице");

        }



    }


}
