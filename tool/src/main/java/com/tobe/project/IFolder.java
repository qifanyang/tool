package com.tobe.project;

public interface IFolder extends IFile{

	/**
	 * 递归查找文件夹下面指定名字的文件,不区分大小写
	 * @param name
	 * @return
	 */
	 IFile	getFile(String name);
	 
	 // Returns a handle to the folder with the given name in this folder.
	 IFolder    getFolder(String name); 
	 
	 IFile createFile(String name, String content);
	 
	 /**
	  * 支持包名, 路径中的"."会被替代成"/"
	  * @param name
	  * @return
	  */
	 IFolder createFolder(String name);
}
