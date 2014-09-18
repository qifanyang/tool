package com.tobe.excelutils;

import java.lang.reflect.Field;
import java.util.List;

public class DataHelper {
	
	public static void print(List<String> titles, Object obj) throws Exception{
		StringBuilder sb = new StringBuilder();
		
		sb.append("=====================数据展示开始=======================\n");
		for(String t : titles){
			if(isEmpty(t))continue;
			sb.append(t).append("\t");
		}
		sb.append("\n");
		for(String t : titles){
			if(isEmpty(t))continue;
			Field declaredField = obj.getClass().getDeclaredField(t);
			declaredField.setAccessible(true);
			sb.append(declaredField.get(obj)).append("\t");
		}
		sb.append("\n");
		sb.append("=====================数据展示结束=======================");
		
		System.out.println(sb.toString());
	}
	
	public static void print(List<String> titles, List<?> list) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("=====================数据展示开始=======================\n");
		for(String t : titles){
			if(isEmpty(t))continue;
			sb.append(t).append("\t");
		}
		sb.append("\n");
		for(Object obj : list){
			for(String t : titles){
				if(isEmpty(t))continue;
				Field declaredField = obj.getClass().getDeclaredField(t);
				declaredField.setAccessible(true);
				sb.append(declaredField.get(obj)).append("\t");
			}
			sb.append("\n");
		}
		sb.append("=====================数据展示结束=======================");
		
		System.out.println(sb.toString());
	}

	public static boolean isEmpty(String str){
		if(null == str || str.length() == 0){
			return true;
		}
		
		return false;
	}
}
