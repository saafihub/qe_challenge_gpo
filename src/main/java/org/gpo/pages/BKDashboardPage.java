package org.gpo.pages;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.nio.file.*;

public class BKDashboardPage {

    private final Locator bookKprHeaderMsg;
    private final Locator generateTaxFileButton;

    private Page page;

    public BKDashboardPage(Page page) {
        this.page = page;
        this.bookKprHeaderMsg = page.locator("//h1[text()='Book Keeper Dashboard']");
        this.generateTaxFileButton = page.locator("#tax_relief_btn");
    }

    public String getBookKeeperHeaderMsg() {
        bookKprHeaderMsg.waitFor();
        return bookKprHeaderMsg.innerText();
    }

    public void clickOnTaxReliefFileButton() {
        generateTaxFileButton.click();
    }

    public boolean taxReliefFileButtonVisibility() {
        generateTaxFileButton.waitFor();
        return generateTaxFileButton.isEnabled();
    }

    public void taxReliefFileButtonState() {
        try {
            generateTaxFileButton.waitFor(new Locator.WaitForOptions()
                    .setTimeout(5000).setState(WaitForSelectorState.ATTACHED));
            System.out.println("Tax relief file button is clickable");
        } catch (Exception e) {
            System.out.println("Tax relief file button is not clickable");
        }
    }

    public void generateFile(String downloadPath, String fileName) throws Exception {
        // Listen for the download event
        Download download = page.waitForDownload(() -> {
            clickOnTaxReliefFileButton();
        });


        String pathToFile = String.valueOf(download.path());
        System.out.println("File downloaded to: " + pathToFile);

        // Define the new file path where it will be saved
        Path newFilePath = Paths.get(downloadPath, fileName);
        download.saveAs(Paths.get(newFilePath.toString()));
        System.out.println("File moved to: " + newFilePath);
    }
}
