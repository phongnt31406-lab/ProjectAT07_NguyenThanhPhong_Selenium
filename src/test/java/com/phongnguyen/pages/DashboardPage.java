package com.phongnguyen.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.phongnguyen.frameworks.constants.ConfigData;
import com.phongnguyen.frameworks.keywords.WebUI;

/**
 * Page Object cho trang Dashboard.
 * Chỉ chứa locator, hành động và getter - KHÔNG assert.
 */
public class DashboardPage extends BasePage {

    // Dữ liệu mong đợi của trang, test dùng để so sánh
    public static final String DASHBOARD_URL = ConfigData.BASE_URL + "/admin/";

    // Khai báo các element dạng đối tượng By (phương thức tìm kiếm)
    private By menuDashBoard = By.xpath("//li[contains(@class,'menu-item-dashboard')]");

    

    // ===== Lấy dữ liệu (getter) cho test assert =====

    /** Chờ tối đa theo timeout mặc định, trả về true nếu menu Dashboard hiển thị. */
    public boolean isMenuDashboardDisplayed() {
        return WebUI.isElementVisible(menuDashBoard);
    }
}
