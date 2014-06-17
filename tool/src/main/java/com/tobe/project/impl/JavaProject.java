package com.tobe.project.impl;

import java.io.File;

import com.tobe.project.IFile;
import com.tobe.project.IFolder;
import com.tobe.project.IProject;
import com.tobe.util.DevUtils;

public class JavaProject implements IProject {

	private Folder root;
	
	public JavaProject(String path){
		root = new Folder(path);
	}
	
	@Override
	public String getName() {
		return root.getName();
	}

	@Override
	public IFile getFile(String name) {
		File file = DevUtils.recursionFind(root.getFullPath(), name);
		return new MFile(file);
	}

	@Override
	public IFolder getFolder(String name) {
		String replace = name.replace(".", "/");
		Folder folder = new Folder(root.getFullPath() + "/" + replace);
		return folder;
	}

}
