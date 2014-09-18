package com.tobe.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.TreePath;

import com.tobe.ui.panel.TreePanel;
import com.tobe.ui.tree.MTreeNode;

/**
 * =刷新=生成全部
 * 
 * 左边消息文件展示面板
 */
public class LeftPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public LeftPanel() {
		setBorder(null);
		initCompent();
	}

	
	private void initCompent() {
//		JSplitPane spane = new JSplitPane();
//		spane.setBorder(null);
		setBorder(new EtchedBorder());
		setLayout(new BorderLayout());
		TreePanel treePanel = new TreePanel();
		treePanel.setBorder(null);
//		treePanel.setSize(200, 600);
//		treePanel.setPreferredSize(new Dimension(230, 600));
//		spane.setLeftComponent(treePanel);
		
		JPanel optpanel = new JPanel();
		JButton freshBtn = new JButton("刷新");
		JButton buildAllBtn = new JButton("生成全部消息");
		
		optpanel.add(freshBtn);
		optpanel.add(buildAllBtn);
		
		add(optpanel, BorderLayout.PAGE_START);
		
		
		add(treePanel, BorderLayout.CENTER);
		
		//统一设置面板引用
		ToolManager.getMe().setTreePanel(treePanel);
		
		
		//默认加载资源
		ToolManager.getMe().loadMsgFiles("D:/resource/resource/message");
		MTreeNode root = (MTreeNode) treePanel.getmModel().getRoot();
		root.expandAll(treePanel.getmTree(), new TreePath(root), true);
		treePanel.updateUI();
	}
	
	

}
