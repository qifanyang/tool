package com.tobe.excelutils.job;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tobe.excelutils.ExcelResultSet;
import com.tobe.excelutils.SelectSQL;

/**执行查询:
 * 默认第一行为标题栏
 * 
 * */
public class SelectJob implements IJob<ExcelResultSet>{
	
	private InputStream dataSource;
	private SelectSQL sql;
	
	public SelectJob(InputStream dataSource, SelectSQL sql) {
		this.dataSource = dataSource;
		this.sql = sql;
	}
	
	public ExcelResultSet excute() {
		ExcelResultSet rs = new ExcelResultSet();
		try {
				// 执行查询操作
				// 查询字段列表全部转为小写
				Workbook wb = new XSSFWorkbook(dataSource);
				rs.setWb(wb);
	
				Sheet sheet = wb.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.rowIterator();
	
				boolean isFirstRow = true;
				while (rowIterator.hasNext()) {
					boolean addRow = false;//默认该行记录不加入结果集,rowFilter判断是否加入
					int titleIndex = 0;
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					boolean isBlank = false;// 有空行,读取cell.getCellType()为3
					// 遍历每一行,放入ExcelResultSet,遇到空不处理,不放入ExcelResultSet
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if(isFirstRow){
							String title = cell.toString().trim();
							if(!sql.show(title)){
								rs.getHeaders().add("");
							}else {
								rs.getHeaders().add(title);
							}
						}else{
							int cellType = cell.getCellType();
							if (cellType == Cell.CELL_TYPE_BLANK || cellType == Cell.CELL_TYPE_ERROR) {
								isBlank = true;
								break;
							}
							
							//空白不会到这里
							addRow = sql.whereFilter(rs, cell, titleIndex);
							if(!addRow){
								break;
							}
							titleIndex++;//没一列加1
						}
					}
	
					// if (!isFirstRow && !isBlank) {
					// list.add(instance);
					// }
					//不是第一行,不是空白,不忽略
					if (!isFirstRow && !isBlank && addRow) {
						rs.getRowList().add(row);
					}
	
					isFirstRow = false;
				}
		
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			return rs;
		}
		
		
}
