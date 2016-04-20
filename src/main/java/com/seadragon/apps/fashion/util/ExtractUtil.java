package com.seadragon.apps.fashion.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractUtil {
	private static final Pattern PROMOTION_PATTERN = Pattern.compile("([0-9]+)%[ ]+off[ ]+with[ ]+code[ ]+([0-9A-Z]+)");
	
	private static final Pattern   PROMOTION_DATE_PATTERN = Pattern.compile(".+from (.+) through ([^.]+)(.|\n)+");

	public static List<String> extractPromotionDates(String str){
		Matcher m = PROMOTION_DATE_PATTERN.matcher(str);
		List<String> results = null;
		if(m.matches()){
			results = new ArrayList<String>();
			results.add(m.group(1).trim());
			results.add(m.group(2).trim());
		}
		return results;
	}
	
	public static List<String> extractPromotion(String str){
		Matcher matcher = PROMOTION_PATTERN.matcher(str);
		List<String> results = null;
		while(matcher.find()){
			results = new ArrayList<String>();
			results.add(matcher.group(1).trim());
			results.add(matcher.group(2).trim());
		}
		return results;
	}
	
	public static float stringToFloat(String str){
		return Float.parseFloat(str.trim().replaceAll("[\\$,]", ""));
	}
}
