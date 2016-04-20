package com.seadragon.apps.fashion.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seadragon.apps.fashion.model.Promotion;

public interface PromotionService {
	Promotion save(Promotion promotion);
	void update(Promotion promotion);
	long count();
	Page<Promotion> findAll(Pageable page);
	Promotion findByPromotionCode(String webSite, String promotionCode);
}
