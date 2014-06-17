package com.tobe.ui.tree;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.tobe.project.IFile;

public class MTreeNode extends DefaultMutableTreeNode implements IFile {

	
	private static final long serialVersionUID = 1L;
	private Map<String, Object>  map = new HashMap<String, Object>();
	public MTreeNode(String substring) {
		super(substring);
	}

	/**
     * 完全展开一棵树或关闭一棵树
     * @param tree JTree
     * @param parent 父节点
     * @param expand true 表示展开，false 表示关闭
     */
    public void expandAll(JTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();

        if (node.getChildCount() > 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }
    
    public Object get(String key){
    	return map.get(key);
    }
    
	public void put(String key, Object obj){
    	map.put(key, obj);
    }

	private File getFile(){
		return (File) get("file");
	}
	
	@Override
	public String getName() {
		return getFile().getName();
	}

	@Override
	public String getFullPath() {
		return getFile().getAbsolutePath();
	}

	@Override
	public boolean delete() {
		return getFile().delete();
	}
}
