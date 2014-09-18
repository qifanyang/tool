package com.tobe.project.impl;

import java.io.File;
import java.io.FileOutputStream;

import com.tobe.project.IFolder;
import com.tobe.util.FileUtils;


public class Folder implements IFolder {

	/**
	 * 文件夹对应的file
	 */
	protected File root = null;
	
	public Folder(File file) {
		if(file.exists() && !file.isDirectory()){
			throw new RuntimeException("file 必须是目录, 不可以是文件!");
		}
		this.root = file;
	}
	
	public Folder(String path){
		File file = new File(path);
		if(file.exists() && !file.isDirectory()){
			throw new RuntimeException("file 必须是目录, 不可以是文件!");
		}
		this.root = file;
	}
	
	@Override
	public File getFile(String name) {
		File file = FileUtils.recursionFindFile(root.getAbsolutePath(), name);
//		return new MFile(file);
		return file;
	}

	@Override
	public IFolder getFolder(String name) {
		File file = FileUtils.recursionFindFolder(root.getAbsolutePath(), name);
		return null == file ? null : new Folder(file);
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
	public File createSubFile(String name, String content) {
		File file = new File(root.getAbsolutePath() + File.separator + name);
		try {
			if(!file.exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return new MFile(file);
		return file;
	}
	
	@Override
	public File createSubFile(String name) {
		File file = new File(root.getAbsolutePath() + File.separator + name);
		try {
			if(!file.exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return new MFile(file);
		return file;
	}

	@Override
	public IFolder createSubFolder(String name) {
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
	public String getAbsolutePath() {
		return root.getAbsolutePath();
	}

	@Override
	public boolean create() {
		if(!root.exists()){
			//创建目录包括父目录
			root.mkdirs();
		}
		return true;
	}

}
