package com.tobe.project;

public interface IFolder extends IFile{

	//Returns a handle to the file with the given name in this folder.
	 IFile	getFile(String name);
	 
	 // Returns a handle to the folder with the given name in this folder.
	 IFolder    getFolder(String name); 
	 
	 boolean exists();
	 
	 IFile createFile(String name, String content);
	 
	 IFolder createFolder(String name);
}
