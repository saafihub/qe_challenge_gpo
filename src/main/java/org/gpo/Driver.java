package org.gpo;

import com.microsoft.playwright.*;
import org.gpo.utils.ConfigReader;

import java.util.Properties;

public class Driver {

    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;
    Properties prop;

    public Properties getProp(){
        ConfigReader configReader = new ConfigReader();
        prop = configReader.initializeProperties();
        return prop;
    }

    public Page initializeDriver() {
        Playwright playwright = Playwright.create();
        String browserName = getProp().getProperty("browser");
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false); // You can change headless as per your need
        if (browser == null) {
            System.out.println("Launching browser: " + browserName);
            if (browserName.equalsIgnoreCase("chrome")) {
                browser = playwright.chromium().launch(options);
            }
            else if (browserName.equalsIgnoreCase("firefox")) {
                browser = playwright.firefox().launch(options);
            }
            else {
                System.out.println("Invalid browser name");
            }
        }
        System.out.println("Browser initialized: " + browser);
        context = browser.newContext();
        page = context.newPage();
        page.setViewportSize(1920, 1080);
        page.context().clearCookies();

        return page;
    }

    public Page getPage() {
        if (page == null) {
            page = initializeDriver();
        }
        return page;
    }
}
