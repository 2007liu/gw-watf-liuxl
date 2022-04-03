package cn.com.goldenwater.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 格式转换
 * 
 * @version 创建时间：2012-9-9
 */
public class FormatUtils {

	public static String formatNumber(Double input) {
		DecimalFormat df = new DecimalFormat("###,###.00");
		return df.format(input);
	}

	public static String formatNumber(String input) {
		DecimalFormat df = new DecimalFormat("###,###.00");
		return df.format(Double.parseDouble(input));
	}

	public static String formatNumber(double input, String formatString) {
		DecimalFormat df = new DecimalFormat(formatString);
		return df.format(input);
	}

	public static String formatNumber(Double input, String formatString) {
		DecimalFormat df = new DecimalFormat(formatString);
		return df.format(input);
	}

	public static String formatNumber(String input, String formatString) {
		DecimalFormat df = new DecimalFormat(formatString);
		return df.format(Double.parseDouble(input));
	}




	public static double round(double d) {
		return Math.round(d * 100) / 100d;
	}

	public static double round(Object d) {
		if (d instanceof Double) {
			return round(((Double) d).doubleValue());
		} else {
			return 0;
		}
	}

	public static String roundAndToStr(BigDecimal d) {
		return roundAndToStr(d.doubleValue());
	}

	public static String roundAndToStr(double d) {
		double dd = round(d);
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(dd);
	}

	public static String roundAndToStr(double d, String formatString) {
		double dd = round(d);
		DecimalFormat format = new DecimalFormat(formatString);
		return format.format(dd);
	}

	public static String roundAndToStr(String s, String formatString) {
		if (s == null || "".equals(s.trim()) == true) {
			return formatNumber(0.0, formatString);
		}
		double d = Double.parseDouble(s);
		return roundAndToStr(d, formatString);
	}



}
