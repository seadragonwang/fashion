package com.seadragon.apps.fashion.scraper.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.model.ItemImage;
import com.seadragon.apps.fashion.model.Product;
import com.seadragon.apps.fashion.model.ProductDetail;
import com.seadragon.apps.fashion.scraper.Scraper;

public class JCrewScraper implements Scraper<Item> {
	private static final Logger logger = LoggerFactory.getLogger(JCrewScraper.class);
	private String webSite = "jcrew";
	private Gson gson;
	private CloseableHttpClient httpClient;
	private String serverUrl;
	public JCrewScraper() {
		httpClient = HttpClients.createDefault();
		gson = new GsonBuilder().create();
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public Item scrape(WebDriver driver, String url) {
		HttpPost httpPost = new HttpPost(serverUrl);
		CloseableHttpResponse response = null;
		Item item = null;
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("url", url));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			String pageHTML = EntityUtils.toString(entity);
			logger.info(pageHTML);
			
			item = gson.fromJson(pageHTML, Item.class);
			item.setWebSite(webSite);
			item.setUrl(url);
			if(item.getProducts() != null){
				for(Product product : item.getProducts()){
					product.setItem(item);
					if(product.getProductDetails() != null){
						for(ProductDetail pd : product.getProductDetails()){
							pd.setProduct(product);
						}
					}
				}
			}
			if(item.getItemImages() != null){
				for(ItemImage image : item.getItemImages()){
					image.setItem(item);
				}
			}
			EntityUtils.consume(entity);

		} catch (Exception ex) {
			logger.error("Failed to scrape : " + url, ex);
		} finally {
			try {
				httpPost.releaseConnection();
				response.close();
			} catch (Exception ex) {
				logger.error("Failed to close HttpPost or HttpResponse object", ex);
			}
		}
		return item;
	}

}
