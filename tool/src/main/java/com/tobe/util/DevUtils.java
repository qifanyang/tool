package com.tobe.util;

import java.io.File;

public class DevUtils {
	
	/**
	 * 递归查找
	 * @param path
	 * @param name
	 * @return
	 */
	public static File recursionFind(String path, String name){
		    File file = new File(path);
	        if (file.isDirectory()) {
	            File[] children = file.listFiles();
	            if (children != null) {
	                for (File child : children) {
	                    if (child.isDirectory()) {
	                    	recursionFind(child.getAbsolutePath(), name);
	                    } else {
	                    	if(child.getName().equalsIgnoreCase(name)){
	                    		return child;
	                    	}
	                    }
	                }
	            }
	        }
	        return null;
	}


}
