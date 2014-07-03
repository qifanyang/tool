package com.tobe.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class TestReadValidate extends TestCase{
	
	public void testRead() throws IOException{
		//C:/Users/Administrator/git/tool/tool/src/test/java/com/tobe/util/SomeBean.java
		//使用正则表达式,匹配validate方法
		StringBuilder builder = new StringBuilder();
		try {
			FileInputStream in = new FileInputStream("C:/Users/Administrator/git/tool/tool/src/test/java/com/tobe/util/SomeBean.java");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\r\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String code = builder.toString();
//		System.out.println(code);
		
//		code = "@Override\r\n public boolean validate() { \r\n";
//		() {
//			//该方法可以修改,生成bean.如过该bean存在,保留该方法
//			System.out.println("{}bean valiate run.....");
//			return false;
//		}
//		
//
//	}
		
//		Pattern p = Pattern.compile("@Override\r*\n*.*validate", Pattern.DOTALL);
//		Matcher m = p.matcher(code);
		
		Scanner scanner = new Scanner(code);
//		scanner.useDelimiter("@Override\r\n*.*validate\\s*()?");
		scanner.useDelimiter("validate\\s*[(]{1}\\s*[)]{1}");//方法名字, 参数
		String halfcode ;
		if(scanner.hasNext()){
			scanner.next();
			halfcode = scanner.next();
			System.out.println(halfcode);
			
			InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(halfcode.getBytes()));
			boolean isbegin = false;
			boolean isneedCount = true;
			int c = 0;//读取字符
			int count = 0;//用于"{"计数.当为0的时候表示方法读取完毕,防止在字符串中使用了"}", 字符串中的{}不计数
			int start = 0;
			int end = 0;//当前字符位置
			while((c = reader.read()) != -1){
				char ch = (char)c;
				if(ch == '"'){
					isneedCount = !isneedCount;
				}
				if(ch == '{'){
					if(isneedCount)
					count++;
					
					if(!isbegin){//记录"{"位置
						start = end;
					}
					isbegin = true;
				}
				
				if(ch == '}'){
					if(isneedCount)
					count--;
				}
				
				if(isbegin && count == 0){
					//提取完毕
					System.out.println("+++++++++++++++");
					System.out.println(halfcode.substring(start, end+1));
					break;
				}
				end++;
			}
		}
		
	}

}
