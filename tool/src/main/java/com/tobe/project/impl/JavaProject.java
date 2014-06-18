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
		File file = new File(getSrc().getFullPath()+"/"+ name);
		return new MFile(file);
	}

	@Override
	public IFolder getPackage(String name) {
		String replace = name.replace(".", "/");
		Folder folder = new Folder(root.getFullPath() + "/src/" + replace);
		return folder;
	}

	@Override
	public IFolder getSrc() {
		Folder folder = new Folder(root.getFullPath() + "/src");
		return folder;
	}

}
