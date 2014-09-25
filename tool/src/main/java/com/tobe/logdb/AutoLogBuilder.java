package com.tobe.logdb;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class AutoLogBuilder {

//	/**
//	 * java数据类型到mysql数据类型的映射
//	 */
//	public static Map<String, String> j2mTypeMap = new HashMap<String, String>();
//	static {
//		//数字型
//		j2mTypeMap.put("byte", "TINYINT");
//		j2mTypeMap.put("Byte", "TINYINT");
//		j2mTypeMap.put("short", "SMALLINT");
//		j2mTypeMap.put("Short", "SMALLINT");
//		j2mTypeMap.put("int", "INTEGER");
//		j2mTypeMap.put("Integer", "INTEGER");
//		j2mTypeMap.put("long", "BIGINT");
//		j2mTypeMap.put("Long", "BIGINT");
//		
//		j2mTypeMap.put("float", "FLOAT");
//		j2mTypeMap.put("Float", "FLOAT");
//		j2mTypeMap.put("double", "DOUBLE");
//		j2mTypeMap.put("Double", "DOUBLE");
//		
//		
//		//文本型
//		j2mTypeMap.put("String", "VARCHAR");
//		j2mTypeMap.put("TEXT", "TEXT");
//		j2mTypeMap.put("LONGTEXT", "LONGTEXT");
//		
//		//时间
//		j2mTypeMap.put("Date", "DATETIME");
//		
//		//二进制
//		j2mTypeMap.put("byte[]", "BLOB");
//		// TODO ....
//	}
	
	/**
	 * 创建表名
	 * @param log
	 * @return
	 */
	public String buildTableName(AutoLog log) {
		String tablename = log.getClass().getSimpleName();
		long time = log.getTime();
		switch (log.rollingPeriod()) {
		case DAY:
			tablename += new DateTime(time).toString("yyyyMMdd");
			break;
		case MONTH:
			tablename += new DateTime(time).toString("yyyyMM");
			break;
		case YEAR:
			tablename += new DateTime(time).toString("yyyy");
			break;
		// case UNROLL:
		}
		return tablename;
	}
	
	public String buildCreateTableSql(AutoLog log) {
		StringBuilder DDL = new StringBuilder();
		DDL.append("CREATE TABLE IF NOT EXISTS `").append(buildTableName(log)).append("` (\n`id` int(11) NOT NULL AUTO_INCREMENT,\n");
		// for (MetaData metaData : getMetadata()) {
		// DDL.append("name varchar(20)").append(",\n");

		// 解析字段
		Field[] fields = log.getClass().getFields();
		for (Field f : fields) {
//			String fieldName = f.getName();
//			Class<?> javaType = f.getType();
//			String mysqltype = J2MTypeMapping.getMySQLType(javaType.getSimpleName());
//			Property annotation = f.getAnnotation(Property.class);
//			if (annotation == null) {
//				// 没有注解,解析字段
//				DDL.append("`").append(fieldName).append("`\t").append(mysqltype);
//				if (javaType == String.class) {
//					DDL.append("(50)");
//				}
//				DDL.append(",\n");
//			} else {
//				// 有注解需要更具注解替换某些属性
//				if (annotation.ignore())
//					continue;// 忽略该字段
//
//				String tempName;
//				String tempType;
//
//				if (annotation.type() == FieldType.DEFAULT) {
//					// 没有设置字段类型,采用默认
//					tempType = mysqltype;
//				} else {
//					tempType = annotation.type().toString();
//				}
//
//				if (annotation.name().length() == 0) {
//					tempName = fieldName;
//				} else {
//					tempName = annotation.name();
//				}
//
//				DDL.append("`").append(tempName).append("`\t").append(tempType);
//				if (javaType == String.class) {
//
//					// String 类型, 数据库可以是varchar text longtext
//					if (annotation.type() == FieldType.VARCHAR) {
////						if (annotation.size() == -1) {
////							DDL.append("(50)");
////						} else {
//							DDL.append("(").append(annotation.size()).append(")");
////						}
//					}
//				}
//				DDL.append(",\n");
//			}
			ColumnMetaData metaData = ColumnMetaData.build(f);
			if(null != metaData){
				DDL.append(metaData.toDDL());
				DDL.append(",\n");
			}
		}

		// }
		DDL.append("PRIMARY KEY (`id`)) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8");
		return DDL.toString();
	}
	
	
	public String buildInsertDataSQL(AutoLog log) {
		// 构建表名
		String tableName = buildTableName(log);
		StringBuilder fieldsBuilder = new StringBuilder();
		StringBuilder valuesBuilder = new StringBuilder();
		// 构建插入语句主体
		for (Field f : log.getClass().getFields()) {
			String fieldName = getRealFieldName(f);
			if(null != fieldName){//null 表示忽略
				fieldsBuilder.append(fieldName).append(",");
				valuesBuilder.append(getRealFieldName(f, log)).append(",");
			}
		}
		fieldsBuilder.deleteCharAt(fieldsBuilder.length() - 1);
		valuesBuilder.deleteCharAt(valuesBuilder.length() - 1);
		// 构建插入语句
		StringBuilder inserSQL = new StringBuilder("insert into `" + tableName + "` ").append("(").append(fieldsBuilder).append(") ").append("values").append("(").append(valuesBuilder).append(")");
		String returnString = inserSQL.toString();
		return returnString;

	}
	
	public String getRealFieldName(Field f){
		Property annotation = f.getAnnotation(Property.class);
		if(null == annotation){
			return f.getName();
		}
		
		if(annotation.ignore()){
			return null;
		}
		
		if(annotation.name().length() == 0){
			return f.getName();
		}
		
		return annotation.name();
	}
	
	/**
	 * 取得字段值
	 * @param f
	 * @param log
	 * @return
	 */
	public String getRealFieldName(Field f, AutoLog log){
		
		Object object = null;
		try {
			object = f.get(log);
			if(object instanceof java.util.Date){
				return "'"+new DateTime(((Date)object).getTime()).toString("yyyy-MM-dd hh:MM:ss")+"'";
			}
//		if(object instanceof List){
//			//暂不作集合类支持
//		}
			if(object instanceof String){
				//TODO 防注入处理
//			object=
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object==null?"''":"'"+object.toString()+"'";
	}
	
	public static String oldTableName = "";

	public boolean isNeedCreatTable(AutoLog log) {
		String newTableName = buildTableName(log);
		if (newTableName.equals(oldTableName)) {
			return false;
		}

		oldTableName = newTableName;
		return true;
	}
	
	public static void main(String[] args) {
		TestBean log = new TestBean();
		
		log.name = "名字测试";
		AutoLogBuilder bb = new AutoLogBuilder();
		if (bb.isNeedCreatTable(log)) {
			// 创建表
			String createTableSql = bb.buildCreateTableSql(log);
			System.out.println(createTableSql);

		}
		// 插入
		System.out.println(bb.buildInsertDataSQL(log));
	}
}
