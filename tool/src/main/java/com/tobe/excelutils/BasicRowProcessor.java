package com.tobe.excelutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicRowProcessor implements RowProcessor {

	private static final BeanProcessor defaultConvert = new BeanProcessor();

//	private static final BasicRowProcessor instance = new BasicRowProcessor();

	/**
	 * Use this to process beans.
	 */
	private final BeanProcessor convert;

	/**
	 * BasicRowProcessor constructor. Bean processing defaults to a
	 * BeanProcessor instance.
	 */
	public BasicRowProcessor() {
		this(defaultConvert);
	}

	public BasicRowProcessor(BeanProcessor convert) {
		super();
		this.convert = convert;
	}

	@Override
	public Object[] toArray(ExcelResultSet rs) throws Exception {
		List<String> rsmd = rs.getHeaders();
	    int cols = rsmd.size();
        Object[] result = new Object[cols];

        for (int i = 0; i < cols; i++) {
            result[i] = rs.getObject(i + 1);
        }

        return result;
	}

	@Override
	public <T> T toBean(ExcelResultSet rs, Class<T> type) throws Exception {
		return this.convert.toBean(rs, type);
	}

	@Override
	public <T> List<T> toBeanList(ExcelResultSet rs, Class<T> type)
			throws Exception {
		return this.convert.toBeanList(rs, type);
	}

	@Override
	public Map<String, Object> toMap(ExcelResultSet rs) throws Exception {
		    Map<String, Object> result = new HashMap<String, Object>();
	        List<String> rsmd = rs.getHeaders();
	        int cols = rsmd.size();

	        for (int i = 0; i < cols; i++) {
	            String columnName = rsmd.get(i);
//	            if (null == columnName || 0 == columnName.length()) {
//	              columnName = rsmd.getColumnName(i);
//	            }
	            result.put(columnName, rs.getObject(i));
	        }
	        return result;
	}

}
