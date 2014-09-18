package com.tobe.excelutils;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

public class ExlUtils {

	/**
	 * 
	 * @param rs
	 * @param cell excel中没有填写值则为空
	 * @return
	 */
	public static int getIntRealValue(ExcelResultSet rs, Cell cell){
		if(null == cell){
			return 0;
		}
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			FormulaEvaluator evaluator = rs.getWb().getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			return Double.valueOf(cellValue.getNumberValue()).intValue();
		}
		return Double.valueOf(cell.getNumericCellValue()).intValue();
	}
	
	
	public static String getStringRealValue(ExcelResultSet rs, Cell cell){
		if(null == cell){
			return null;
		}
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			FormulaEvaluator evaluator = rs.getWb().getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			return cellValue.getStringValue();
		}
		return cell.toString().trim();
	}
	
	public static Object getObjectRealValue(ExcelResultSet rs, Cell cell){
		if(null == cell){
			return null;
		}
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			FormulaEvaluator evaluator = rs.getWb().getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			return cellValue.getStringValue();
		}
		return cell.toString().trim();
	}

	/**先查找classpath,为空再查找文件系统*/
	public static InputStream getDataSource(String path){
		InputStream dataSource = null;
		if (!path.startsWith("/")) {
			dataSource = ExlUtils.class.getResourceAsStream("/" + path);
		}else{
			dataSource = ExlUtils.class.getResourceAsStream(path);
		}
		
		if(dataSource == null){
			try {
				dataSource = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("找不到文件  , path = " + path);
			}
		}
		
		return dataSource;
		
	}
}
