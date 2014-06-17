package com.tobe.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * 单元格编辑器
 * 默认采用public class DefaultCellEditor extends AbstractCellEditor  implements TableCellEditor, TreeCellEditor { 
 * ...
 * }
 * 默认的是树和表格通用,所以自己实现可以不实现TreeCellEditor
 * 默认根据表格值类型会返回:JTextField,JCheckBox,JComboBox
 * 不同的列可能有不同的cellEditor,这里只是个模版
 * @author yangqifan
 */
public class MTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JColorChooser colorChooser;
	private JDialog colorDialog;
	
	public MTableCellEditor(){
		panel = new JPanel();
		colorChooser = new JColorChooser();
		colorDialog = JColorChooser.createDialog(null, "Planet Color", false, colorChooser,
		         EventHandler.create(ActionListener.class, this, "stopCellEditing"),
		         EventHandler.create(ActionListener.class, this, "cancelCellEditing"));
	}

	@Override
	public Object getCellEditorValue() {
		return colorChooser.getColor();
	}

	/**
	 * 返回自定义表格编辑器,当双击某单元格时调用该方法
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
		colorChooser.setColor(Color.YELLOW);
		return panel;
	}
	
	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		colorDialog.setVisible(true);
		return true;
	}
	
	@Override
	public boolean stopCellEditing() {
		colorDialog.setVisible(false);
		return super.stopCellEditing();
	}

	@Override
	public void cancelCellEditing() {
		colorDialog.setVisible(false);
		super.cancelCellEditing();
	}
	
	/**
	 * /**
	 * DefaultCellEditor$1(DefaultCellEditor$EditorDelegate).stopCellEditing() line: 350 [local variables unavailable]	
	 *  Point p = e.getPoint();
            pressedRow = table.rowAtPoint(p);
            pressedCol = table.columnAtPoint(p);
            outsidePrefSize = pointOutsidePrefSize(pressedRow, pressedCol, p);
	 */
	
}
