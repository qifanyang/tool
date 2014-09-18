package com.tobe.bean;

import java.util.List;
import java.util.Set;

public class Bean{

	private String beanName;
	private String packageName;
	private String explain;
	private List<Field> fields;
	private Set imports;

	public Bean()
	{
	}

	public String getBeanName()
	{
		return beanName;
	}

	public void setBeanName(String beanName)
	{
		this.beanName = beanName;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public List<Field> getFields()
	{
		return fields;
	}

	public void setFields(List<Field> fields)
	{
		this.fields = fields;
	}

	public String getExplain()
	{
		return explain;
	}

	public void setExplain(String explain)
	{
		this.explain = explain;
	}

	public Set getImports()
	{
		return imports;
	}

	public void setImports(Set imports)
	{
		this.imports = imports;
	}
}
