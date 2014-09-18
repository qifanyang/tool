package com.tobe.project;

import java.io.File;

public interface IProject {
	
	String getName();
	
	/**
	 * 获得一个程序文件
	 * @param name xx.yy.zz.Mm.java
	 * @return
	 */
	File getFile(String name);
	
	/**
	 * 获得一个包
	 * @param name xx.yy.zz
	 * @return
	 */
	IFolder	getPackage(String name);
	
	/**
	 * 查询源代码目录路径:
	 * maven项目  /src/main/java
	 * eclipse默认项目 /src
	 * @return
	 */
	IFolder getSourceCode();
	
}
