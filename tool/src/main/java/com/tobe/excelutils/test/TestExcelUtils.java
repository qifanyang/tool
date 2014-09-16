package com.tobe.excelutils.test;


import java.util.List;

import com.tobe.excelutils.ExcelRunner;
import com.tobe.excelutils.SelectSQL;
import com.tobe.excelutils.handler.BeanHandler;
import com.tobe.excelutils.handler.BeanListHandler;

public class TestExcelUtils {
	
	public static void main(String[] args) throws Exception {
		
		SelectSQL sql = new SelectSQL();
//		sql.ignore("name").ignore("start");
//		sql.select("singledesc");
		sql.where("name", "单笔充值");
		
		ExcelRunner runner = new ExcelRunner("/商业活动.xlsx");
		
		ActivityVO vo = runner.query(sql, new BeanHandler<ActivityVO>(ActivityVO.class));
		
		runner = new ExcelRunner("/商业活动.xlsx");
		
		List<ActivityVO> query = runner.query(sql, new BeanListHandler<ActivityVO>(ActivityVO.class));
		
		System.out.println("");
	}

}
