package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

import static common.Config.BROWSER_AND_PLATFORM;
import static constants.Constants.TimeOutVariables.IMPLICIT_WAIT;

public class Clients_Factory {

    protected final static Logger logger = Logger.getLogger(Clients_Factory.class);


    /**
     * Фабрика вебдрайверов
     * позволяет создать необходимый вебдрйвер
     */
    public static WebDriver createDriver() {
        WebDriver driver = null;
        switch (BROWSER_AND_PLATFORM) {
            case "CHROME":
                logger.info("Создаю ChromeDriver");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "MOZILLA":
                logger.info("Создаю firefoxdriver");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "OPERA":
                logger.info("Создаю OperaDriver");
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                default:
                Assertions.fail("INCORECT BROWSER NAME " + BROWSER_AND_PLATFORM);
        }
        driver.manage().window().maximize();    // разворачивание окна браузера на весь экран
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));   // для новосозданного вебдрайвера устанавливаем время ожидания(устанавливается в Contants)
        return driver;
    }

}
