package com.tobe.ui.tree;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * 修改默认Render的Icon
 * @author yangqifan
 */
public class IconTreeCellRender extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private Icon leafIcon;
	private Icon openIcon;
	private Icon closedIcon;
	
	public IconTreeCellRender(){
		leafIcon = new ImageIcon(getClass().getResource("application_osx_start.png"));
		openIcon = new ImageIcon(getClass().getResource("application_osx_add.png"));
	}
	
	@Override
	public Icon getLeafIcon() {
		return leafIcon;
	}

	@Override
	public Icon getOpenIcon() {
		return openIcon;
	}
	
	@Override
	public Icon getClosedIcon() {
		return openIcon;
	}
}
