package com.phongnguyen.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.phongnguyen.frameworks.constants.ConfigData;
import com.phongnguyen.frameworks.keywords.WebUI;

/**
 * Page Object cho trang Login.
 * Chỉ chứa locator, hành động và hàm lấy dữ liệu (getter) - KHÔNG assert.
 * Việc kiểm tra đúng/sai do class Test đảm nhận.
 */
public class LoginPage extends BasePage {

    // Dữ liệu mong đợi của trang, test dùng để so sánh
    public static final String LOGIN_URL = ConfigData.LOGIN_URL;
    public static final String LOGIN_PAGE_TITLE = "Perfex CRM | Anh Tester Demo - Login";
    public static final String LOGIN_PAGE_HEADER_TEXT = "Login";
    public static final String MSG_INVALID_EMAIL_OR_PASSWORD = "Invalid email or password";
    public static final String MSG_INVALID_EMAIL_FORMAT = "The Email Address field must contain a valid email address.";

    // Khai báo các element dạng đối tượng By (phương thức tìm kiếm)
    private By headerPage = By.xpath("//h1[normalize-space()='Login']");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    private By errorMessage = By.xpath("//div[contains(@class,'alert-danger')]");
    private By alertEmailRequiredMessage = By.xpath("//div[normalize-space()='The Email Address field is required.']");
    private By alertPasswordRequiredMessage = By.xpath("//div[normalize-space()='The Password field is required.']");

    // ===== Action =====

    public void openLoginPage() {
        WebUI.openURL(LOGIN_URL);
        WebUI.waitForPageLoaded();
    }

    public DashboardPage loginCRM(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
        return new DashboardPage();
    }

    private void setEmail(String email) {
        WebUI.setText(inputEmail, email);
    }

    private void setPassword(String password) {
        WebUI.setText(inputPassword, password);
    }

    private void clickLoginButton() {
        WebUI.clickElement(buttonLogin);
    }

    // ===== getter data for test assert =====

    public String getLoginPageTitle() {
        return WebUI.getPageTitle();
    }

    public String getHeaderLoginPage() {
        return WebUI.getElementText(headerPage);
    }

    public boolean isErrorMessageDisplayed() {
        return WebUI.isElementVisible(errorMessage);
    }

    public String getErrorMessageText() {
        return WebUI.getElementText(errorMessage).trim();
    }

    public boolean isEmailRequiredMessageDisplayed() {
        return WebUI.isElementVisible(alertEmailRequiredMessage);
    }

    public boolean isPasswordRequiredMessageDisplayed() {
        return WebUI.isElementVisible(alertPasswordRequiredMessage);
    }

    // ===== Verify page (precondition guard) =====
    public void verifyLoginPageDisplayed() {
        Assert.assertEquals(WebUI.getCurrentURL(), LOGIN_URL,
                "Fail. The Login page URL not match.");
        Assert.assertEquals(getHeaderLoginPage(), LOGIN_PAGE_HEADER_TEXT,
                "Fail. The Login page header not match.");
        Assert.assertTrue(WebUI.isElementVisible(inputEmail)
                && WebUI.isElementVisible(inputPassword)
                && WebUI.isElementVisible(buttonLogin),
                "Fail. The Login form is not displayed.");
    }
}
