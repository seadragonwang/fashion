package com.seadragon.apps.fashion.job;

import org.springframework.context.ApplicationContext;

public interface ScrapingJob {
	void run(ApplicationContext appCtx, int pageSize);
	void run(ApplicationContext appCtx, String webSite, String url);

}
