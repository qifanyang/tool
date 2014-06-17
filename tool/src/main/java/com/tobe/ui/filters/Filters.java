package com.tobe.ui.filters;

import java.io.File;
import java.io.FileFilter;

public class Filters {
	
	 public static final FileFilter DIR_FILTER = new FileFilter() {
	        public boolean accept(File f) {
	            return f.isDirectory();
	        }
	 };
	 
	 public static final FileFilter XML_FILTER = new FileFilter() {
		 public boolean accept(File f) {
			 return f.getAbsolutePath().endsWith(".xml");
		 }
	 };
	 
	 public static final FileFilter DIR_XML_FILTER = new FileFilter() {
		 public boolean accept(File f) {
			 return f.isDirectory() || f.getAbsolutePath().endsWith(".xml");
		 }
	 };

}
