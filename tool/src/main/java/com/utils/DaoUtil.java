package com.utils;

import java.util.concurrent.ConcurrentHashMap;

import ht.ser.DBAccountInfo;
import ht.ser.GSConfig;
import ht.ser.GameServerManager;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DaoUtil {

	//TODO 缓存数据源
	private static ConcurrentHashMap<String, MysqlDataSource> dsmap = new ConcurrentHashMap<String, MysqlDataSource>();
	
	/**游戏服数据源*/
	public static DataSource getGameDataSource(String ip, String name){  
        String url = "jdbc:mysql://" + ip + ":3306/pgame_user"; 
        GSConfig cfg = getGSConfig(ip);
        MysqlDataSource ds = DataSourceCache.ensureDataSource(url, cfg.getUser(), cfg.getPasswd(), null);
        if(ds != null){
        	return ds;
        }
//        ds = new BasicDataSource();
        ds = new MysqlDataSource();  
        ds.setServerName(name);  
        ds.setURL(url); 
        ds.setUser(cfg.getUser());
        ds.setPassword(cfg.getPasswd());  
        ds.setCharacterEncoding("utf8");  
        return DataSourceCache.ensureDataSource(url, cfg.getUser(), cfg.getPasswd(), ds);  
    }  
	
	public static DataSource getLogDataSource(String ip, String name){  
        String url = "jdbc:mysql://" + ip + ":3306/pgame_log";  
        GSConfig cfg = getGSConfig(ip);
        MysqlDataSource ds = DataSourceCache.ensureDataSource(url, cfg.getUser(), cfg.getPasswd(), null);
        if(ds != null){
        	return ds;
        }
        ds = new MysqlDataSource();  
        ds.setServerName(name);  
        ds.setURL(url);  
        ds.setUser(cfg.getUser());
        ds.setPassword(cfg.getPasswd());  
        ds.setCharacterEncoding("utf8");  
        return DataSourceCache.ensureDataSource(url, cfg.getUser(), cfg.getPasswd(), ds);   
    }
	
	//测试,内网用密码liumang,   本地用密码123456
	public static DataSource getRechargeDataSource(String ip, String name){  
		String url = "jdbc:mysql://" + ip + ":3306/pgame_log";  
		GSConfig cfg = getGSConfig(ip);
		 MysqlDataSource ds = DataSourceCache.ensureDataSource(url, cfg.getUser(), cfg.getPasswd(), null);
	        if(ds != null){
	        	return ds;
	        }
	    ds = new MysqlDataSource();  
		ds.setServerName(name);  
		ds.setURL(url);  
	    ds.setUser(cfg.getUser());
	    ds.setPassword(cfg.getPasswd());  
		ds.setCharacterEncoding("utf8");  
		return DataSourceCache.ensureDataSource(url, cfg.getUser(), cfg.getPasswd(), ds);   
	}
	
	/**在loginDbInfo初始化后调用*/
	public static DataSource getLoginDataSource(){
		DBAccountInfo info = GameServerManager.getMe().getLoginDbInfo();
		String url = "jdbc:mysql://" + info.getIp() + ":3306/"+info.getDbname();  
		MysqlDataSource ds = DataSourceCache.ensureDataSource(url, info.getUser(), info.getPasswd(), null);
	        if(ds != null){
	        	return ds;
	        }
		ds = new MysqlDataSource();  
		ds.setServerName(info.getDbname());  
		ds.setURL(url);  
	    ds.setUser(info.getUser());
	    ds.setPassword(info.getPasswd());  
		ds.setCharacterEncoding("utf8");  
		return DataSourceCache.ensureDataSource(url, info.getUser(), info.getPasswd(), ds);
	}
	
	/**游戏配置数据,比如道具等等,暂时和login放在一起*/
	public static DataSource getModelDataSource(){
		DBAccountInfo info = GameServerManager.getMe().getLoginDbInfo();
		String url = "jdbc:mysql://" + info.getIp() + ":3306/pgame_system";  
		MysqlDataSource ds = DataSourceCache.ensureDataSource(url, info.getUser(), info.getPasswd(), null);
        if(ds != null){
        	return ds;
        }
        ds = new MysqlDataSource();  
		ds.setServerName(info.getDbname());  
		ds.setURL(url);  
		ds.setUser(info.getUser());
		ds.setPassword(info.getPasswd());  
		ds.setCharacterEncoding("utf8");  
		return DataSourceCache.ensureDataSource(url, info.getUser(), info.getPasswd(), ds); 
	}
	
	private static GSConfig getGSConfig(String ip){
		GSConfig gsConfig = GameServerManager.getMe().getGsconofig().get(ip);
		return gsConfig;
	}
}
