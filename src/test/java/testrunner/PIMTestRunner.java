package testrunner;

import com.github.javafaker.Faker;
import config.EmployeeModel;
import config.Setup;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PIMPage;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;

public class PIMTestRunner extends Setup {
    @BeforeTest
    public void doLogin(){
        LoginPage loginPage=new LoginPage(driver);
        loginPage.doLogin("admin","admin123");
    }

    @Test(priority = 1)
    public void createUser() throws InterruptedException, IOException, ParseException {
        PIMPage pimPage=new PIMPage(driver);
        Faker faker=new Faker();
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String username=faker.name().username();
        String password="P@ssword123";
        pimPage.createUser(firstName,lastName,username,password);
        WebElement headerElement=driver.findElement(By.xpath("//h6[text()=\"Personal Details\"]"));
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOf(headerElement));

        String textActual= headerElement.getText();
        String textExpected="Personal Details";
        Assert.assertEquals(textActual,textExpected);

        EmployeeModel employeeModel=new EmployeeModel();
        employeeModel.setFirstName(firstName);
        employeeModel.setLastName(lastName);
        employeeModel.setUsername(username);
        employeeModel.setPassword(password);
        Utils.saveUsers(employeeModel);
    }
}
