import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class MainTest {
    private AppiumDriver<WebElement> driver;
    private final String elementNotFoundMessage = "Не найден элемент! ";

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "G:\\github\\MobileAppAutomator\\MobileAppAutomator-Ex06\\apks\\org.wikipedia.apk");

        driver = new AppiumDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testArticleHasTitle() {
        // Открываем первую статью в ленте
        String featuredArticleCardImageId = "org.wikipedia:id/view_featured_article_card_image";
        waitElementAndClick(By.id(featuredArticleCardImageId));

        // Проверяем наличие заголовка статьи без ожидания отрисовки элемента
        String articleTitleId = "org.wikipedia:id/view_page_title_text";
        assertElementPresent(By.id(articleTitleId));
    }

    private void assertElementPresent(By by) {
        try {
            driver.findElement(by);
        } catch (Exception e) {
            Assert.fail(elementNotFoundMessage + by.toString());
        }
    }

    private void waitElementAndClick(By by) {
        final long timeoutInSeconds = 5;
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(elementNotFoundMessage + by.toString());

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.click();
    }
}
