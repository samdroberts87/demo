package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static WebDriver driver;

    public static void initDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");

            // Disable Chrome password manager + Safe Browsing
//            Map<String, Object> prefs = new HashMap<>();
//            prefs.put("credentials_enable_service", false);
//            prefs.put("profile.password_manager_enabled", false);

            // Safe Browsing prefs
//            prefs.put("safebrowsing.enabled", false);
//            prefs.put("safebrowsing.disable_download_protection", true);
//            prefs.put("safebrowsing.disable_extension_blacklist", true);
//
//            // Additional password breach detection disabling
//            prefs.put("profile.password_manager_leak_detection", false);
//            prefs.put("profile.default_content_setting_values.notifications", 2);
//            prefs.put("profile.default_content_settings.popups", 0);
//
//            options.setExperimentalOption("prefs", prefs);
//
//            // Comprehensive argument list to disable password features
//            options.addArguments(
//                    "--disable-save-password-bubble",
//                    "--disable-notifications",
//                    "--disable-features=PasswordManagerOnboarding,PasswordLeakDetection,PasswordManager,AutofillPasswordGeneration",
//                    "--disable-password-generation",
//                    "--disable-password-manager-reauthentication",
//                    "--disable-component-extensions-with-background-pages",
//                    "--disable-background-timer-throttling",
//                    "--disable-backgrounding-occluded-windows",
//                    "--disable-renderer-backgrounding",
//                    "--disable-field-trial-config",
//                    "--disable-ipc-flooding-protection",
//                    "--password-store=basic",
//                    "--use-mock-keychain",
//                    "--disable-web-security",
//                    "--disable-site-isolation-trials",
//                    "--disable-features=VizDisplayCompositor"
//            );

            driver = new ChromeDriver(options);
//            driver.manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}