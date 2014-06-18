package com.tobe.util;

import java.io.File;

public class DevUtils {
	
	/**
	 * 递归查找
	 * @param path
	 * @param name
	 * @return
	 */
	public static File recursionFindFile(String path, String name){
		    File file = new File(path);
	        if (file.isDirectory()) {
	            File[] children = file.listFiles();
	            if (children != null) {
	                for (File child : children) {
	                    if (child.isDirectory()) {
	                    	recursionFindFile(child.getAbsolutePath(), name);
	                    } else {
	                    	if(child.getName().equalsIgnoreCase(name)){
	                    		return child;
	                    	}
	                    }
	                }
	            }
	        }
	        return new File(path + "/" + name);
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
		return new File(path + "/" + name.replace(".", "/"));
	}


}
