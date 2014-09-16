package com.tobe.excelutils;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

//用来访问Excel数据
public class ExcelResultSet{
	
	private Workbook wb;
	
	private List<Row> rowList = new ArrayList<Row>();//第一行为header
	
	private List<String> headers= new ArrayList<String>();
	private int cursor = -1;//游标
	
	public int getRow(){
		return cursor;
	}
	
	public boolean next(){
		if(0 == rowList.size()) return false;
		if(cursor >= rowList.size() - 1) return false;
		cursor++;
		return true;
	}
	
	private Cell cell(int i){
		return rowList.get(cursor).getCell(i);
	}
	
	private int index(String name){
		for(int i = 0 , size = headers.size(); i < size; i++){
			if(headers.get(i).equalsIgnoreCase(name)){
				return i;
			}
		}
		return -1;
	}
	
	public List<Row> getRowList() {
		return rowList;
	}

	public void setRowList(List<Row> rowList) {
		this.rowList = rowList;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	
	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	public int getInt(int columnIndex){
		Cell cell = cell(columnIndex);
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			return Double.valueOf(cellValue.getNumberValue()).intValue();
		}
		return Double.valueOf(cell(columnIndex).getNumericCellValue()).intValue();
	}
	
	public int getInt(String columnLabel){
		return getInt(index(columnLabel));
	}
	
	public String getString(int columnIndex){
		Cell cell = cell(columnIndex);
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			return cellValue.getStringValue();
		}
		return cell(columnIndex).toString().trim();
	}
	
	public String getString(String columnLabel){
		return getString(index(columnLabel));
	}

	public Object getObject(int index) {
		return cell(index).toString();
	}
	
	
}
