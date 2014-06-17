package com.tobe.project;

public interface IProject {
	
	String getName();
	
	IFile getFile(String name);
	
	IFolder	getFolder(String name);
}
