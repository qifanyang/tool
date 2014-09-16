package com.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSourceCache {
    private static int MAX = 50;//根据游戏服务器数量调整
	
	public static Map<String , MysqlDataSource> uidmap = Collections.synchronizedMap(new LinkedHashMap<String , MysqlDataSource>(100, 0.75f, true){
		private static final long serialVersionUID = 1L;

		protected boolean removeEldestEntry(java.util.Map.Entry<String , MysqlDataSource> eldest) {
			return size() > MAX;
		};
	});
	
	public static MysqlDataSource ensureDataSource(String url, String name, String pass, MysqlDataSource ds){
		String key = url + "&&" + name + "&&" + pass;
		if(ds == null){
			return uidmap.get(key);
		}
		
		uidmap.put(key, ds);
		return ds;
	}
}
