package com.tobe.main;

import java.io.File;
import java.io.FileFilter;

import javax.swing.tree.DefaultMutableTreeNode;

import com.tobe.ui.filters.Filters;
import com.tobe.ui.panel.TreePanel;
import com.tobe.ui.tree.MTreeNode;

/**
 * 各种操作都在这里处理
 *
 */
public class Manager {
	private static Manager ins = new Manager();
	
	public static Manager getIns(){
		return ins;
	}

	private MTreeNode currentNode ;

	public void loadMsgFiles(String path){
		//递归文件夹,创建节点
		File file = new File(path);
		if(!file.exists()){
			//TODO logging panel
			return;
		}
		
		loadDirectory(path, Filters.DIR_FILTER);
		loadDirectory(path, Filters.XML_FILTER);
	}
	
	/**
	 * 递归加载文件夹下面的文件,并创建treeNode节点
	 * @param path
	 * @param fileFilter
	 */
	 private void loadDirectory(String path, FileFilter fileFilter) {
		   if(currentNode == null){
			   currentNode = (MTreeNode) getTreePanel().getmModel().getRoot();
		   }
	        File file = new File(path);
	        if (file.isDirectory()) {
	            File[] children = file.listFiles(fileFilter);
	            if (children != null) {
	                for (File child : children) {
	                    if (child.isDirectory()) {
	                    	MTreeNode node = new MTreeNode(child.getAbsolutePath().substring(path.length() + 1));
	                        currentNode.add(node);
	                        currentNode = node;
	                        loadDirectory(child.getAbsolutePath(), Filters.DIR_XML_FILTER);

	                    } else {
//	                        root.add(new DefaultMutableTreeNode(child.getAbsolutePath().substring(rootPath.length() + 1)));

	                        currentNode.add(new MTreeNode(child.getAbsolutePath().substring(path.length() + 1)));
	                    }
	                }
	                currentNode = (MTreeNode) currentNode.getParent();
	            }
	        }
	 }

	
	
	//包含各种面板的引用,统一写在最下面
	private TreePanel treePanel;
	public TreePanel getTreePanel() {
		return treePanel;
	}
	public void setTreePanel(TreePanel treePanel) {
		this.treePanel = treePanel;
	}
}
