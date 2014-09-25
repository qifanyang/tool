package com.tobe.logdb;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 表同步
 */
public class TableSyncUtils {

	static DruidDataSource dataSource;

	protected static void setUp() throws Exception {
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/pgame_log";
		String user = "root";
		String password = "123456";
		String driverClass = "com.mysql.jdbc.Driver";

		dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(jdbcUrl);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setUsername(user);
		dataSource.setPassword(password);

	}

	/**
	 * 检查类属性和数据库表字段差异,若类带有Property注解的字段, 在数据库表中没有, 则在数据库表中添加该字段
	 * 
	 * @param conn
	 *            数据库连接
	 * @param cls
	 *            要同步的类
	 * @throws SQLException
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static void syncFieldToMysql(Connection conn, Class<?> cls) throws Exception {
		conn = dataSource.getConnection();

		// 检查表是否存在

		// 获取表字段
		cls = TestBean.class;
		String tableName = cls.getSimpleName();
		// 检查cls里面有注解的字段是否存在于表中,一存在的字段名字一样,字段类型不一样,不处理
		Set<String> tableColumnsName = getTableColumnsName(conn, tableName);

		// 类字段
		Set<String> propertyFieldSet = getPropertyFieldSet(cls, true);

		// 求数据库里面不存在的字段,差集
		propertyFieldSet.removeAll(tableColumnsName);

		//ALTER TABLE `TestBean`
		//ADD COLUMN `addNew`  varchar(50) NULL;
		if (propertyFieldSet.size() > 0) {
			// 有新增的字段
//			System.out.println(propertyFieldSet);
			
			List<Field> addNewField = new ArrayList<Field>();
			for(String add : propertyFieldSet){
				Field field = cls.getField(add);
				if(null != field){
					addNewField.add(field);
				}
			}
			
			String buildAlertSQL = buildAlertSQL(addNewField, tableName);
			System.out.println(buildAlertSQL);
		}

	}
	
	public static String buildAlertSQL(List<Field> addFieldList, String tableName){
		StringBuilder sql = new StringBuilder();
		boolean haveNewFiled = false;
		sql.append("ALTER TABLE `").append(tableName).append("`");
		for(Field f : addFieldList){
			ColumnMetaData metaData = ColumnMetaData.build(f);
			if(null != metaData){
				sql.append("\nADD COLUMN ").append(metaData.toDDL()).append(",");
				haveNewFiled = true;
			}
		}
		
		if(haveNewFiled){
			sql.deleteCharAt(sql.length() - 1);
			sql.append(";");
		}
		
		return sql.toString();
		
	}

	public static Set<String> getTableName(Connection conn) throws SQLException {
		ResultSet tableRet = conn.getMetaData().getTables(null, null, null, new String[] { "TABLE" });
		Set<String> tablenames = new HashSet<String>();
		while (tableRet.next()) {
			tablenames.add(tableRet.getString("TABLE_NAME").toLowerCase());
		}
		return tablenames;
	}

	public static Set<String> getTableColumnsName(Connection conn, String tableName) throws SQLException {
		ResultSet tableRet = conn.getMetaData().getColumns(null, null, tableName, null);
		Set<String> columnsName = new HashSet<String>();
		while (tableRet.next()) {
			columnsName.add(tableRet.getString("COLUMN_NAME").toLowerCase());
		}
		return columnsName;
	}

	/**
	 * 1:返回所有字段
	 * 2:返回只带注解的字段
	 * @param property
	 *            true:表示返回只带有{@link Property}注解的public属性字段, 包括父类的<br>
	 *            false:所有字段,如果注解忽略不返回<br>
	 * @param cls
	 * @return
	 */
	public static Set<String> getPropertyFieldSet(Class<?> cls, boolean property) {
		Set<String> propertyFieldSet = new HashSet<String>();
		Field[] fields = cls.getFields();
		for (Field f : fields) {
			Property annotation = f.getAnnotation(Property.class);
			if(property){
				//只返回带有注解的字段
				if (annotation != null && !annotation.ignore()) {
					propertyFieldSet.add(f.getName());
				}
			}else{
				//返回所有字段,包括有注解没注解的, 注解忽略的话不返回
				if(null == annotation){
					propertyFieldSet.add(f.getName());
				}else{
					if(!annotation.ignore()){
						propertyFieldSet.add(f.getName());
					}
				}
			}
		}

		return propertyFieldSet;
	}

	public static void main(String[] args) throws Exception {
		setUp();
		syncFieldToMysql(null, null);
	}

}
