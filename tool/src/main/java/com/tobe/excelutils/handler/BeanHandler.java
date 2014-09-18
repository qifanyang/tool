package com.tobe.excelutils.handler;


import com.tobe.excelutils.ExcelResultSet;
import com.tobe.excelutils.RowProcessor;

public class BeanHandler<T> implements ExcelSetHandler<T> {

	private final Class<T> type;
	
	 private final RowProcessor convert;

	public BeanHandler(Class<T> type) {
		this.type = type;
		this.convert = ArrayHandler.ROW_PROCESSOR;
	}

	@Override
	public T handle(ExcelResultSet rs) throws Exception {
		return rs.next() ? this.convert.toBean(rs, this.type) : null;
	}

}
