package com.seadragon.apps.fashion.service.impl;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.seadragon.apps.fashion.model.Customer;
import com.seadragon.apps.fashion.model.Item;
import com.seadragon.apps.fashion.model.ItemPromotion;
import com.seadragon.apps.fashion.model.Product;
import com.seadragon.apps.fashion.model.ProductDetail;
import com.seadragon.apps.fashion.service.CustomerService;
import com.seadragon.apps.fashion.service.IndexService;
import com.seadragon.apps.fashion.service.ItemService;


public class IndexServiceESJavaImpl implements IndexService {
	private static Logger logger = LoggerFactory.getLogger(IndexServiceESJavaImpl.class);
	public static final String PROPERTIES = "properties";
	public static final String FASHION_TYPE_ITEM = "item";
	public static final String FASHION_TYPE_CUSTOMER= "customer";

	@Autowired
	private Client client;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CustomerService customerService;
	
	@Override
	public void insertDocument(Item item) throws Exception {
		createIndexRequestBuilder(item).execute().actionGet();
	}

	@Override
	public void insertBulkItemDocuments(List<Item> itemList) throws Exception {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(Item item: itemList){
			bulkRequest.add(createIndexRequestBuilder(item));
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			Iterator<BulkItemResponse> iter = bulkResponse.iterator();
			while(iter.hasNext()){
				BulkItemResponse bir = iter.next();
				if(bir.isFailed()){
					logger.error(bir.toString());
				}
			}
		}
	}

	@Override
	public void insertDocument(Customer customer) throws Exception {
		createIndexRequestBuilder(customer).execute().actionGet();
	}

	@Override
	public void insertBulkCustomerDocuments(List<Customer> customerList) throws Exception {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(Customer customer: customerList){
			bulkRequest.add(createIndexRequestBuilder(customer));
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			Iterator<BulkItemResponse> iter = bulkResponse.iterator();
			while(iter.hasNext()){
				BulkItemResponse bir = iter.next();
				if(bir.isFailed()){
					logger.error(bir.toString());
				}
			}
		}

	}
	
	@Override
	public void createIndex(String indexName){
		client.admin().indices().prepareCreate(indexName).execute().actionGet();
	}
	
	@Override
	public void deleteIndex(String indexName){
		client.admin().indices().prepareDelete(indexName).execute().actionGet();
	}

	@Override
	public void deleteDocuments(String indexName) {
		client.prepareDeleteByQuery(indexName).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
	}

	@Override
	public void deleteDocuments(String indexName, String typeName) {
		client.prepareDeleteByQuery(indexName).setQuery(QueryBuilders.termQuery("_type", typeName)).execute().actionGet();
	}

	@Override
	public void deleteIndex(String indexName, String typeName) {
		client.admin().indices().prepareDelete(indexName, typeName).execute().actionGet();
	}

	@Override
	public void reindex(String indexName) {
		deleteDocuments(indexName);
		if(indexName().equals(indexName)){
			reindexItem(100);
			reindexCustomer(100);
		}
	}

