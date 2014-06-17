package com.tobe.main;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.tree.TreePath;

import com.tobe.ui.panel.TreePanel;
import com.tobe.ui.tree.MTreeNode;


public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public MainPanel() {
		setBorder(null);
		initCompent();
	}

	
	private void initCompent() {
		JSplitPane spane = new JSplitPane();
//		spane.setBorder(null);
		TreePanel treePanel = new TreePanel();
		treePanel.setBorder(null);
//		treePanel.setSize(200, 600);
		treePanel.setPreferredSize(new Dimension(230, 600));
		spane.setLeftComponent(treePanel);
		
		add(spane);
		
		//统一设置面板引用
		Manager.getIns().setTreePanel(treePanel);
		
		//TODO test
		Manager.getIns().loadMsgFiles("D:/dev/neworkspace/QMR_Resource/resource/message");
		MTreeNode root = (MTreeNode) treePanel.getmModel().getRoot();
		root.expandAll(treePanel.getmTree(), new TreePath(root), true);
		treePanel.updateUI();
	}

}
