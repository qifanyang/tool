package com.tobe.excelutils.code;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据字段类表构建一个javaBean
 *
 */
public class JavaBeanBuilder implements ICodeBuilder {

	protected String packageName;

	protected String className;
	
	protected List<String> fields = new ArrayList<String>();
	
	public JavaBeanBuilder(String packageName, String className){
		this.packageName = packageName;
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	/**
	 * 生成类的包名,默认为空,没有包
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	@Override
	public String code() {
		StringBuilder sb = new StringBuilder();

		if(null != packageName && packageName.length() > 0){
			sb.append("package ").append(packageName).append("\n");
		}
		
		//类名
		sb.append("public class ").append(toUpper(className, 0)).append("{ \n\n");
		
		//字段
		for(int i = 0, size = fields.size(); i < size; i++){
			sb.append("\tprivate String ").append(fields.get(i)).append(";\n\n");
		}
		
		//get set 方法
		for(int i = 0, size = fields.size(); i < size; i++){
			String f = fields.get(i);
			
			sb.append("\tpublic void ")
			.append("set").append(toUpper(f, 0)).append("(String ").append(f).append(") {\n")//方法名
			.append("\t\tthis.").append(f).append(" = ").append(f).append(";")//方法体
			.append("\n\t}\n\n");
			
			sb.append("\tpublic String ").append("get").append(toUpper(f, 0)).append("(").append(") {\n")//方法名
			.append("\t\treturn ").append(f).append(";")//方法体
			.append("\n\t}\n\n");
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	/**
	 * list为字段列表
	 * 
	 * @param fields
	 * @return
	 */
	public String buildCode(List<String> fields) {
		StringBuilder sb = new StringBuilder();

		if(null != packageName && packageName.length() > 0){
			sb.append("package ").append(packageName).append("\n");
		}
		
		//类名
		sb.append("public class ").append(toUpper(className, 0)).append("{ \n\n");
		
		//字段
		for(int i = 0, size = fields.size(); i < size; i++){
			sb.append("\tprivate String ").append(fields.get(i)).append(";\n\n");
		}
		
		//get set 方法
		for(int i = 0, size = fields.size(); i < size; i++){
			String f = fields.get(i);
			
			sb.append("\tpublic void ")
			.append("set").append(toUpper(f, 0)).append("(String ").append(f).append(") {\n")//方法名
			.append("\t\tthis.").append(f).append(" = ").append(f).append(";")//方法体
			.append("\n\t}\n\n");
			
			sb.append("\tpublic String ").append("get").append(toUpper(f, 0)).append("(").append(") {\n")//方法名
			.append("\t\treturn ").append(f).append(";")//方法体
			.append("\n\t}\n\n");
		}
		
		sb.append("}");
		
		return sb.toString();

	}
	
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
