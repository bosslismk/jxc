package com.inventory.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtils {
	public static String createOrderNo(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuilder time=new StringBuilder(sdf.format(new Date()));
		return time.toString();
	}
}
