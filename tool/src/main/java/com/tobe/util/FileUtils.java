package com.tobe.util;

import java.io.File;

/**
 * 文件工具类
 *@author TOBE
 *
 */
public class FileUtils {
	
	/**
	 * 递归查找,忽略文件名大小写
	 * @param path 要查找的文件路径,传入文件夹则递归查找
	 * @param name 要查找的文件名
	 * @return 没有找到直接
	 */
	public static File recursionFindFile(String path, String name){
		    File file = new File(path);
	        if (file.isDirectory()) {
	            File[] children = file.listFiles();//将会调用native代码,没有该文件夹,会返回空
	            if (children != null && children.length > 0) {
	                for (File child : children) {
	                    if (child.isDirectory()) {
	                    	//递归查找
	                    	recursionFindFile(child.getAbsolutePath(), name);
	                    } else {
	                    	
	                    	if(child.getName().equalsIgnoreCase(name)){
	                    		return child;
	                    	}
	                    }
	                }
	            }
	        }
	        
	        //到这里,表示没有查找到文件
//	        return new File(path + "/" + name);
	        return null;
	}
	
	public static File recursionFindFolder(String path, String name){
		File file = new File(path);
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (children != null) {
				for (File child : children) {
					if (child.isDirectory()) {
						if(child.getName().equalsIgnoreCase(name)){
							return child;
						}else{
							recursionFindFolder(child.getAbsolutePath(), name);
						}
					} else {
//						if(child.getName().equalsIgnoreCase(name)){
//							return child;
//						}
					}
				}
			}
		}
//		return new File(path + "/" + name.replace(".", "/"));
		return null;
	}


}
