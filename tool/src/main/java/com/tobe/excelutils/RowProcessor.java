package com.tobe.excelutils;

import java.util.List;
import java.util.Map;

public interface RowProcessor {

	 
    Object[] toArray(ExcelResultSet rs) throws Exception;

   
    <T> T toBean(ExcelResultSet rs, Class<T> type) throws Exception;

   
    <T> List<T> toBeanList(ExcelResultSet rs, Class<T> type) throws Exception;

    
    Map<String, Object> toMap(ExcelResultSet rs) throws Exception;
}
