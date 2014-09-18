//package com.tobe.project.impl;
//
//import java.io.File;
//
//import com.tobe.project.IFile;
//
//public class MFile implements IFile {
//
//	private File file;
//	
//	public MFile(File file) {
//		this.file = file;
//	}
//	
//	@Override
//	public String getName() {
//		return file.getName();
//	}
//
//	@Override
//	public String getFullPath() {
//		return null;
//	}
//
//	@Override
//	public boolean delete() {
//		return false;
//	}
//
//	@Override
//	public boolean create() {
////		if(!file.exists()){
////			file.mkdirs();
////		}
////		file.createNewFile();
//		return false;
//	}
//
//	@Override
//	public boolean exists() {
//		return file.exists();
//	}
//
//
//}
