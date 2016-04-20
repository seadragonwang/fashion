package com.seadragon.apps.fashion.service;

import java.util.List;

import com.seadragon.apps.fashion.model.Customer;
import com.seadragon.apps.fashion.model.Item;

public interface IndexService {

	void insertDocument(Item item) throws Exception;

	void insertBulkItemDocuments(List<Item> itemList) throws Exception;

	void insertDocument(Customer customer) throws Exception;

	void insertBulkCustomerDocuments(List<Customer> customerList)
			throws Exception;

	void createIndex(String indexName);

	void deleteIndex(String indexName);

	void deleteDocuments(String indexName);

	void deleteDocuments(String indexName, String typeName);

	void deleteIndex(String indexName, String typeName);

	void reindex(String indexName);

	void reindex(String indexName, String typeName);

	void reindexCustomer(int pageSize);

	void reindexItem(int pageSize);

	void rebuildIndex(String indexName);
}
