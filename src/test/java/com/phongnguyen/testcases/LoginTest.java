package com.phongnguyen.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.phongnguyen.common.BaseTest;
import com.phongnguyen.dataproviders.DataProviderFactory;
import com.phongnguyen.frameworks.keywords.WebUI;
import com.phongnguyen.pages.DashboardPage;
import com.phongnguyen.pages.LoginPage;

public class LoginTest extends BaseTest {
        private LoginPage loginPage;
        private DashboardPage dashboardPage;
        private SoftAssert softAssert;

        @BeforeClass
        public void beforeClass() {
                softAssert = new SoftAssert();
        }

        @AfterClass
        public void afterClass() {
                softAssert.assertAll();
        }

        @BeforeMethod
        public void beforeMethod() {
                loginPage = new LoginPage();
        }

        @Test(priority = 1, dataProvider = "data_login", dataProviderClass = DataProviderFactory.class)
        public void verifyLoginSuccess(Hashtable<String, String> data) {
                // Arrange
                loginPage.openLoginPage();
                loginPage.verifyLoginPageDisplayed();

                // Act
                dashboardPage = loginPage.loginCRM(data.get("EMAIL"), data.get("PASSWORD"));

                // Assert
                Assert.assertTrue(dashboardPage.isMenuDashboardDisplayed(),
                                "Fail. The Dashboard menu is not displayed.");
                Assert.assertEquals(WebUI.getCurrentURL(), DashboardPage.DASHBOARD_URL,
                                "Fail. The Dashboard page URL not match.");
        }

        @Test
        public void verifyLoginFailedwithInvalidEmail() {
                // Arrange
                loginPage.openLoginPage();
                loginPage.verifyLoginPageDisplayed();
                // Action
                // Email đúng định dạng nhưng không tồn tại trong hệ thống
                dashboardPage = loginPage.loginCRM("admin123@example.com", "123456");

                // Assert
                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Fail. The error message is not displayed.");
                Assert.assertEquals(loginPage.getErrorMessageText(), LoginPage.MSG_INVALID_EMAIL_OR_PASSWORD,
                                "Fail. The error message content not match.");
                Assert.assertEquals(WebUI.getCurrentURL(), LoginPage.LOGIN_URL,
                                "Fail. Should stay on the Login page.");

        }

        @Test
        public void verifyLoginFailedwithInvalidPassword() {
                // Arrange
                loginPage.openLoginPage();
                loginPage.verifyLoginPageDisplayed();
                // Action
                dashboardPage = loginPage.loginCRM("admin@example.com", "12345");

                // Assert
                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Fail. The error message is not displayed.");
                Assert.assertEquals(loginPage.getErrorMessageText(), LoginPage.MSG_INVALID_EMAIL_OR_PASSWORD,
                                "Fail. The error message content not match.");
                Assert.assertEquals(WebUI.getCurrentURL(), LoginPage.LOGIN_URL,
                                "Fail. Should stay on the Login page.");
        }

        @Test
        public void verifyLoginFailedwithEmailNull() {
                // Arrange
                loginPage.openLoginPage();
                loginPage.verifyLoginPageDisplayed();

                // Action
                dashboardPage = loginPage.loginCRM("", "12345");
                // Assert
                Assert.assertTrue(loginPage.isEmailRequiredMessageDisplayed(),
                                "Fail. The required email message is not displayed.");
                Assert.assertEquals(WebUI.getCurrentURL(), LoginPage.LOGIN_URL,
                                "Fail. Should stay on the Login page.");
        }

        @Test
        public void verifyLoginFailedwithEmailandPassNull() {
                // Arrange
                loginPage.openLoginPage();
                loginPage.verifyLoginPageDisplayed();

                // Action
                dashboardPage = loginPage.loginCRM("", "");
                // Assert
                Assert.assertTrue(loginPage.isPasswordRequiredMessageDisplayed(),
                                "Fail. The required Email message is not displayed.");
                Assert.assertTrue(loginPage.isPasswordRequiredMessageDisplayed(),
                                "Fail. The required Password message is not displayed.");

                Assert.assertEquals(WebUI.getCurrentURL(), LoginPage.LOGIN_URL,
                                "Fail. Should stay on the Login page.");

        }

        @Test
        public void verifyLoginFailedwithEmailFormatInvalid() {
                // Arrange
                loginPage.openLoginPage();
                loginPage.verifyLoginPageDisplayed();
                // Action
                // Email sai định dạng (thiếu @)
                dashboardPage = loginPage.loginCRM("admin@example", "123456");

                // Assert
                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Fail. The error message is not displayed.");
                Assert.assertEquals(loginPage.getErrorMessageText(), LoginPage.MSG_INVALID_EMAIL_FORMAT,
                                "Fail. The error message content not match.");
                Assert.assertEquals(WebUI.getCurrentURL(), LoginPage.LOGIN_URL,
                                "Fail. Should stay on the Login page.");
        }

}
