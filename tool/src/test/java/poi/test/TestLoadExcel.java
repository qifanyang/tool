package poi.test;

import ht.vo.ActivityVO;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestLoadExcel {

	public <T> List<T> load(String name, Class<T> c) throws Exception {
		InputStream ins = null;
		try {
			List<T> list = new ArrayList<T>();
//			name = "/商业活动.xlsx";
			ins = getClass().getResourceAsStream(name);
			Workbook wb = new XSSFWorkbook(ins);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.rowIterator();

			wb.createSheet("haha");
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
//								System.out.println(cellFormula);
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

				if(isFirstRow){
					for(String n : nameList){
						System.out.println("#{"+n + "},");
					}
				}
				isFirstRow = false;
			}
			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			if(ins != null){
				ins.close();
			}
		}
	}

	// if(isFirstRow){//先读取列名,名字顺序与值循序一致
	// nameList.add(cell.toString().trim());
	// }else{
	// String cname = nameList.get(nameIndex++);//列名
	// int cellType = cell.getCellType();
	// if(cellType == Cell.CELL_TYPE_NUMERIC){
	// System.out.println();
	// }else if(cellType == Cell.CELL_TYPE_STRING){
	//
	// }
	//
	// c.getField(cname).set(vo, 1)
	// }

	public static void main(String[] args) throws Exception {
		List<ActivityVO> list = new TestLoadExcel().load("/商业活动.xlsx",ActivityVO.class);
//		List<ActivityVO> list1 = new TestLoadExcel().load("/商业活动.xlsx",ActivityVO.class);
		System.out.println("");

	}
}
