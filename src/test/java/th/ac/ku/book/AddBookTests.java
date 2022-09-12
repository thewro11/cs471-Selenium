package th.ac.ku.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddBookTests {
    
    @LocalServerPort
    private int port;

    // One time only, for all test cases.
    private static WebDriver driver;
    private static WebDriverWait wait;

    @FindBy(id = "nameInput")
    private WebElement nameField;

    @FindBy(id = "authorInput")
    private WebElement authorField;

    @FindBy(id = "priceInput")
    private WebElement priceField;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    // Do this each time a test case is run.
    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, 1000L);
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/book/add");
        PageFactory.initElements(driver, this);
    }

    @AfterEach
    public void AfterEach() throws InterruptedException {
        Thread.sleep(3000);
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @Test
    void testAddBook() {
        // WebElement nameField = wait.until(webDriver ->
        //                         webDriver.findElement(By.id("nameInput")));
        // WebElement authorField = driver.findElement(By.id("authorInput"));
        // WebElement priceField = driver.findElement(By.id("priceInput"));
        // WebElement submitButton = driver.findElement(By.id("submitButton"));

        nameField.sendKeys("Clean Code");
        authorField.sendKeys("Robert Martin");
        priceField.sendKeys("600");

        submitButton.click();

        WebElement firstTd = wait.until(webDriver ->
                               webDriver.findElement(By.tagName("td")));

        assertEquals("Clean Code", firstTd.getText());

        WebElement name = wait.until(webDriver -> webDriver
        .findElement(By.xpath("//table/tbody/tr[1]/td[1]")));
        WebElement author = driver
        .findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
        WebElement price = driver
        .findElement(By.xpath("//table/tbody/tr[1]/td[3]"));

        assertEquals("Clean Code", name.getText());
        assertEquals("Robert Martin", author.getText());
        assertEquals("600.00", price.getText());
    }
}
