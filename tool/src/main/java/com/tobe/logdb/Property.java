package com.tobe.logdb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Property {

	/**
	 * 不需要记录的字段,返回true
	 * @return	是否忽略
	 */
	boolean ignore() default false;
	
	/**
	 * 用string.length()==0判断是否设置了名字
	 * @return	字段名字
	 */
	String name() default "";

	/**
	 * 没有设置字段类型,返回个默认值,表示不修改字段类型
	 * @return
	 */
	FieldType type() default FieldType.DEFAULT;
	
	/**
	 * 字段长度,只对于VARCHAR有效,其它数据库字段该值无效
	 * @return 字段长度, 默认返回50
	 */
	int size() default 50;
	
	boolean nullAble() default true;
}
