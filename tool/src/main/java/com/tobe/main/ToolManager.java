package com.tobe.main;

import java.io.File;
import java.io.FileFilter;

import com.tobe.ui.filters.Filters;
import com.tobe.ui.panel.TreePanel;
import com.tobe.ui.tree.MTreeNode;

/**
 * 各种操作都在这里处理
 *
 */
public class ToolManager {
	private static ToolManager me = new ToolManager();
	
	public static ToolManager getMe(){
		return me;
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
	 * 
	 * 修改:修改为先扫描文件下下面的文件,创建文件节点,然后再创建树图形
	 * @param path
	 * @param fileFilter
	 */
	private void loadDirectory(String path, FileFilter fileFilter) {
		if (currentNode == null) {
			// 开始构建树形结构,当前节点为根节点
			currentNode = (MTreeNode) getTreePanel().getmModel().getRoot();
		}

		File file = new File(path);
		if (file.isDirectory()) {
			File[] children = file.listFiles(fileFilter);
			if (children != null) {
				for (File child : children) {
					if (child.isDirectory()) {
						MTreeNode node = new MTreeNode(child.getAbsolutePath().substring(path.length() + 1));
						node.put("file", child);
						currentNode.add(node);
						//如果加载的是文件夹,则修改当前文件节点为新建的节点,相当于当前节点等级下降一层
						currentNode = node;
						loadDirectory(child.getAbsolutePath(),Filters.DIR_XML_FILTER);
						currentNode = (MTreeNode) currentNode.getParent();//退出递归的时候,上升当前节点层数

					} else {
						// root.add(new
						// DefaultMutableTreeNode(child.getAbsolutePath().substring(rootPath.length()
						// + 1)));
						MTreeNode mTreeNode = new MTreeNode(child.getAbsolutePath().substring(path.length() + 1));
						mTreeNode.put("file", child);
						currentNode.add(mTreeNode);
					}
				}
			}
		}else{
			//必须为目录
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
