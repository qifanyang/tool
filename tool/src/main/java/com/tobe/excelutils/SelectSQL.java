package com.tobe.excelutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

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
	private boolean isAddRow = true;//配合where子句,判断是否将该行加入结果集中
	public boolean isAddRow() {
		return isAddRow;
	}
	/**
	 * where("name", "xiaoming");
	 *  where子句表示值等于value才放入结果集,否则不放入
	 * @param titles
	 * @param cell
	 * @param titleIndex
	 * @return
	 */
	public boolean whereFilter(ExcelResultSet rs, Cell cell, int titleIndex){
		if(wheres.size() == 0){
			isAddRow = true;
			return true;//没有where字段,全部显示
		}
		
		//这里一行会调用多次,只有当匹配的字段值相等才把该行记录加入结果集中
		String t = rs.getHeaders().get(titleIndex).toLowerCase();
		if(!wheres.containsKey(t)){
			return true;
		}else{
			Object object = wheres.get(t);//name = kkkkk
			if(object != null){//找到需要的字段,判断值,若不等,返回false,提前结束该行的扫描
//				if(!cell.toString().equals(object.toString())){//
//					isAddRow = false;
//					return false;
//				}
				if(!ExlUtils.getObjectRealValue(rs, cell).equals(object.toString())){//
					isAddRow = false;
					return false;
				}
			}
		}
		
		
		//当扫描到最后一个字段,还是没有匹配到,表示where子句列明出错,抛出错误
		
		
		//没有在where中也添加到结果集中
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
