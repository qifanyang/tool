package com.tobe.logdb;

import java.util.HashMap;
import java.util.Map;

public class J2MTypeMapping {

	/**
	 * java数据类型到mysql数据类型的映射
	 */
	public static Map<String, String> j2mTypeMap = new HashMap<String, String>();
	static {
		//数字型
		j2mTypeMap.put("byte", "TINYINT");
		j2mTypeMap.put("Byte", "TINYINT");
		j2mTypeMap.put("short", "SMALLINT");
		j2mTypeMap.put("Short", "SMALLINT");
		j2mTypeMap.put("int", "INT");
		j2mTypeMap.put("Integer", "INTEGER");
		j2mTypeMap.put("long", "BIGINT");
		j2mTypeMap.put("Long", "BIGINT");
		
		j2mTypeMap.put("float", "FLOAT");
		j2mTypeMap.put("Float", "FLOAT");
		j2mTypeMap.put("double", "DOUBLE");
		j2mTypeMap.put("Double", "DOUBLE");
		
		
		//文本型
		j2mTypeMap.put("String", "VARCHAR");
		
		//java没有这个类型,这里是用的注解
		j2mTypeMap.put("TEXT", "TEXT");
		j2mTypeMap.put("LONGTEXT", "LONGTEXT");
		
		//时间
		j2mTypeMap.put("Date", "DATETIME");
		
		//二进制
		j2mTypeMap.put("byte[]", "BLOB");
		// TODO ....
	}
	
	public static String getMySQLType(String javaType){
		return j2mTypeMap.get(javaType);
	}
}
