package com.tobe.excelutils.handler;

import com.tobe.excelutils.ExcelResultSet;

public interface ExcelSetHandler<T> {

	T handle(ExcelResultSet rs) throws Exception;
}
