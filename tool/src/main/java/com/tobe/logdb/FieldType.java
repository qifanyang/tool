package com.tobe.logdb;

/**
 * MySQL数据类型
 */
public enum FieldType {
	DEFAULT,//用于注解默认值,不对应mysql数据类型
	TINYINT,
	SMALLINT,
	INT,
	BIGINT,
	FLOAT,
	DOUBLE,
	VARCHAR,
	TEXT,
	LONGTEXT,
	DATETIME;
	
}
