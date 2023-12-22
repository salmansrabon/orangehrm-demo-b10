package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;

public class LoginTestRunner extends Setup {
    ;
    LoginPage loginPage;

    @Test(priority = 1, enabled = false)
    public void doLoginWithWrongCreds() {
        loginPage = new LoginPage(driver);
        loginPage.doLogin("admin", "wrongpass");
        String textActual = driver.findElement(By.className("oxd-alert-content-text")).getText();
        String textExpected = "Invalid credentials";
        Assert.assertTrue(textActual.contains(textExpected));
    }

    @Test(priority = 2)
    public void doLoginWithValidCreds() throws IOException, ParseException {
        loginPage = new LoginPage(driver);
        String fileLocation="./src/test/resources/employees.json";
        JSONParser parser=new JSONParser();
        JSONArray empArray= (JSONArray) parser.parse(new FileReader(fileLocation));
        JSONObject adminCredObj= (JSONObject) empArray.get(0);
        if (System.getProperty("username") != null && System.getProperty("password") != null) {
            loginPage.doLogin(System.getProperty("username"), System.getProperty("password"));
        } else {
            loginPage.doLogin(adminCredObj.get("username").toString(), adminCredObj.get("password").toString());
        }


        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        boolean isImageExists = driver.findElement(By.className("oxd-userdropdown-img")).isDisplayed();
        Assert.assertTrue(isImageExists);
    }

    @Test(priority = 3)
    public void doLogout() {
        loginPage = new LoginPage(driver);
        loginPage.doLogout();
        String loginHeaderTitleActual = driver.findElement(By.className("orangehrm-login-title")).getText();
        String loginHeaderExpected = "Login";
        Assert.assertEquals(loginHeaderTitleActual, loginHeaderExpected);
    }

}
