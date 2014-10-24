package com.tobe.util;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public class TestCodeUtil extends TestCase{
	
	public void testOne(){
		String path = "E:/sourcetreerepo/tool/tool/src/test/java/com/tobe/util/SomeBean.java";
		String code = CodeUtils.extractMethodCode(path, "validate");
//		System.out.println(code);
		System.out.println("=======================");
		code = CodeUtils.extractMethodCode(path, "validate");
		System.out.println(code);
		try {
			Method method = SomeBean.class.getMethod("validate");
			System.out.println(method.toString());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
