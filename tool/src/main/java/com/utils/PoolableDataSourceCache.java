package com.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

//数据源使用连接对象池
public class PoolableDataSourceCache {

	private static int MAX = 50;//根据游戏服务器数量调整
	
	static{
		 try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	
	public static Map<String , DataSource> uidmap = Collections.synchronizedMap(new LinkedHashMap<String , DataSource>(100, 0.75f, true){
		private static final long serialVersionUID = 1L;

		protected boolean removeEldestEntry(java.util.Map.Entry<String , DataSource> eldest) {
			return size() > MAX;
		};
	});
	

	
	public static DataSource getDataSource(String url, String name, String pass){
		
		String key = url + "&&" + name + "&&" + pass;
		
		DataSource ds = uidmap.get(key);
		if(null == ds){
			//不包含,则在这里创建
			ObjectPool connectionPool = new GenericObjectPool(null);
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url,null);

	        //
	        // Now we'll create the PoolableConnectionFactory, which wraps
	        // the "real" Connections created by the ConnectionFactory with
	        // the classes that implement the pooling functionality.
	        //
	        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,connectionPool,null,null,false,true);

	        //
	        // Finally, we create the PoolingDriver itself,
	        // passing in the object pool we created.
	        //
	        BasicDataSource bds = new BasicDataSource();
//	        bds.setMaxActive(maxActive)
	        
	        ds = bds;
	        uidmap.put(key, ds);
	        
	        
		}
		return ds;
		
	}
}
