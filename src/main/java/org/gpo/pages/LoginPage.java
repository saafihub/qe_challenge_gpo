package org.gpo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class LoginPage {

    private Page page;

    private Locator username;
    private Locator password;
    private Locator submitButton;
    private Locator headerMessage;
    private Locator errorMessage;

    public LoginPage(Page page) {
        this.page = page;
        this.username = page.locator("#username-in");
        this.password = page.locator("#password-in");
        this.submitButton = page.locator("//input[@type='submit']");
        this.headerMessage = page.locator("//h1[text()='Working Class Hero System']");
        this.errorMessage = page.locator("//div[text()='Unable to log in successfully!']");
    }

    public String getLoginPageHeader() {
        headerMessage.waitFor();
        return headerMessage.innerText();
    }

    public void getUserName(String uname) {
        username.fill(uname);
    }

    public void getPassword(String pwd) {
        password.fill(pwd);
    }

    public void clickOnSubmit() {
        submitButton.click();
    }

    public String getErrorMessage() {
        errorMessage.waitFor();
        return errorMessage.innerText();
    }

    public CDashboardPage doClerkLogin(String uname, String pwd) {
        System.out.println("Login with: " + uname + " and " + pwd);
        username.fill(uname);
        password.fill(pwd);
        submitButton.click();
        return new CDashboardPage(page);
    }

    public BKDashboardPage doBookkeeperLogin(String uname1, String pwd1) {
        System.out.println("Login with: " + uname1 + " and " + pwd1);
        username.fill(uname1);
        password.fill(pwd1);
        submitButton.click();
        return new BKDashboardPage(page);
    }
}
