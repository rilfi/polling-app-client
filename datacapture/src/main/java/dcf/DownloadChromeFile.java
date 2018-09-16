package dcf;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

public class DownloadChromeFile {
       public static void main(String[] args) {
       System.setProperty("webdriver.chrome.driver","D:\\aaa\\chromedriver.exe");
       String downloadFilepath = "c:\\download";

       HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
       chromePrefs.put("profile.default_content_settings.popups", 0);
       chromePrefs.put("download.default_directory", downloadFilepath);
       ChromeOptions options = new ChromeOptions();
       HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
       options.setExperimentalOption("prefs", chromePrefs);
       options.addArguments("--test-type");
       options.addArguments("--disable-extensions"); //to disable browser extension popup

       DesiredCapabilities cap = DesiredCapabilities.chrome();
       cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
       cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); // Bydefault it will accepts all popups.
       cap.setCapability(ChromeOptions.CAPABILITY, options);
         Map<String, Object> prefs = new HashMap<String, Object>();
         prefs.put("safebrowsing.enabled", "true");
         options.setExperimentalOption("prefs", prefs);
         ChromeDriver chromeDriver = new ChromeDriver(options);
       ChromeDriver driver = new ChromeDriver(cap);
                driver.get("https://aaoa.flightlogger.net/report/flight");
                //driver.findElement(By.xpath("Export Button xpath")).click();
        }
}