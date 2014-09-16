package com.tobe.excelutils.handler;

import java.util.List;

import com.tobe.excelutils.BasicRowProcessor;
import com.tobe.excelutils.ExcelResultSet;
import com.tobe.excelutils.RowProcessor;

public class BeanListHandler<T> implements ExcelSetHandler<List<T>> {


	private final Class<T> type;
	
	private final RowProcessor convert;

	public BeanListHandler(Class<T> type) {
		this.type = type;
		this.convert = new BasicRowProcessor();
	}

	
	@Override
	public List<T> handle(ExcelResultSet rs) throws Exception {
		return this.convert.toBeanList(rs, type);
	}

}