	@Override
	public void reindexCustomer(int pageSize) {
		try {
			int offset = 0;
			Page<Customer> page = customerService.findAll(new PageRequest(offset, pageSize));
			while (page != null && page.getSize() > 0) {
				insertBulkCustomerDocuments(page.getContent());
				offset += pageSize;
				page = customerService.findAll(new PageRequest(offset, pageSize));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void reindexItem(int pageSize) {
		try {
			int offset = 0;
			Page<Item> page = itemService.findAll(new PageRequest(offset, pageSize));
			while (page != null && page.getSize() > 0) {
				insertBulkItemDocuments(page.getContent());
				offset += pageSize;
				page = itemService.findAll(new PageRequest(offset, pageSize));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void rebuildIndex(String indexName) {
		deleteIndex(indexName);
		createIndex(indexName);
		if (indexName().equals(indexName)) {
			try {
				buildItemMapping();
				buildCustomerMapping();
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
			reindexItem(100);
			reindexCustomer(100);
		}
	}
	
	public IndexRequestBuilder createIndexRequestBuilder(Customer customer) throws Exception{
		XContentBuilder xb = jsonBuilder()
				.startObject()
					.field("id", customer.getId())
					.field("firstName", customer.getFirstName())
					.field("lastName", customer.getLastName())
					.field("email", customer.getEmail())
					.field("phoneNumber", customer.getPhoneNumber())
					.field("dateOfBirth", customer.getDateOfBirth())
				.endObject();
		return client.prepareIndex(indexName(), "customer", Long.toString(customer.getId())).setSource(xb);
	}

	public XContentBuilder buildCustomerMapping() throws Exception {
		XContentBuilder xbMapping = jsonBuilder()
				.startObject()
					.startObject("customer")
						.startObject(PROPERTIES)
							.startObject("id")
								.field("type", "long")
							.endObject()
							.startObject("fistName")
								.field("type", "string")
							.endObject()
							.startObject("email")
								.field("type", "string")
								.field("index", "no")
							.endObject()
							.startObject("dateOfBirth")
								.field("type", "date")
								.field("analyzer", "english")
							.endObject()
							.startObject("phoneNumber")
								.field("type", "string")
								.field("index", "no")
							.endObject()
						.endObject()
					.endObject()
				.endObject();
		client.admin().indices().preparePutMapping(indexName())
		.setType("customer").setSource(xbMapping).execute().actionGet();
		logger.info(xbMapping.string());
		return xbMapping;
	}
	
	public IndexRequestBuilder createIndexRequestBuilder(Item item) throws Exception{
		List<Float> priceAndDiscount = getPriceAndDiscount(item);
		XContentBuilder xb = jsonBuilder()
				.startObject()
					.field("id", item.getId())
					.field("ItemNumber", item.getNumber())
					.field("itemName", item.getName())
					.field("description", item.getDescription())
					.field("price", priceAndDiscount.get(0))
					.field("discount", priceAndDiscount.get(1))
				.endObject();
		return client.prepareIndex(indexName(), "item", Long.toString(item.getId())).setSource(xb);
	}

	public XContentBuilder buildItemMapping() throws Exception {
		XContentBuilder xbMapping = jsonBuilder()
				.startObject()
					.startObject("item")
						.startObject(PROPERTIES)
							.startObject("id")
								.field("type", "long")
							.endObject()
							.startObject("itemNumber")
								.field("type", "string")
							.endObject()
							.startObject("itemName")
								.field("type", "string")
							.endObject()
							.startObject("description")
								.field("type", "string")
								.field("analyzer", "english")
							.endObject()
							.startObject("price")
								.field("type", "float")
							.endObject()
							.startObject("discount")
								.field("type", "float")
							.endObject()
							.startObject("imageUrl")
								.field("type", "string")
							.endObject()
						.endObject()
					.endObject()
				.endObject();
		client.admin().indices().preparePutMapping(indexName())
		.setType("item").setSource(xbMapping).execute().actionGet();
		logger.info(xbMapping.string());
		return xbMapping;
	}

	private String 	indexName(){
		return "fashion";
	}
	
	public List<Float> getPriceAndDiscount(Item item) {
		float promotionDiscount = 1.0f;
		if (item.getItemPromotions() != null) {
			for (ItemPromotion itemPromotion : item.getItemPromotions()) {
				promotionDiscount *= itemPromotion.getDiscount();
			}
		}
		
		float minActualPrice = Float.MAX_VALUE;
		float maxDiscount = 0f;
		if (item.getProducts() != null) {
			for (Product product : item.getProducts()) {
				float originalPrice = product.getOriginalPrice();
				float actualPrice = 0f;
				float discount = 0f;
				List<ProductDetail> productDetails = product.getProductDetails();
				if (productDetails != null) {
					actualPrice = promotionDiscount * productDetails.get(0).getPrice();
					if (actualPrice < minActualPrice) {
						minActualPrice = actualPrice;
					}
					discount = (originalPrice - actualPrice) / originalPrice;
					if (discount > maxDiscount) {
						maxDiscount = discount;
					}
				}
			}
		}
		List<Float> results = new ArrayList<Float>();
		results.add(minActualPrice);
		results.add(maxDiscount);
		return results;
	}

	@Override
	public void reindex(String indexName, String typeName) {
		deleteDocuments(indexName, typeName);
		if (indexName().equals(indexName)) {
			if (FASHION_TYPE_ITEM.equals(typeName)) {
				reindexItem(100);
			} else if (FASHION_TYPE_CUSTOMER.equals(typeName)) {
				reindexCustomer(100);
			}

		}
	}
}
