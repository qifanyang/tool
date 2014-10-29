package com.utils;

/**
 * {@link Assert}这是程序运行时断言,状态不对会抛出异常
 * 
 * AssertNotify是用于判断并提示给客户端的断言
 * 
 * @author TOBE
 * 
 */
public abstract class AssertNotify {

	public static void isInOpenRange(int v,int min, int max, String message) {
		if(v < min || v > max){
			throw new AssertException(message);
		}
	}
}
