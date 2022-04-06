package org.example;

import net.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.awt.print.Book;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SearchPage {
    public WebDriver driver;
    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"twotabsearchtextbox\"]")
    private WebElement searchBox;
    @FindBy(xpath = "//*[@id=\"nav-search-submit-button\"]")
    private WebElement searchButton;
    @FindBy(xpath = "//span[contains(@class, 'a-size-medium a-color-base a-text-normal')]")
    private WebElement bookHeader;
    @FindBy(xpath = "//span[contains(@class, 'a-offscreen')]")
    private WebElement bookPrice;

    public void inputSearchText(String searchText) {
        searchBox.sendKeys(searchText);
    }

    public void pressSearchButton() {
        searchButton.click();
    }

    //объявляем аррей лист для всех книг на странице
    List<String> booksPrice = new ArrayList<String>();
    List<String> booksPriceFraction = new ArrayList<String>();
    List<String> booksName = new ArrayList<String>();
    List<Boolean> booksIsBestSeller = new ArrayList<Boolean>();
    List<String> booksAuthor = new ArrayList<String>();

    //считаем все контейнеры результатов поиска на странице
    int count;
    public int countItems() {
        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class, 's-card-container s-overflow-hidden aok-relative s-include-content-margin s-latency-cf-section s-card-border')]"));
        count = elements.size();
        System.out.println(count);
        return count;
    }

    //добавляем имена книг в аррей лист
    public void countNames() {
        for (int i = 0; i < count; i++) {
            List<WebElement> elements = driver.findElements(By.xpath("//span[contains(@class, 'a-size-medium a-color-base a-text-normal')]"));
            for (WebElement element : elements) {
                booksName.add(element.getText());
            }
        }
    }

    //добавляем цены в аррей лист
    public void countPrices() {
        List<WebElement> wholePrice = driver.findElements(By.xpath("//span[contains(@class, 'a-price-whole')]"));
        for (WebElement ep : wholePrice) {
            if (ep.getText() != null || wholePrice.size() != 0) {
                booksPrice.add(ep.getText());
            } else booksPrice.add("0");
        }
    }

    //добавляем цену после точки в аррей лист
    public void countPricesFraction() {
            List<WebElement> fractionPrice = driver.findElements(By.xpath("//span[contains(@class, 'a-price-fraction')]"));
            for (WebElement ep : fractionPrice) {
                if (ep.getText() != null || fractionPrice.size() != 0) {
                    booksPriceFraction.add(ep.getText());
                }
                else booksPriceFraction.add(".00");
            }
        }

    //добавляем авторов в аррей лист (случай если автор в span теге и случай для a тега)
    String authorText = "";
    public void countAuthors(){
        for (int i = 2; i < 20; i++) {
            if (i == 5 || i == 14) {
                i += 1;
            }
            for (int j = 1; j < 10; j++) {
                List<WebElement> PP = driver.findElements(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div["+i+"]/div/div/div/div/div/div[2]/div/div/div[1]/div/div/span["+j+"]"));
                    for (WebElement ep : PP) {
                        if (driver.findElements(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div["+i+"]/div/div/div/div/div/div[2]/div/div/div[1]/div/div/span["+j+"]")).size() != 0) {
                            authorText += ep.getText() + " ";
                        }
                        else {
                            j = 1;
                        }
                    }

                List<WebElement> CC = driver.findElements(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div["+i+"]/div/div/div/div/div/div[2]/div/div/div[1]/div/div/a["+j+"]"));
                for (WebElement ed : CC) {
                    if (driver.findElements(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div["+i+"]/div/div/div/div/div/div[2]/div/div/div[1]/div/div/a["+j+"]")).size() != 0) {
                        authorText += ed.getText() + " ";
                    }
                }
            }
            booksAuthor.add(authorText);
            authorText = "";
        }
    }

    //идентифицируем бестселлеры и проставляем булеан
    public void getBest() {
        for (int i = 2; i < 20; i++) {
            if (i == 5 || i == 14) {i += 1; }
            if (driver.findElements(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div["+i+"]/div/div/div/div/div/div[1]/div/div[1]/div/span/div")).size() != 0) {
                booksIsBestSeller.add(Boolean.TRUE);
            } else booksIsBestSeller.add(Boolean.FALSE);
        }
    }

    //  находим и запоминаем нужную книгу из аррей листа
    public String getBookName() {
        String bookName = booksName.get(0);
        for (int i = 0; i < count; i++) {
            if (booksName.get(i).equals("Head First Java: Edition en anglais")) {
                bookName = booksName.get(i);
                break;
            }
        }
        return bookName;
    }

    //создаем клас книги
    static class BookObject {
        static String bookName;
        static String bookPrice;
        static String bookPriceFraction;
        static String isBestSeller;
        static String bookAuthor;

        //создаем конструктор
        public BookObject(String bookName, String bookPrice, String bookPriceFraction, Boolean isBestSeller, String bookAuthor) {
            BookObject.bookAuthor = bookAuthor;
            BookObject.bookName = bookName;
            BookObject.bookPrice = bookPrice;
            BookObject.bookPriceFraction = bookPriceFraction;
            BookObject.isBestSeller = String.valueOf(isBestSeller);
        }
    }

    //создаем объекты и выводим на экран
    public void getBooks() {
        ArrayList<BookObject> arrayOfBooks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arrayOfBooks.add(new BookObject(booksName.get(i), booksPrice.get(i), booksPriceFraction.get(i), booksIsBestSeller.get(i), booksAuthor.get(i)));
            System.out.println(i + ". Book name: " + "\"" + BookObject.bookName + "\";" + " Book price = £" + BookObject.bookPrice + "." + BookObject.bookPriceFraction + "; Best Seller = " + BookObject.isBestSeller + "; Author: " + BookObject.bookAuthor);
        }
    }
}

