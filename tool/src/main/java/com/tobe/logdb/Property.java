package com.tobe.logdb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Property {

	boolean ignore() default false;//不需要记录的字段,换回true
	
	String name() default "";//用string.length()==0判断

	FieldType type() default FieldType.DEFAULT;//没有设置字段类型,返回个默认值,表示不修改字段类型
	
	int size() default -1;
}
