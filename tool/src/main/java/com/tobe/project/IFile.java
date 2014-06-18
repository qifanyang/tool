package com.tobe.project;

public interface IFile {

	String getName();
	
	String getFullPath();
	
	boolean delete();
	
	boolean create();
	
	boolean exists();
}
