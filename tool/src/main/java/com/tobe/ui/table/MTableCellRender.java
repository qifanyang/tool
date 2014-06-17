package com.tobe.ui.table;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 表格默认单元渲染器使用JLabel,
 * public class DefaultTableCellRenderer extends JLabel implements TableCellRenderer, Serializable{
 * ...
 * }
 * 自定义渲染器使用继承,也可以直接返回一个对象,但要缓存,否则每次都创建一个影响效率
 * @author yangqifan
 */
public class MTableCellRender implements TableCellRenderer {

	/**
	 * 返回用于绘制单元格的组件。此方法用于在绘制前适当地配置渲染器
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return null;
	}

	
	
}
