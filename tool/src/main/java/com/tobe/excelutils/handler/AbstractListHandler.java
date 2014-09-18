package com.tobe.excelutils.handler;

import java.util.ArrayList;
import java.util.List;

import com.tobe.excelutils.ExcelResultSet;


public abstract class AbstractListHandler<T> implements ExcelSetHandler<List<T>> {
	
	@Override
    public List<T> handle(ExcelResultSet rs) throws Exception {
        List<T> rows = new ArrayList<T>();
        while (rs.next()) {
            rows.add(this.handleRow(rs));
        }
        return rows;
    }
	
	protected abstract T handleRow(ExcelResultSet rs) throws Exception;

}
