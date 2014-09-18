package com.tobe.excelutils.handler;

import java.util.Map;

import com.tobe.excelutils.ExcelResultSet;
import com.tobe.excelutils.RowProcessor;

public class MapListHandler extends AbstractListHandler<Map<String, Object>> {

	 /**
     * The RowProcessor implementation to use when converting rows
     * into Maps.
     */
    private final RowProcessor convert;

    /**
     * Creates a new instance of MapListHandler using a
     * <code>BasicRowProcessor</code> for conversion.
     */
    public MapListHandler() {
        this(ArrayHandler.ROW_PROCESSOR);
    }

    /**
     * Creates a new instance of MapListHandler.
     *
     * @param convert The <code>RowProcessor</code> implementation
     * to use when converting rows into Maps.
     */
    public MapListHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }

	@Override
	protected Map<String, Object> handleRow(ExcelResultSet rs) throws Exception {
		 return this.convert.toMap(rs);
	}

	
}
