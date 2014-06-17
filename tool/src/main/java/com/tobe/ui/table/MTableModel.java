package com.tobe.ui.table;

import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author yangqifan
 */
public class MTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Object[][] data = new Object[10][3];//显示各种对象,所以用objec

	public MTableModel(){
		data[2][2] = "sdfsdf";
		data[2][0] = 1;
	}
	
	@Override
	public int getColumnCount() {
		return data[0].length;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
//		System.out.println(row + ":" + col);
		return data[row][col];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
	/**
	 * 表格失去焦点时,会调用这个方法,应在这里改变model的值,然后getValueAt才会取得最新的值
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//		data[rowIndex][columnIndex] = Integer.parseInt((String) aValue);
		//TODO 这里还可可以更改到数据库或文件中
	}
}
