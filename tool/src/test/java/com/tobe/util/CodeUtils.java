package com.tobe.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CodeUtils {

	private String sigleLineNoteRegex = "\\s*[//]{1}";//任意空格+一个'//'
	private String mutilLineNoteRegex = "\\s*[/**]{1}";//任意空格+一个'/**'  不要用多行注释
	
	/**
	 * 根据方法名字提取方法体代码,只能提取无参方法代码
	 * @param path 类源文件绝对路径
	 * @param methodName
	 * @return
	 */
	public static String extractMethodCode(String path, String methodName){
		String result = "";
		//方法正则表达式,方法名+任意个空格+一个'('+ 任意个空格 + 一个')'
		String regex = methodName + "\\s*[(]{1}\\s*[)]{1}";
		StringBuilder builder = new StringBuilder();
		try {
			FileInputStream in = new FileInputStream(path);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\r\n");//readLine()方法返回的String不包含任何终止符号
			}
			String code = builder.toString();
			builder.setLength(0);
			
			Scanner scanner = new Scanner(code);
			scanner.useDelimiter(regex);
			String halfcode ;
			if(scanner.hasNext()){
				scanner.next();
				halfcode = scanner.next();
				//因为代码中有汉字.无法使用bytes遍历,所以使用reader遍历
				InputStreamReader innderReader = new InputStreamReader(new ByteArrayInputStream(halfcode.getBytes()));
				boolean isbegin = false;
				boolean isneedCount = true;//引号中不需要计数
				int c = 0;//读取字符
				int count = 0;//用于"{"计数.当为0的时候表示方法读取完毕,防止在字符串中使用了"}", 字符串中的{}不计数
				int start = 0;
				int end = 0;//当前字符位置
				while((c = innderReader.read()) != -1){
					char ch = (char)c;
					if(ch == '"'){
						isneedCount = !isneedCount;
					}
					if(ch == '{'){
						isbegin = true;
						
						if(isneedCount)
						count++;
						
						if(!isbegin){//记录第一个"{"位置
							start = end;
						}
					}
					
					if(ch == '}'){
						if(isneedCount)
						count--;
					}
					
//					if(isbegin){
//						builder.append(ch);
//					}
					if(isbegin && count == 0){
						//提取完毕
						result = halfcode.substring(start, end+1);
//						System.out.println("+++++++++++++++");
//						System.out.println(result);
//						System.out.println(builder.toString());
						break;
					}
					end++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return methodName +"()"+ result;
	}
}
