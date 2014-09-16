package com.utils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.ListenableFuture;

public class UIDCache {

	private static int MAX = 370;
	
	public static Map<String , String> uidmap = Collections.synchronizedMap(new LinkedHashMap<String , String>(500, 0.75f, true){
		private static final long serialVersionUID = 1L;

		protected boolean removeEldestEntry(java.util.Map.Entry<String , String> eldest) {
			return size() > MAX;
		};
	});
	
	public static String ensureUID(String sid, String name, String uid){
		String key = sid + "&&" + name;
		if(uid == null){
			//get uid
			return uidmap.get(key);
		}
		
		uidmap.put(key, uid);
		return uid;
	}
	
	private	static LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(300)
			.removalListener(new RemovalListener<String, String>() {

				@Override
				public void onRemoval(RemovalNotification<String, String> notification) {
					// 缓存超过最大数量限制,，超出时用LRU（least-recently-used)算法清除。

				}
			}).build(new CacheLoader<String, String>() {
				@Override
				public String load(String key) {
					String strProValue = "hello " + key + "!";
					return strProValue;
				}

				@Override
				public ListenableFuture<String> reload(String key,
						String oldValue) throws Exception {
					return super.reload(key, oldValue);
				}

			});

	private String load(String key){
		DataSource dataSource = DaoUtil.getLoginDataSource();
		String[] sidAndName = key.split("&&");
		
		QueryRunner runner = new QueryRunner(dataSource);
		return key;
	}

	public static String getUID(String sid, String name){
		try {
			return  cache.get(sid +"&&" + name);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		getUID("2", "fgg");
	}
}
