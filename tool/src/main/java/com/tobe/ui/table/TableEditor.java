package com.tobe.ui.table;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;


/**
 * 
 * @author yangqifan
 */
public class TableEditor extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	
	public TableEditor(){
		
	}

	private JTable createTable() {
		JTable t = new JTable(new MTableModel());
		//当显示的是数字,无法获取其editor,会调用object的editor
		t.setDefaultEditor(Object.class, new MTableCellEditor());
//		t.setDefaultEditor(Integer.class, new MTableCellEditor());
//		t.setDefaultEditor(String.class, new MTableCellEditor());
		
		//TODO 给表格加弹出菜单
		//TODO 表格宽度调整
		return t;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				TableEditor ins = new TableEditor();
				ins.setSize(WIDTH, HEIGHT);
				ins.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JTable createTable = ins.createTable();
				ins.add(new JScrollPane(createTable), BorderLayout.CENTER);
				ins.setTitle("表格测试");
				ins.setLocationRelativeTo(null);
				ins.setVisible(true);
			}
		});
	}
}
