package org.gpo.pages;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import java.nio.file.Paths;

public class CDashboardPage {
    private Page page;
    private Locator headerMessage;
    private Locator addHeroButton;
    private Locator addOption;
    private Locator uploadCsvFileOption;
    private Locator uploadCsvFile;
    private Locator createButton;
    private Locator successMessage;
    public CDashboardPage(Page page) {
        this.page = page;
        this.headerMessage = page.locator("//h1[text()='Clerk Dashboard']");
        this.addHeroButton = page.locator("#dropdownMenuButton2");
        this.addOption = page.locator("//div[@class='dropdown']//ul//li[1]//a");
        this.uploadCsvFileOption = page.locator("//div[@class='dropdown']//ul//li[2]//a");
        this.uploadCsvFile = page.locator("#upload-csv-file");
        this.createButton = page.locator("//button[text()='Create']");
        this.successMessage = page.locator("//div[@id='notification-block']//div//h3");
    }

    public String getClerkHeaderMessage() {
        headerMessage.waitFor();
        return headerMessage.innerText();
    }

    public boolean isAddHeroButtonExists() {
        addHeroButton.waitFor();
        return addHeroButton.isVisible();
    }

    public void clickAddHeroButton() {
        addHeroButton.click();
    }

    public void clickAddOptionFromDropdown() {
        addOption.click();
    }

    public void clickUploadCsvFileFromDropdown() {
        uploadCsvFileOption.click();
    }

    public void uploadWorkingCsvFile() {
        uploadCsvFile.setInputFiles(Paths.get(System.getProperty("user.dir") + "/src/test/resources/dataProviders/taxpayers_data.csv"));
    }

    public void uploadErroneousCsvFile() {
        uploadCsvFile.setInputFiles(Paths.get(System.getProperty("user.dir") + "/src/test/resources/dataProviders/taxpayers_error.csv"));
    }

    public void uploadEmptyCsvFile() {
        uploadCsvFile.setInputFiles(Paths.get(System.getProperty("user.dir") + "/src/test/resources/dataProviders/taxpayers_empty.csv"));
    }

    public void uploadHeaderCsvFile() {
        uploadCsvFile.setInputFiles(Paths.get(System.getProperty("user.dir") + "/src/test/resources/dataProviders/taxpayers_header.csv"));
    }

    public void uploadTXTFile() {
        uploadCsvFile.setInputFiles(Paths.get(System.getProperty("user.dir") + "/src/test/resources/dataProviders/taxpayers.txt"));
    }

    public void clickCreateButton() {
        createButton.click();
    }

    public String getSuccessNotificationMsg() {
        successMessage.waitFor();
        return successMessage.innerText();
    }
}
