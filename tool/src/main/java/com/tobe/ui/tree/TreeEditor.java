package com.tobe.ui.tree;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * 
 * @author yangqifan
 */
public class TreeEditor extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	
	public TreeEditor(){
		
	}

	private JTree createTable() {
			DefaultMutableTreeNode      root = new DefaultMutableTreeNode("JTree");
	        DefaultMutableTreeNode      parent;

	        parent = new DefaultMutableTreeNode("colors");
	        root.add(parent);
	        parent.add(new DefaultMutableTreeNode("blue"));
	        parent.add(new DefaultMutableTreeNode("violet"));
	        parent.add(new DefaultMutableTreeNode("red"));
	        parent.add(new DefaultMutableTreeNode("yellow"));

	        parent = new DefaultMutableTreeNode("sports");
	        root.add(parent);
	        parent.add(new DefaultMutableTreeNode("basketball"));
	        parent.add(new DefaultMutableTreeNode("soccer"));
	        parent.add(new DefaultMutableTreeNode("football"));
	        parent.add(new DefaultMutableTreeNode("hockey"));

	        parent = new DefaultMutableTreeNode("food");
	        parent.setAllowsChildren(true);
	        root.add(parent);
	        DefaultMutableTreeNode aa = new DefaultMutableTreeNode("hot dogs");
	        aa.setAllowsChildren(true);
//	        parent.add(aa);
//	        parent.add(new DefaultMutableTreeNode("pizza"));
//	        parent.add(new DefaultMutableTreeNode("ravioli"));
//	        parent.add(new DefaultMutableTreeNode("bananas"));
		JTree t = new JTree(root, true);
		t.setCellRenderer(new IconTreeCellRender());
		//当显示的是数字,无法获取其editor,会调用object的editor
//		t.setDefaultEditor(Object.class, new MTableCellEditor());
		
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
				TreeEditor ins = new TreeEditor();
				ins.setSize(WIDTH, HEIGHT);
				ins.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JTree tree = ins.createTable();
				ins.add(new JScrollPane(tree), BorderLayout.CENTER);
				ins.setTitle("树测试");
				ins.setLocationRelativeTo(null);
				ins.setVisible(true);
			}
		});
	}
}
