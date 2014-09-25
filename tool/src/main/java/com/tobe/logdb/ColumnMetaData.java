package com.tobe.logdb;

import java.lang.reflect.Field;

import org.joda.time.DateTime;

/**
 * 数据库列
 */
public class ColumnMetaData {

	private String name;
	private String javaType;
	private String MySQLType;
	private Integer size;
	private boolean nullable;
	private String def = "";
	private String primary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getMySQLType() {
		return MySQLType;
	}

	public void setMySQLType(String mySQLType) {
		MySQLType = mySQLType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public boolean getNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getDef() {
		if(def.length() == 0){
			return def;
		}else{
			return "DEFAULT '" + def + "'";
		}
	}

	public void setDef(String def) {
		this.def = def;
	}

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	@Override
	public String toString() {
		return "ColumnInfo [name=" + name + ", type=" + javaType + ", size=" + size + ", nullable=" + nullable + ", def=" + def + ", primary=" + primary
				+ "]";
	}

	private String getFieldType() {
		if (getMySQLType().equalsIgnoreCase("varchar")) {
			return getMySQLType() + "(" + getSize() + ")";
		} else {
			return getMySQLType();
		}
	}

	public String toDDL() {
		StringBuilder ddl = new StringBuilder();
		// String ddl = getName() + "\t" + getFieldType() + "\t" +
		// getNullAble();

		ddl.append("`").append(getName()).append("`\t").append(getFieldType()).append(getNullable() ? "" : " NOT NULL " + getDef());
		return ddl.toString();
	}

	public static ColumnMetaData build(Field f) {
		String fieldName = f.getName();
		Class<?> javaType = f.getType();
		ColumnMetaData metaData = new ColumnMetaData();
		metaData.setJavaType(javaType.getSimpleName());
		
		String mysqltype = J2MTypeMapping.getMySQLType(javaType.getSimpleName());
		Property annotation = f.getAnnotation(Property.class);
		if (annotation == null) {
			// 没有注解,解析字段
			metaData.setName(fieldName);
			metaData.setSize(50);
			metaData.setMySQLType(mysqltype);
		} else {
			// 有注解需要更具注解替换某些属性
			if (annotation.ignore())
				return null;// 忽略该字段

			String tempName;
			String tempType;

			if (annotation.type() == FieldType.DEFAULT) {
				// 没有设置字段类型,采用默认
				tempType = mysqltype;
			} else {
				tempType = annotation.type().toString();
			}

			if (annotation.name().length() == 0) {
				tempName = fieldName;
			} else {
				tempName = annotation.name();
			}
			metaData.setName(tempName);
			metaData.setMySQLType(tempType);
			metaData.setNullable(annotation.nullAble());
			if(!metaData.getNullable()){
				metaData.setDefaultValue();
			}
			// DDL.append("`").append(tempName).append("`\t").append(tempType);
			if (javaType == String.class) {

				metaData.setSize(annotation.size());
				// String 类型, 数据库可以是varchar text longtext
//				if (annotation.type() == FieldType.VARCHAR) {
//					metaData.setSize(annotation.size());
//				}
			}
		}
		return metaData;
	}
	
	public void setDefaultValue(){
		if(getJavaType().equalsIgnoreCase("byte") || getJavaType().equalsIgnoreCase("short") || getJavaType().equalsIgnoreCase("int")
			|| getJavaType().equalsIgnoreCase("integer") ||	getJavaType().equalsIgnoreCase("long")||	getJavaType().equalsIgnoreCase("float")
			||	getJavaType().equalsIgnoreCase("double")){
			setDef("0");
		}else if(getJavaType().equalsIgnoreCase("date")){
			setDef(new DateTime().toString("yyyy-MM-dd hh:MM:ss"));
		}
	}
}
