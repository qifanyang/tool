package com.tobe.excelutils.handler;

import com.tobe.excelutils.BasicRowProcessor;
import com.tobe.excelutils.ExcelResultSet;
import com.tobe.excelutils.RowProcessor;

public class ArrayHandler implements ExcelSetHandler<Object[]> {

	static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();

	private static final Object[] EMPTY_ARRAY = new Object[0];

	/**
	 * The RowProcessor implementation to use when converting rows into arrays.
	 */
	private final RowProcessor convert;

	/**
	 * Creates a new instance of ArrayHandler using a
	 * <code>BasicRowProcessor</code> for conversion.
	 */
	public ArrayHandler() {
		this(ROW_PROCESSOR);
	}

	public ArrayHandler(RowProcessor convert) {
		super();
		this.convert = convert;
	}

	 /**
     * Places the column values from the first row in an <code>Object[]</code>.
     * @param rs <code>ResultSet</code> to process.
     * @return An Object[]. If there are no rows in the <code>ResultSet</code>
     * an empty array will be returned.
     *
     */
	@Override
	public Object[] handle(ExcelResultSet rs) throws Exception {
		 return rs.next() ? this.convert.toArray(rs) : EMPTY_ARRAY;
	}

}
