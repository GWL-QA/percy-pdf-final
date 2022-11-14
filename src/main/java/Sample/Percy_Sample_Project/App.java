package Sample.Percy_Sample_Project;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.percy.selenium.Percy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.HttpServer;


/**
 * Hello world!
 *
 */
public class App extends http
{
	  public static WebDriver driver = null;
	  public static Percy pp;
	  private static ExecutorService serverExecutor;
	    private static HttpServer server;
	    private static final String TEST_URL = "http://localhost:3000";

	  
	  @BeforeClass
	  public void testing() throws IOException
	  {
		  serverExecutor = Executors.newFixedThreadPool(1);
	        server = App.startServer(serverExecutor);
	        
//////      
      ChromeOptions option = new ChromeOptions();
      option.setHeadless(true);
//      Dimension newDimension = new Dimension(800, 1024);
//		driver.manage().window().setSize(newDimension);
      
      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver(option);
      
      pp = new Percy(driver);
	  }
	  
	  
//		public static void main( String[] args )
//	    {
////			
////////	        System.out.println( "Hello World!" );
////////	        Reporter.log("Hello World!", true);
//	        Percy pp = new Percy(driver);
////////	        
////	        ChromeOptions option = new ChromeOptions();
//////	        option.setHeadless(true);
////	        
////	        WebDriverManager.chromedriver().setup();
////	        driver = new ChromeDriver(option);
//////	        
//	        driver.get("https://dev-wev.pantheonsite.io/wev-classes/");
//	        pp.snapshot("https://dev-wev.pantheonsite.io/wev-classes/", Arrays.asList(375,834, 1366,1600, 1440, 1280));
//////	        
////	        
//////	        driver.close();
////	        
//////			App t = new App();
//////			t.test();
////	       
//	    }
		 @Test
	     public void test() throws InterruptedException
	     {
			 
			 driver.get("https://dev-wev.pantheonsite.io/explore-entrepreneurship/");

//			 driver.get("https://dev-wev.pantheonsite.io/wev-classes/");
			 Thread.sleep(2000);
			 
			 driver.findElement(By.id("wt-cli-accept-all-btn")).click();
//			 
			 Thread.sleep(2000);
			 
		     pp.snapshot("1-page", Arrays.asList(1600,375,834));
//			 pp.snapshot(TEST_URL,Arrays.asList(375, 480, 720, 1280, 1440, 1920), null, false, "div#main-content, div.element.style {overflow: visible !important}");
//			 pp.snapshot("Home Page",Arrays.asList(375, 480, 720, 1280, 1440, 1920));
//			 pp.snapshot("Home Page",Arrays.asList(375, 480, 720, 1280, 1440, 1920), null, false, "div#main-content, div.element.style{overflow: visible !important}");
//			 pp.snapshot(TEST_URL,Arrays.asList(375, 480, 720, 1280, 1440, 1920), null, false, "div#main-content, div.element.style{overflow: initial !important}");
			 
			 
			 
	     }
}
