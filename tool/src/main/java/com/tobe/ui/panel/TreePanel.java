package com.tobe.ui.panel;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.tobe.actions.ActionContext;
import com.tobe.actions.GenCodeDialogAction;
import com.tobe.config.CommCofig;
import com.tobe.ui.tree.IconTreeCellRender;
import com.tobe.ui.tree.MTreeModel;
import com.tobe.ui.tree.MTreeNode;

/**
 * 树形面板
 */
public class TreePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTree mTree ;
	private TreeModel mModel;
	
	public TreePanel(){
		
		MTreeNode  root = new MTreeNode("Messages");
		mModel = new MTreeModel(root);
		mTree = new JTree(mModel);
//		mTree.setRootVisible(false);
		mTree.setCellRenderer(new IconTreeCellRender());
		//TODO 修改icon图标
		
		
		mTree.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
			    int x = e.getX();
			    int y = e.getY();
			    TreePath closestPathForLocation = mTree.getClosestPathForLocation(x, y);
			    // if
			    // (!closestPathForLocation.getLastPathComponent().toString().equalsIgnoreCase(noFlag))
			    // {
			    mTree.setSelectionPath(closestPathForLocation);
			    // }
			    MTreeNode lastPathComponent = (MTreeNode) closestPathForLocation.getLastPathComponent();

//			    refreshItem.setEnabled(true);
//				lastModifyItem.setEnabled(true);
//				renameMenuItem.setEnabled(true);
////				delMenuItem.setEnabled(true);
//			    // 弹出菜单过滤，当为根节点时，不可编辑，不可重命名，不可删除
//			    if (lastPathComponent.getParent() == null) {
//					// editContentMenuItem.setEnabled(false);
//					renameMenuItem.setEnabled(false);
//	//				delMenuItem.setEnabled(false);
//					refreshItem.setEnabled(true);
//					lastModifyItem.setEnabled(true);
//			    } else {
//					// editContentMenuItem.setEnabled(true);
//					renameMenuItem.setEnabled(true);
//	//				delMenuItem.setEnabled(true);
//			    }
//
//			    if(lastPathComponent.isLeaf()){
//			    	lastModifyItem.setEnabled(false);
//			    }else {
//					lastModifyItem.setEnabled(true);
//				}
			    // 当为正文节点时，不可添加目录，不可添加正文
//			    if (lastPathComponent.getChildCount() <= 0 && !lastPathComponent.toString().equalsIgnoreCase(CustomTreeNode.noFlag)) {
//				addCatalogMenuItem.setEnabled(false);
//				addContentMenuItem.setEnabled(false);
//				// editContentMenuItem.setEnabled(true);
//				renameMenuItem.setEnabled(true);
//				delMenuItem.setEnabled(true);
//			    } else {
//				addCatalogMenuItem.setEnabled(true);
//				addContentMenuItem.setEnabled(true);
//			    }

			    // 弹出菜单过滤，当为noflag节点时,do nothing
//			    if (lastPathComponent.toString().equalsIgnoreCase(CustomTreeNode.noFlag)) {
//				addCatalogMenuItem.setEnabled(false);
//				addContentMenuItem.setEnabled(false);
//				 editContentMenuItem.setEnabled(false);
//				renameMenuItem.setEnabled(false);
//				delMenuItem.setEnabled(false);
//			    }

			    // treePopupMenu.setLocation(e.getLocationOnScreen());//JDK5
			    // no method e.getLocationOnScreen()
//			    Point windowPos = mTree.getParent().getLocationOnScreen();
			    Point windowPos = mTree.getLocationOnScreen();
			    JPopupMenu popupMenu = PopupMenuFactory.getPopupMenu(mTree, lastPathComponent);
			    popupMenu.setLocation((int) windowPos.getX() + e.getX(), (int) windowPos.getY() + e.getY());
			    popupMenu.setVisible(true);
			}
		   }
		});
		
		
		setLayout(new BorderLayout());
		add(new JScrollPane(mTree), BorderLayout.WEST);
		
	}

	public JTree getmTree() {
		return mTree;
	}

	public void setmTree(JTree mTree) {
		this.mTree = mTree;
	}

	public TreeModel getmModel() {
		return mModel;
	}

	public void setmModel(TreeModel mModel) {
		this.mModel = mModel;
	}

	/**
	 * 
	 *根据树形节点生成弹出菜单
	 */
	static class PopupMenuFactory{
		static JPopupMenu leafPop;//缓存
		static JPopupMenu noleafPop;
		
		static JPopupMenu getPopupMenu(JTree mTree, MTreeNode node){
			//创建弹出菜单
			JPopupMenu popupMenu = null;
			if(node.isLeaf()){
				if(leafPop != null){
					popupMenu = leafPop;
				}else {
					popupMenu = new JPopupMenu();
					final JMenuItem genCodeItem = new JMenuItem("生成代码");
					final JMenuItem refreshItem = new JMenuItem("刷新文件");
					popupMenu.add(genCodeItem);
					popupMenu.add(refreshItem);
					leafPop = popupMenu;
					popupMenu.setInvoker(mTree);
					popupMenu.setBorderPainted(true);
					
					GenCodeDialogAction action = new GenCodeDialogAction();
					ActionContext context = new ActionContext();
					context.setNode(node);
					context.setConfig(new CommCofig());//TODO commconfig这里是配置的
					action.setContext(context);
					genCodeItem.addActionListener(action);
					
				}
				
			}else {
				if(noleafPop != null){
					popupMenu = noleafPop;
				}else {
					popupMenu = new JPopupMenu();
					final JMenu lastModifyItem = new JMenu("最近修改");
					final JMenuItem todayModifyItem = new JMenuItem("今天内");
					final JMenuItem weekModifyItem = new JMenuItem("一周内");
					final JMenuItem monthModifyItem = new JMenuItem("一个月内");
					lastModifyItem.add(todayModifyItem);
					lastModifyItem.add(weekModifyItem);
					lastModifyItem.add(monthModifyItem);
					popupMenu.add(lastModifyItem);
					noleafPop = popupMenu;
					popupMenu.setInvoker(mTree);
					popupMenu.setBorderPainted(true);
				}
			}
			return popupMenu;
		}
	}
}
