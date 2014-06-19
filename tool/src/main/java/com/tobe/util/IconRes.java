package com.tobe.util;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * 所有的icon都从这里取,没有的继续添加,窗口的图标为image也从这里取
 */
public class IconRes {

	/**
	 * 程序主图标
	 */
	public static final Image IMAGE_APP = Toolkit.getDefaultToolkit().getImage(IconRes.class.getResource("res/bird48.png"));
	
	public static final Icon question_icon = new ImageIcon(IconRes.class.getResource("res/question-balloon_blue.png"));
}
