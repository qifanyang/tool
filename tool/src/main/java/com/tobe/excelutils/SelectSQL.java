package com.tobe.excelutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SelectSQL implements ISQL {

	private String sheetName = "0";
	private List<String> fields = new ArrayList<String>();// 查询字段,手动拼接
	private List<String> ignores = new ArrayList<String>();// 忽略查询字段,手动拼接
	private Map<String, Object> wheres = new HashMap<String, Object>();// 条件字段,手动拼接
	private List<String> groups = new ArrayList<String>();// 分组字段,手动拼接
	private List<String> orders = new ArrayList<String>();// 排序字段,手动拼接

	@Override
	public StatementType stmtType() {
		return StatementType.SELECT;
	}

	public SelectSQL select(String f) {
		fields.add(f.toLowerCase());
		return this;
	}
	
	public SelectSQL ignore(String i) {
		ignores.add(i.toLowerCase());
		return this;
	}

	public SelectSQL where(String title, Object value) {
		wheres.put(title.toLowerCase(), value);
		return this;
	}

	public SelectSQL groupBy(String g) {
		groups.add(g.toLowerCase());
		return this;
	}

	public SelectSQL orderBy(String o) {
		orders.add(o);
		return this;
	}

	
	public boolean show(String title){
		boolean show = true;
		String lf = title.toLowerCase();
		if(fields.size() > 0){//大于0表示只显示指定的字段值,后面也可以忽略
			show = fields.contains(lf);
		}
		if(ignores.size() > 0){
			if(ignores.contains(lf)){
				show = false;
			}
		}
		return show;
	}
	
	//表达式还没有处理,需要把方法提取到Utils中
	public boolean rowFilter(List<String> titles, Cell cell, int titleIndex){
		if(wheres.size() == 0)return true;//全部显示
		
		String t = titles.get(titleIndex).toLowerCase();
		Object object = wheres.get(t);//name = kkkkk
		if(object != null){
			 if(cell.toString().equals(object.toString())){//B5 相等表示忽略
				 return false;
			 }
		}
		return true;
		
	}
	
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void clear() {
		fields.clear();
		ignores.clear();
		wheres.clear();
		groups.clear();
		orders.clear();
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public Map<String, Object> getWheres() {
		return wheres;
	}

	public void setWheres(Map<String, Object> wheres) {
		this.wheres = wheres;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getOrders() {
		return orders;
	}
	
	public List<String> getIgnores() {
		return ignores;
	}

	public void setOrders(List<String> orders) {
		this.orders = orders;
	}

	
}
