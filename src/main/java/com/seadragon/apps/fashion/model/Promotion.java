package com.seadragon.apps.fashion.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// April 10, 2014, 12:01AM ET

@Entity
@Table(name="PROMOTION")
public class Promotion {
	private static final Logger logger  = LoggerFactory.getLogger(Promotion.class);
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name="START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name="END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Column(name="PROMOTION_CODE")
	@Filter(name = "filterByDate", condition = "START_DATE >= NOW() and END_DATE <= NOW")
	private String promotionCode;

	@Column(name="DISCLAIMER")
	private String disclaimer;

	@Column(name="WEB_SITE")
	private String webSite;

	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy, hh:mmaaa z");

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	
	public void setStartDate(String startDate){
		try{
			this.startDate = sdf.parse(startDate.replaceAll("ET$", "EDT").replaceAll("PT$", "PDT"));
		}catch(ParseException ex){
			logger.error(ex.getMessage(), ex);
			this.startDate = null;
		}
	}
	
	public void setEndDate(String endDate){
		try{
			this.endDate = sdf.parse(endDate.replaceAll("ET$", "EDT").replaceAll("PT$", "PDT"));
		}catch(ParseException ex){
			logger.error(ex.getMessage(), ex);
			this.endDate = null;
		}
	}
}
