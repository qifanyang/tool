package com.tobe.main;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.tree.TreePath;

import com.tobe.ui.panel.TreePanel;
import com.tobe.ui.tree.MTreeNode;


public class LeftPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public LeftPanel() {
		setBorder(null);
		initCompent();
	}

	
	private void initCompent() {
//		JSplitPane spane = new JSplitPane();
//		spane.setBorder(null);
		setLayout(new BorderLayout());
		TreePanel treePanel = new TreePanel();
		treePanel.setBorder(null);
//		treePanel.setSize(200, 600);
//		treePanel.setPreferredSize(new Dimension(230, 600));
//		spane.setLeftComponent(treePanel);
		
		add(treePanel);
		
		//统一设置面板引用
		ToolManager.getMe().setTreePanel(treePanel);
		
		//默认加载资源
		ToolManager.getMe().loadMsgFiles("D:/resource/resource/message");
		MTreeNode root = (MTreeNode) treePanel.getmModel().getRoot();
		root.expandAll(treePanel.getmTree(), new TreePath(root), true);
		treePanel.updateUI();
	}

}
