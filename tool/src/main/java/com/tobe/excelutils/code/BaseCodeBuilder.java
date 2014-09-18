package com.tobe.excelutils.code;

public abstract class BaseCodeBuilder {

	/**
	 * 字符串某个索引位置转为大写,用于类名,方法名
	 * @param s
	 * @param index
	 * @return
	 */
	public static String toUpper(String s, int index){
		if(index == 0){
			//首字母大写
			String upperCase = s.substring(0, 1).toUpperCase();
			String suffix = s.substring(1);
			return upperCase + suffix;
		}else{
			String pre = s.substring(0, index);
			String upperCase = s.substring(index, index + 1).toUpperCase();
			String suffix = s.substring(index + 1);
			return pre + upperCase + suffix;
		}
		
	}
}
