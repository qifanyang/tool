package ht.ser;

import ht.msg.IMessage;
import ht.msg.ResActivityMsg;
import ht.vo.ActivityVO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
//import org.springframework.beans.factory.BeanFactory;

//import dwz.business.ActionReport;
//import dwz.business.RSIntegerColumnHandler;
//import dwz.business.customertool.CustomerToolServiceMgr;
//import dwz.business.customertool.info.RoleInfo;
//import dwz.framework.sys.business.BusinessFactory;
//import dwz.framework.sys.business.BusinessObjectServiceMgr;
//import dwz.persistence.beans.VipInfo;
//import dwz.utils.DaoUtil;


public class GameServerManager {

	private final static Log log = LogFactory.getLog(GameServerManager.class);
	
	private static GameServerManager me;
	
	public static GameServerManager getMe(){
		if(me == null){
			synchronized (GameServerManager.class) {
				if(me == null){
					me = new GameServerManager();
				}
			}
		}
		return me;
	}

	/**游戏服MAP key:服务器id*/
	private Map<Integer, GameServer> gsMap = new ConcurrentHashMap<Integer, GameServer>();

	public  ConcurrentHashMap<String, GSConfig> gsconofig = new ConcurrentHashMap<String, GSConfig>();
	
	public  ConcurrentHashMap<String, LoginServer> loginserverMap = new ConcurrentHashMap<String, LoginServer>();

	public DBAccountInfo loginDbInfo = null;
//	public Map<Integer, GameServer> getGsMaps() {
//		return gsMap;
//	}
//
//	public void setGsMaps(Map<Integer, GameServer> gsMaps) {
//		this.gsMap = gsMaps;
//	}

	private List<ActivityVO> activityList = null;
	
	/**游戏服务器注册到后台*/
	public boolean register(Integer sid, GameServer gs){
		if(gsMap.containsKey(sid)){
			log.error("游戏服务器已经注册 , sid = " + sid + ", ip = " + gs.getIp());
			return false;
		}
		gsMap.put(sid, gs);
		log.error("[注册]新的游戏服务器成功 , sid = " + sid + " ip = " + gs.getIp());
		return true;
	}
	
	public boolean remove(int sid, String ip){
		boolean success = gsMap.remove(sid) != null ? true : false;
		if(success){
			log.error("[移除]游戏服务器成功 , sid = " + sid + " ip = " + ip);
		}
		return success;
	}
	
	public GameServer getGameServer(String sid){
		return getGameServer(Integer.parseInt(sid));
	}
	
	public GameServer getGameServer(int sid){
		GameServer gs = gsMap.get(sid);
		if(gs == null){
			log.error("gs没有连接到后台,  sid = " + sid);
		}
		return gs;
	}
	
	
	public boolean sendMessage(int sid, IMessage msg){
		GameServer gs = getGameServer(sid);
		if(gs != null){
			gs.sendMessage(msg);
			return true;
		}
		return false;
	}
	
	public void broadcastMessage(IMessage msg){
		for(GameServer gs : gsMap.values()){
			gs.sendMessage(msg);
		}
	}
	
	public boolean hasConnected(int sid){
		return gsMap.containsKey(sid);
	}
	
	public boolean hasConnected(String sid){
		return gsMap.containsKey(Integer.parseInt(sid));
	}
	
	
	public ConcurrentHashMap<String, GSConfig> getGsconofig() {
		return gsconofig;
	}

	public void setGsconofig(ConcurrentHashMap<String, GSConfig> gsconofig) {
		this.gsconofig = gsconofig;
	}

	
	public DBAccountInfo getLoginDbInfo() {
		return loginDbInfo;
	}

	public void setLoginDbInfo(DBAccountInfo loginDbInfo) {
		this.loginDbInfo = loginDbInfo;
	}

	
	public ConcurrentHashMap<String, LoginServer> getLoginserverMap() {
		return loginserverMap;
	}

