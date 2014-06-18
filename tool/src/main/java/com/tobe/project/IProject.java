package com.tobe.project;

public interface IProject {
	
	String getName();
	
	/**
	 * 获得一个程序文件
	 * @param name xx.yy.zz.Mm.java
	 * @return
	 */
	IFile getFile(String name);
	
	/**
	 * 获得一个包
	 * @param name xx.yy.zz
	 * @return
	 */
	IFolder	getPackage(String name);
	
	IFolder getSrc();
	
}
