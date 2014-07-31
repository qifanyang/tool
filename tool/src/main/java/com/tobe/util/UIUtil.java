package com.tobe.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ResourceBundle;


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
	
	static ResourceBundle res = ResourceBundle.getBundle("UI");
	
	/**i18n*/
	public static String getName(String key){
		return res.getString(key);
	}
	
	//常用名字
	
	public static String NAME_OK = getName("ok");//确定
	public static String NAME_CANEL = getName("cancel");//取消
	public static String NAME_YES = getName("yes");//是
	public static String NAME_NO = getName("no");//否
	public static String NAME_AUTO_MAKE = getName("automake");//自动生成
	
}
