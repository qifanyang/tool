package com.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BackUtils {
	
	/**
	 * 睡眠sec秒
	 * @param sec 秒
	 */
	public static void sleep(int sec){
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**只支持excel 2007, 名字后缀.xlsx*/
	public static <T> List<T> load(InputStream ins, Class<T> c) throws Exception {
		List<T> list = new ArrayList<T>();
//		name = "/商业活动.xlsx";
//		ins = getClass().getResourceAsStream(name);
		Workbook wb = new XSSFWorkbook(ins);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.rowIterator();

		boolean isFirstRow = true;
		ArrayList<String> nameList = new ArrayList<String>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			int nameIndex = 0;
			T instance = c.newInstance();
			boolean isBlank = false;// 有空行,读取cell.getCellType()为3
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (isFirstRow) {// 先读取列名,名字顺序与值循序一致
					nameList.add(cell.toString().trim());
				} else {
					String cname = nameList.get(nameIndex++);// 列名
					Field declaredField = c.getDeclaredField(cname);
					declaredField.setAccessible(true);
					Type ftype = declaredField.getGenericType();
					int cellType = cell.getCellType();
					if (cellType == Cell.CELL_TYPE_BLANK) {
						isBlank = true;
						break;
					}
					if (ftype == int.class) {
						declaredField.set(instance,Double.valueOf(cell.getNumericCellValue()).intValue());
					} else if (ftype == String.class) {
						if(cellType == Cell.CELL_TYPE_FORMULA){
							FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
							CellValue cellValue = evaluator.evaluate(cell);
							String cv = cellValue.getStringValue();
							declaredField.set(instance, cv);
//							System.out.println(cellFormula);
						}else{
							declaredField.set(instance, cell.toString().trim());
						}
					} else {
						System.out.println("类型错误");
					}

				}
			}

			if (!isFirstRow && !isBlank) {
				list.add(instance);
			}

			isFirstRow = false;
		}
		return list;
	}
	
	/**只支持excel 2007, 名字后缀.xlsx*/
	public static <T> List<T> load(String name, Class<T> c) throws Exception {
		InputStream ins = null;
		try {
//			name = "/商业活动.xlsx";
			ins = c.getResourceAsStream(name);
			return load(ins, c);

		} catch (Exception e) {
			throw e;
		} finally {
			if(ins != null){
				ins.close();
			}
		}
	}

}
