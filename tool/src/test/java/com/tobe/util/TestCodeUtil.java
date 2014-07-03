package com.tobe.util;

import junit.framework.TestCase;

public class TestCodeUtil extends TestCase{
	
	public void testOne(){
		String path = "C:/Users/Administrator/git/tool/tool/src/test/java/com/tobe/util/SomeBean.java";
		String code = CodeUtils.extractMethodCode(path, "validate");
		System.out.println(code);
		System.out.println("=======================");
		code = CodeUtils.extractMethodCode(path, "testone");
		System.out.println(code);
	}

}
