package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    //  конструктор класса, занимающийся инициализацией полей класса
    public WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"nav-link-accountList\"]")
    private WebElement signInButton;
    @FindBy(xpath = "//a[normalize-space()='Books']")
    private WebElement booksFilter;


    public void clickSignIn() {
        signInButton.click();
    }
    public void clickBooksFilter(){
        booksFilter.click();
    }
}