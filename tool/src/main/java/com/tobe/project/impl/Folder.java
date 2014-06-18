package com.tobe.project.impl;

import java.io.File;
import java.io.FileOutputStream;

import com.tobe.project.IFile;
import com.tobe.project.IFolder;
import com.tobe.util.DevUtils;

public class Folder implements IFolder {

	protected File root = null;
	
	public Folder(File file) {
		this.root = file;
	}
	
	public Folder(String path){
		File file = new File(path);
		this.root = file;
	}
	
	@Override
	public IFile getFile(String name) {
		File file = DevUtils.recursionFindFile(root.getAbsolutePath(), name);
		return new MFile(file);
	}

	@Override
	public IFolder getFolder(String name) {
		File file = DevUtils.recursionFindFolder(root.getAbsolutePath(), name);
		return new Folder(file);
	}

	@Override
	public boolean delete() {
		return root.delete();
	}

	@Override
	public boolean exists() {
		return root.exists();
	}

	@Override
	public IFile createFile(String name, String content) {
		File file = new File(root.getAbsolutePath() + "/" + name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new MFile(file);
	}

	@Override
	public IFolder createFolder(String name) {
		String replace = name.replace(".", "/");
		Folder folder = new Folder(root.getAbsolutePath() + "/" + replace);
		if(!folder.exists()){
			folder.create();
		}
		return folder;
	}

	@Override
	public String getName() {
		return root.getName();
	}

	@Override
	public String getFullPath() {
		return root.getAbsolutePath();
	}

	@Override
	public boolean create() {
		if(!root.exists()){
			root.mkdirs();
		}
		return true;
	}


}
