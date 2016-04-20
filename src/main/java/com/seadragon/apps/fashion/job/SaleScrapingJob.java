package com.seadragon.apps.fashion.job;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.seadragon.apps.fashion.service.ItemService;
import com.seadragon.apps.fashion.service.ScrapeService;

public class SaleScrapingJob {
	private static Logger logger = LoggerFactory.getLogger(SaleScrapingJob.class);
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private WebDriver driver;
	
	@Autowired
	private ScrapeService scrapeService;

	public void run(ApplicationContext appCtx) {
//		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		logger.info("The daily sale scraping started ... ");
		scrapeService.scrapeSale(driver);
		
		logger.info("The daily sale scraping finished.");
		driver.close();
		driver.quit();
	}

	public static final void main(String[] argvs) {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("fashion-application-context-sa.xml");
		SaleScrapingJob scrapingJob = (SaleScrapingJob) appCtx.getBean("saleScrapingJob");
		scrapingJob.run(appCtx);
	}

}
