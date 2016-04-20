package com.seadragon.apps.fashion.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExtractUtilTest {

	@Test
	public void testStringToFloat() {
		float value = ExtractUtil.stringToFloat("2,200.00");
		assertEquals(2200.00f, value, 0.000001);
	}

}
