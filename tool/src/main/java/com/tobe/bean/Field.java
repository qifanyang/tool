package com.tobe.bean;

/**
 * 字段描述
 */
public class Field {

	private String className;// 对应的java类型名字
	private String name;
	private String explain;// 注释
	private int listType;//1表示该字段为List

	public Field() {
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getListType() {
		return listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
}
