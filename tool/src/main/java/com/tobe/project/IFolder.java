package com.tobe.project;

import java.io.File;

/**
 * File使用不方便,扩展File的Folder,提供在文件夹下面创建文件夹,文件
 *@author TOBE
 *
 */
public interface IFolder {

	
	String getName();

	String getAbsolutePath();

	boolean delete();

	/**
	 * 创建当前文件夹
	 * @return
	 */
	boolean create();

	boolean exists();
	
	//文件夹也具有文件的属性,上面定义文件的操作

	/**
	 * 递归查找文件夹下面指定名字的文件,不区分大小写
	 * 
	 * @param name
	 * @return
	 */
	File getFile(String name);

	/**
	 * 在文件夹下面递归查找指定的文件夹, 忽略名字大小写
	 * 
	 * @param name
	 *            文件名字
	 * @return 查找到的文件夹, 没查到返回null
	 */
	IFolder getFolder(String name);

	/**
	 * 在该文件夹下创建一个文件
	 * 
	 * @param name
	 *            指定文件名
	 * @param content
	 *            文件内容
	 * @return 新建的文件
	 */
	File createSubFile(String name, String content);

	/**
	 * 创建空文件
	 * @param name
	 * @return
	 */
	File createSubFile(String name);
	
	/**
	 * 在该文件夹下创建一个文件夹 支持包名, 路径中的"."会被替代成"/"<br>
	 * 
	 * 例如 :com.game.test <br>
	 * com/game/test<br>
	 * 
	 * @param name
	 *            文件夹名字
	 * @return 新建的文件夹
	 */
	IFolder createSubFolder(String name);
}
