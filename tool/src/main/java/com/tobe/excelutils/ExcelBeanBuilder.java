package com.tobe.excelutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tobe.excelutils.code.JavaBeanBuilder;


/**
 * 根据excel文件,生成title的Bean
 * 
 * 在将excel数据插入数据库,可以用ASM生成class,但对于需要使用excel配置就需要生成对应的bean
 * 
 * @author TOBE
 * 
 */
public class ExcelBeanBuilder extends JavaBeanBuilder{

	private int titleIndex;// 标题栏在excel中的行

	private int sheetIndex;

	public ExcelBeanBuilder() {
		this(1, 0, null);
	}

	public ExcelBeanBuilder(int titleIndex, int sheetIndex, String packageName) {
		super(packageName, null);
		this.titleIndex = titleIndex;
		this.sheetIndex = sheetIndex;
	}

	public int getTitleIndex() {
		return titleIndex;
	}

	/**
	 * excel标题栏,默认为第一行,对应bean字段
	 * @param titleIndex
	 */
	public void setTitleIndex(int titleIndex) {
		this.titleIndex = titleIndex;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	/**
	 * 要解析的excel的sheet,默认第0个
	 * @param sheetIndex
	 */
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}


	public List<String> parse(String path) {
		List<String> titles = new ArrayList<String>();
		InputStream dataSource = ExlUtils.getDataSource(path);
		try {

			Workbook wb = new XSSFWorkbook(dataSource);
			String sheetName = wb.getSheetName(sheetIndex);
			Sheet sheet = wb.getSheetAt(sheetIndex);

//			titles.add(sheetName);// 0为sheetName
			setClassName(sheetName);
			Iterator<Row> rowIterator = sheet.rowIterator();

//			int rows = sheet.getPhysicalNumberOfRows();
			int rowCount = 1;
			while (rowIterator.hasNext()) {
				if (rowCount++ == titleIndex) {// 该行为标题栏
					Row row = rowIterator.next();
					
//					int syso = row.getPhysicalNumberOfCells();
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next(); 
						String title = cell.toString().trim();
						titles.add(title);
					}
					break;// title已将找到,跳出
				} else {
					continue;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dataSource) {
				try {
					dataSource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return titles;
	}


	public String buildCode(String path) {
		List<String> flist = parse(path);
		setFields(flist);
		return code();
	}

	
	
	public static void main(String[] args) {
//		System.out.println(toUpper("leifds", 0));
//		System.out.println(toUpper("leifds", 3));
		
		ExcelBeanBuilder beanBuilder = new ExcelBeanBuilder();
//		System.out.println(beanBuilder.buildCode("/商业活动.xlsx"));
		beanBuilder.setTitleIndex(2);
		System.out.println(beanBuilder.buildCode("/游戏配置.xlsx"));
	}
}
