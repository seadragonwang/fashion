package com.seadragon.apps.fashion.job;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.seadragon.apps.fashion.service.ScrapeService;

@Service("dailyScrapingJob")
public class DailyScrapingJob {
	private static Logger logger = LoggerFactory.getLogger(DailyScrapingJob.class);
	
	@Autowired
	private WebDriver driver;
	
	@Autowired
	private ScrapeService scrapeService;
	
	public void run(ApplicationContext appCtx, int pageSize){
		logger.info("The daily scraping started ... ");
		Pageable request = null;
		int current = 0-pageSize;
		do{
			current += pageSize;
			request = new PageRequest(current, pageSize);
		} while(scrapeService.scrape(driver, request));
		logger.info("The daily scraping finished.");
	}
	
	public static final void main(String[] argvs){
		 ApplicationContext appCtx = new ClassPathXmlApplicationContext("fashion-application-context-sa.xml");
		 DailyScrapingJob scrapingJob = (DailyScrapingJob)appCtx.getBean("dailyScrapingJob");
		 scrapingJob.run(appCtx, 50);
	}
}
