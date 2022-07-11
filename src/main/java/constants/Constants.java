package constants;

import org.openqa.selenium.By;

/**
 * КЛасс констант
 * тут можно устанавливать значения переменных используемых в коде
 */
public class Constants {





    public static final By SEARCH_LINE = By.xpath("//input[@class='gLFyf gsfi']"); // путь поисковой строки на главной странице гугл
    public static final By IMAGE_BUTTON = By.xpath("//div[@class='hdtb-mitem'][3]/a"); // путь кнопки картинки под поисковой строкой после поиска в гугл

    /**
     * В этом классе хранятся переменные ожиданий
     */
    public static class TimeOutVariables {
        public static final int IMPLICIT_WAIT = 10;  //Время в секундах для неявного ожидания (используется для ожидания появления эдемента на странице)
    }


    /**
     * В этом классе хранятся URL адреса
     */
    public static class Urls {
        public static final String GOOGLE_HOME_PAGE = "https://www.google.com/";  //адрес домашней страницы Google
        public static final String URL_COMPARSION = "https://www.ivi.ru/";  // адрес ivi
        public static final String URL_IVI_IN_GOOGLE_PLAY = "https://play.google.com/store/apps/details?id=ru.ivi.client&hl=ru&gl=US";  // адрес ivi приложения в плей маркете
        public static final String URL_IVI_IN_WIKI = "https://ru.wikipedia.org/wiki/Ivi.ru";
    }


    /**
     * В этом классе хранятся тексты вводимые в поля
     */
    public static class Texts {
        public static final String GOOGLE_SEARCH_TEXT = "ivi";//текст вводимый в поисковую строку Google



    }


    /**
     * в этом классе хранятся размеры картинок
     */
    public static class Size {
                public static final int BIG_SIZE = 720*480; // значение с которой происходит сравнение картинок , что бы считать картинку большой или нет
    }







}
