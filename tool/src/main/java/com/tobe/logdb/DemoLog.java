package com.tobe.logdb;

import java.util.Date;

public class DemoLog extends AutoLog {
	
	//表示该字段忽略,创建表不会创建该字段,插入值也不会
	@Property(ignore=true)
	public long uid = 1000;

	@Property(size=20)
	public String name;
	
	//没有使用注解, 则数据库字段名字为字段名, 数据类型为java对应的mysql数据类型
	public int age;
	
	//String没有设置注解默认 varchar(50), 更改大小用注解size属性
	//String需要用TEXT数据库字段, 使用type=FieldType.LONGTEXT
	@Property(type=FieldType.LONGTEXT)
	public String desc;
	
	public Date date = new Date();
	
	@Override
	public RollingPeriod rollingPeriod() {
		return RollingPeriod.DAY;
	}

}
