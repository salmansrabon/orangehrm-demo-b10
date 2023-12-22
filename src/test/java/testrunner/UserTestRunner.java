package testrunner;

import config.Setup;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Utils;

import java.io.IOException;

public class UserTestRunner extends Setup {
    LoginPage loginPage;
    @Test(priority = 2, description = "New user can login successfully by valid creds")
    public void doLoginByUser() throws IOException, ParseException {
        loginPage=new LoginPage(driver);
        String username= Utils.getUser().get("username").toString();
        String password= Utils.getUser().get("password").toString();
        loginPage.doLogin(username,password);
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        boolean isImageExists= driver.findElement(By.className("oxd-userdropdown-img")).isDisplayed();
        Assert.assertTrue(isImageExists);
    }
}
