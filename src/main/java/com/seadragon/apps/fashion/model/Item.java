package com.seadragon.apps.fashion.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "ITEM")
public class Item {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ITEM_NUMBER")
	private String number;

	@Column(name = "ITEM_NAME")
	private String name;

	@Column(name = "ITEM_TYPE")
	private String type;

	@Column(name = "URL")
	private String url;

	@Column(name = "WEB_SITE")
	private String webSite;

	@Column(name = "DATE_CREATED")
	private Date dateCreated;

	@Column(name = "DATE_UPDATED")
	private Date dateUpdated;

	@Column(name = "AVAILABLE")
	private boolean available;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Product> products = new ArrayList<Product>();

	@ManyToMany(fetch=FetchType.EAGER, mappedBy="items")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Customer> customers;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<ItemImage> itemImages = new HashSet<ItemImage>();
	
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<ItemPromotion> itemPromotions;

	public Item(){}
	
	public Item(String webSite, String url){
		this.webSite = webSite;
		this.url = url;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Set<ItemImage> getItemImages() {
		return itemImages;
	}

	public void setItemImages(Set<ItemImage> itemImages) {
		this.itemImages = itemImages;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Set<ItemPromotion> getItemPromotions() {
		return itemPromotions;
	}

	public void setItemPromotions(Set<ItemPromotion> itemPromotions) {
		this.itemPromotions = itemPromotions;
	}
}
