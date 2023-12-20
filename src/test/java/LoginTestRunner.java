import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestRunner extends Setup {;
    LoginPage loginPage;
    @Test(priority = 1)
    public void doLoginWithWrongCreds(){
        loginPage=new LoginPage(driver);
        loginPage.doLogin("admin","wrongpass");
        String textActual= driver.findElement(By.className("oxd-alert-content-text")).getText();
        String textExpected="Invalid credentials";
        Assert.assertTrue(textActual.contains(textExpected));
    }
    @Test(priority = 2)
    public void doLoginWithValidCreds(){
        loginPage=new LoginPage(driver);
        loginPage.doLogin("admin","admin123");
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        boolean isImageExists= driver.findElement(By.className("oxd-userdropdown-img")).isDisplayed();
        Assert.assertTrue(isImageExists);
    }
    @Test (priority = 3)
    public void doLogout(){
        loginPage=new LoginPage(driver);
        loginPage.doLogout();
        String loginHeaderTitleActual= driver.findElement(By.className("orangehrm-login-title")).getText();
        String loginHeaderExpected="Login";
        Assert.assertEquals(loginHeaderTitleActual,loginHeaderExpected);
    }
}
