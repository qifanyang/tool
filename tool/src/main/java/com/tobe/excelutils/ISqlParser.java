package com.tobe.excelutils;

//暂不支持解析SQL语句,使用JAVA直接构造
public interface ISqlParser {

	public StatementType parse(String sql);
}
