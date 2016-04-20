package com.seadragon.apps.fashion.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.seadragon.apps.fashion.model.Promotion;
import com.seadragon.apps.fashion.repository.PromotionRepository;
import com.seadragon.apps.fashion.service.PromotionService;

@Service("promotionService")
@Transactional
public class PromotionServiceImpl implements PromotionService {
	@Autowired
	private PromotionRepository promotionRepository;
	
	@Override
	public Promotion save(Promotion promotion) {
		return promotionRepository.saveAndFlush(promotion);
	}

	@Override
	public void update(Promotion promotion) {
		promotionRepository.saveAndFlush(promotion);
	}

	@Override
	public long count() {
		return promotionRepository.count();
	}

	@Override
	public Page<Promotion> findAll(Pageable page) {
		return promotionRepository.findAll(page);
	}

	public Promotion findByPromotionCode(String webSite, String promotionCode) {
		return promotionRepository.findByPromotionCode(webSite, promotionCode, new Date());
	}
}