	public void setLoginserverMap(
			ConcurrentHashMap<String, LoginServer> loginserverMap) {
		this.loginserverMap = loginserverMap;
	}

	public void loadGSConfig(){
		InputStream inputStream = GameServerManager.class.getResourceAsStream("/gameservers.xml");
		SAXReader saxReader = new SAXReader();
        try 
        {
        	Document document = saxReader.read(inputStream);
			Element root = document.getRootElement();
			Iterator<Element> serElements = root.elementIterator("server");
	    	while (serElements.hasNext())
	        {
	        Element ser = serElements.next();
	        String ip = ser.attributeValue("ip");
	        String passwd = ser.attributeValue("passwd");
	        String user = ser.attributeValue("user");
	        String http = ser.attributeValue("http");
//	        System.out.println(ip);
//	        System.out.println(logpasswd);
//	        System.out.println(userpasswd);
	        
	        GSConfig cfg = new GSConfig();
	        cfg.setIp(ip);
	        cfg.setUser(user);
	        cfg.setPasswd(passwd);
	        cfg.setHttp(http);
	        getGsconofig().put(ip, cfg);
	            
	        }
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("解析游戏服数据库配置列表配置文件出错了");
		}
        
        log.error("解析游戏服数据库配置完成");
	}
	
	public void loadLoginConfig(){
		 try {
			 InputStream inputStream = GameServerManager.class.getResourceAsStream("/login.properties");
			 Properties properties = new Properties();
			properties.load(inputStream);
			DBAccountInfo accountInfo = new DBAccountInfo();
			accountInfo.setIp(properties.getProperty("login.ip","127.0.0.1"));
			accountInfo.setUser(properties.getProperty("login.user","root"));
			accountInfo.setPasswd(properties.getProperty("login.passwd","123456"));
			accountInfo.setDbname(properties.getProperty("login.dbname","pgame_login"));
			setLoginDbInfo(accountInfo);
			log.error("加载login数据库配置OK");
		} catch (IOException e) {
			log.error("加载login数据库配置出错", e);
		}
		 
	}
	
	public void loadLoginServer(){
		
//		DataSource loginDataSource = DaoUtil.getLoginDataSource();
//		QueryRunner run = new QueryRunner(loginDataSource);
//		
//		BeanListHandler<LoginServer> serverHandler = new BeanListHandler<LoginServer>(LoginServer.class);
//		try {
//			 List<LoginServer> servrlist = run.query("SELECT * FROM server", serverHandler);
//			 for(LoginServer lg : servrlist){
//			 	loginserverMap.put(lg.getIp(), lg);
//			 }
//			 
//			 log.error("加载loginserver结束");
//		} catch (SQLException e) {
//			log.error("加载login服务器出错", e);
//		} finally{
//			try {
//				loginDataSource.getConnection().close();
//			} catch (SQLException e) {
//				log.error(e, e);
//			}
//		}
		
	}
	
	public void loadActivity(){
//		CustomerToolServiceMgr service = BusinessFactory.getInstance().getService(CustomerToolServiceMgr.SERVICE_NAME);
//		activityList = service.loadActivity();
		log.error("加载活动结束, size = " + activityList.size());
	}
	
//	public ActionReport broadcastActivity(List<ActivityVO> list){
//			activityList = list;//更新
//			ResActivityMsg activityMsg = new ResActivityMsg();
//			activityMsg.setAc(list);
//			
//			broadcastMessage(activityMsg);
//			
//			return new ActionReport();
//	}
	
//	public ActionReport sendSingleActivity(int sid){
//		ResActivityMsg activityMsg = new ResActivityMsg();
//		activityMsg.setAc(activityList);
//		
//		sendMessage(sid, activityMsg);
//		
//		return new ActionReport();
//	}
	
	

	public List<ActivityVO> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<ActivityVO> activityList) {
		this.activityList = activityList;
	}
	
	
}
