package com.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-gpu");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(new Dimension(1200, 800));
    }

    @Test
    public void testLogin() {
        driver.get("http://localhost:3000/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='请输入用户名']")));
        usernameInput.clear();
        usernameInput.sendKeys("asd");

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='密码']")));
        passwordInput.clear();
        passwordInput.sendKeys("asd");

        String captchaText = getCaptchaText(wait);
        System.out.println("提取到的验证码: " + captchaText);

        WebElement captchaInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='请输入验证码']")));
        captchaInput.clear();
        captchaInput.sendKeys(captchaText);

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit' and contains(@class, 'btn')]")));
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/home"));

        System.out.println("登录测试完成，请输入 'exit' 关闭浏览器，或其他键继续运行...");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine();
        if ("exit".equalsIgnoreCase(input)) {
            driver.quit();
        }
    }

    @Test
    public void testRegister() {
        driver.get("http://localhost:3000/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement switchToRegisterButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'register-btn')]")));
        switchToRegisterButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'form-box') and contains(@class, 'register')]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'register')]//div[contains(@class, 'ValidCode')]")));

        WebElement registerUsernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'register')]//input[@placeholder='请输入用户名']")));
        registerUsernameInput.clear();
        String randomUsername = generateRandomUsername();
        registerUsernameInput.sendKeys(randomUsername);

        WebElement registerPasswordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'register')]//input[@placeholder='密码']")));
        registerPasswordInput.clear();
        String randomPassword = generateRandomPassword();
        registerPasswordInput.sendKeys(randomPassword);

        String captchaText = getCaptchaText(wait, "register");
        System.out.println("提取到的验证码: " + captchaText);

        WebElement captchaInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'register')]//input[@placeholder='请输入验证码']")));
        captchaInput.clear();
        captchaInput.sendKeys(captchaText);

        // 使用 JavaScript 点击注册按钮
        WebElement registerSubmitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'register')]//button[@type='submit' and contains(@class, 'btn')]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", registerSubmitButton);

        wait.until(ExpectedConditions.urlContains("/"));

        System.out.println("注册测试完成，请输入 'exit' 关闭浏览器，或其他键继续运行...");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine();
        if ("exit".equalsIgnoreCase(input)) {
            driver.quit();
        }
    }

    private String getCaptchaText(WebDriverWait wait, String context) {
        String xpath = context.equals("register")
                ? "//div[contains(@class, 'register')]//div[contains(@class, 'ValidCode')]//span"
                : "//div[contains(@class, 'login')]//div[contains(@class, 'ValidCode')]//span";
        List<WebElement> captchaElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
        StringBuilder captchaText = new StringBuilder();
        for (WebElement element : captchaElements) {
            captchaText.append(element.getText());
        }
        return captchaText.toString().toLowerCase();
    }

    private String getCaptchaText(WebDriverWait wait) {
        return getCaptchaText(wait, "login");
    }

    private String generateRandomUsername() {
        return "user" + System.currentTimeMillis();
    }

    private String generateRandomPassword() {
        return "password" + (int)(Math.random() * 1000);
    }

    @AfterEach
    public void tearDown() {
        // 不自动关闭
    }
}