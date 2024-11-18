package org.gpo.pages;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class CDashboardAddPage {
    private Page page;
    private Locator createButton;
    private Locator successMessage;
    private Locator natid;
    private Locator fname;
    private Locator gender;
    private Locator bdate;
    private Locator bpoints;
    private Locator xsalary;
    private Locator taxpaid;

    public CDashboardAddPage(Page page) {
        this.page = page;
        this.createButton = page.locator("//button[text()='Create']");
        this.successMessage = page.locator("//div[@id='notification-block']//div//h3");
        this.natid = page.locator("id=natid-in");
        this.fname = page.locator("id=name-in");
        this.gender = page.locator("id=gender-radio-1");
        this.bdate = page.locator("id=birth-date");
        this.bpoints = page.locator("id=brownie-pts-in");
        this.xsalary = page.locator("id=salary-in");
        this.taxpaid = page.locator("id=tax-paid-in");
    }
    public void enterNatId(String natId) {
        natid.fill(natId);
    }

    public void enterName(String name) {
        fname.fill(name);
    }

    public void selectMaleGender() {
        gender.click();
    }

    public void enterDateWithKeyboard(String date) {
        bdate.focus();
        page.keyboard().type(date);
    }

    public void enterBrowniePoints(String browniePoints) {
        bpoints.fill(browniePoints);
    }

    public void enterSalary(String salary) {
        xsalary.fill(salary);
    }

    public void enterTaxPaid(String taxPaid) {
        taxpaid.fill(taxPaid);
    }

    public void clickCreateButton() {
        createButton.click();
    }

    public String getSuccessNotificationMsg() {
        successMessage.waitFor();
        return successMessage.innerText();
    }

}
