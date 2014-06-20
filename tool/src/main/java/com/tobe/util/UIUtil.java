package com.tobe.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;


/**
 * 界面显示相关工具方法
 *
 */
public class UIUtil {

	public static Point getScreenCenter(){
		Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Point center = new Point((int)(dim.getWidth()/2), (int)(dim.getHeight()/2));
		return center;
	}
	
	/**
	 * 在setSize之后调用
	 * @param c
	 */
	public static void setCompentToCenter(Component c){
		Dimension dim = c.getSize();
		Point center = new Point((int)(dim.getWidth()/2), (int)(dim.getHeight()/2));
		Point screenCenter = getScreenCenter();
		Point point = new Point((int)(screenCenter.getX() - center.getX()), (int)(screenCenter.getY() - center.getY()));
//		Point point = new Point((int)(screenCenter.getX() - 200), (int)(screenCenter.getY() - 100));
		c.setLocation(point);
	}
}
