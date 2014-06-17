package com.tobe.bean;


public class Field
{

	private String className;
	private String name;
	private String explain;
	private int listType;

	public Field()
	{
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getListType()
	{
		return listType;
	}

	public void setListType(int listType)
	{
		this.listType = listType;
	}

	public String getExplain()
	{
		return explain;
	}

	public void setExplain(String explain)
	{
		this.explain = explain;
	}
}
