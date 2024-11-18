package hooks;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.gpo.Driver;


public class Application {
    private final Driver driverFactory;
    public Application(){
        this.driverFactory = new Driver();
    }

    @Before("@UI")
    public Driver getBaseTest() {
        return driverFactory;
    }

    @After("@UI")
    public void tearDown(Scenario scenario) {
        Page page = driverFactory.getPage();
        if (scenario.isFailed()) {
            try {
                String screenshotName = scenario.getName().replaceAll(" ", "_");
                byte[] sourcePath = page.screenshot(new Page.ScreenshotOptions().setType(ScreenshotType.PNG));
                scenario.attach(sourcePath, "image/png", screenshotName);
                System.out.println("Screenshot attached for scenario: " + screenshotName);
            } catch (Exception e) {
                System.out.println("Error capturing screenshot: " + e.getMessage());
            }
        }
    }
}
