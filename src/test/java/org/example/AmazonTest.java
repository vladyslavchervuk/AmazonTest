//1. Перейти на  https://www.amazon.com/
//2. Встановити фільтр Book
//3. Ввести пошукове слово Java
//4. Зберегти інформацію з першої сторінки: Назва книги, автор, ціна чи є бестселлером в ліст
//5. Переконатись, що в ліст є книга  https://www.amazon.com/Head-First-Java-Kathy-Sierra/dp/0596009208/ref=sr_1_2?dchild=1&keywords=Java&qid=1610356790&s=books&sr=1-2
package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmazonTest {
    public static WebDriver driver;
    public static HomePage homePage;
    public static SignInPage signInPage;
    public static SearchPage searchPage;

    @BeforeClass
    public static void setup() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        signInPage = new SignInPage(driver);
        searchPage = new SearchPage(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("loginpage"));
    }

    @Test
    public void goToSignInPage() {
        //логинимся
        homePage.clickSignIn();
        //вводим эмейл
        signInPage.inputEmail(ConfProperties.getProperty("email"));
        //жмем дальше
        signInPage.pressContinue();
        //вводим пароль
        signInPage.inputPassword(ConfProperties.getProperty("password"));
        //жмем сайн ин баттон
        signInPage.pressSignInButton();
        //выбираем фильтр
        homePage.clickBooksFilter();
        //вводим поисковое слово
        searchPage.inputSearchText(ConfProperties.getProperty("searchText"));
        //жмем поиск
        searchPage.pressSearchButton();
        //считаем и выводим на экран количество результатов поиска
        searchPage.countItems();
        //добавляем в лист тайтлы книг
        searchPage.countNames();
        //добавляем в лист цены книг
        searchPage.countPrices();
        //добавляем в лист цену после точки
        searchPage.countPricesFraction();
        //определяем бест селлер ли
        searchPage.getBest();
        //добавляем в лист авторов
        searchPage.countAuthors();
        //добавляем из листа в объект все данные и выводим на экран
        searchPage.getBooks();
        //ищем есть ли в списке нужная нам книга
        searchPage.getBookName();
        String bk = searchPage.getBookName();
        Assert.assertEquals(ConfProperties.getProperty("bookName"), bk);
        System.out.println("BOOK " + "\"" + bk + "\" IS IN THE LIST!!!");
    }

//    @AfterClass
//    public static void tearDown(){
//        driver.quit();
//    }
}

