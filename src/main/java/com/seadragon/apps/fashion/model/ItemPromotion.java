package com.seadragon.apps.fashion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_PROMOTION")
public class ItemPromotion {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "DISCOUNT")
	private float discount;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "PROMOTION_ID")
	private Promotion promotion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
}
