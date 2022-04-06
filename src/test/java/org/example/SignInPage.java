package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage {
    public WebDriver driver;

    public SignInPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"ap_email\"]")
    private WebElement emailField;
    @FindBy(xpath = "//*[@id=\"continue\"]")
    private WebElement continueButton;
    @FindBy(xpath = "//*[@id=\"ap_password\"]")
    private WebElement passwordField;
    @FindBy(xpath = "//*[@id=\"authportal-main-section\"]/div[2]/div/div/div/div/span")
    private WebElement emailText;
    @FindBy(xpath = "//*[@id=\"signInSubmit\"]")
    private WebElement signInButton;

    public void inputEmail(String email) {
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"authportal-main-section\"]/div[2]/div/div[1]/form/div/div/div/h1")));
        emailField.sendKeys(email);
    }
    public void pressContinue(){
        continueButton.click();
    }
    public String getEmailText(){
        String email = emailText.getText();
        return email;
    }
    public void inputPassword(String password){
        passwordField.sendKeys(password);
    }
    public void pressSignInButton(){
        signInButton.click();
    }
}

