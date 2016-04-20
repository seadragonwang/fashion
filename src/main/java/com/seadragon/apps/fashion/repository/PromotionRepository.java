package com.seadragon.apps.fashion.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seadragon.apps.fashion.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long>{
	@Query("select p from Promotion p where p.webSite=:webSite and p.startDate <= :currentTime and p.endDate >= :currentTime and p.promotionCode=:promotionCode")
	Promotion findByPromotionCode(@Param("webSite")String webSite, @Param("promotionCode")String promotionCode, @Param("currentTime")Date currentTime);
}
