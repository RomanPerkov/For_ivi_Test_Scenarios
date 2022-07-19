package pages.base;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Класс содержит константы и методы исаользуесмые на старнице статьи в Wiki про ivi
 */
public class ivisInwikiPage extends SuperPage {

    public ivisInwikiPage(WebDriver driver) {
        super(driver);
    }

    public static final By IVI_HREF_ON_WIKI = By.xpath("//*[@href='https://www.ivi.ru/']"); // путь к элементу содержащему ссылку на ivi

    /**
     * Метод проверяет есть ли в статье про ivi ссылка на оф сайт ivi
     */
    public boolean isThereALinkOnIvi() {
        if (driver.findElements(IVI_HREF_ON_WIKI).size() > 0) {              // поиск ссылки, если коллекция не пуста, значит на сайте присутствует ссылка на ivi
            logger.info("Ссылка на официальный сайт ivi присутствует");
            return true;
        } else {
            logger.info("Ссылка не найдена");
            Assertions.fail();
            return false;

        }

    }


}
