import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


class SampleTest {

    WebDriver driver;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        FirefoxOptions options = new FirefoxOptions().addPreference("browser.startup.homepage", "https://www.ua.pt");
        driver = new FirefoxDriver(options);
    }


    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        driver.close();
    }

    @Test
    public void blazeDemoTest() {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1853, 1053));
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Mexico City']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(6)")).click();
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'Rome']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        MatcherAssert.assertThat(driver.findElement(By.cssSelector("h3")).getText(), is("Flights from Mexico City to Rome:"));
        driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();
        MatcherAssert.assertThat(driver.findElement(By.cssSelector("h2")).getText(), is("Your flight from TLV to SFO has been reserved."));
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("Manuel Montoya");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("123 Mega St.");
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys("Mexico City");
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys("Mexico State");
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("321456");
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("12222222222223");
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys("M Montoya");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        MatcherAssert.assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Thank you for your purchase today!"));
        assertEquals(driver.getTitle(), "BlazeDemo Confirmation");
    }
}