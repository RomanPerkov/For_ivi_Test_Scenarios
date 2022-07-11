package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

import static common.Config.BROWSER_AND_PLATFORM;
import static constants.Constants.TimeOutVariables.IMPLICIT_WAIT;

public class Clients_Factory {


    /**
     * Фабрика вебдрайверов
     * позволяет создать необходимый вебдрйвер
     */
    public static WebDriver createDriver() {
        WebDriver driver = null;
        switch (BROWSER_AND_PLATFORM) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "MOZILLA":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "SAFARI":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                default:
                Assertions.fail("INCORECT BROWSER NAME " + BROWSER_AND_PLATFORM);
        }
        driver.manage().window().maximize();    // разворачивание окна браузера на весь экран
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));   // для новосозданного вебдрайвера устанавливаем время ожидания
        return driver;
    }

}
