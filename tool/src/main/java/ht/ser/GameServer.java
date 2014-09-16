package ht.ser;


import ht.NetConnection;
import ht.msg.IMessage;
import ht.msg.JsonMsg;
import ht.msg.MsgCode;
import ht.msg.ReqBugMsg;
import ht.msg.ResNoticeMsg;
import ht.msg.Test1Msg;
import ht.msg.TestMsg;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import dwz.business.customertool.CustomerToolServiceMgr;
//import dwz.business.customertool.info.ReportInfo;
//import dwz.framework.sys.business.BusinessFactory;
//import dwz.framework.sys.business.BusinessObjectServiceMgr;
//import dwz.web.management.CustomerToolController;

/**
 * 表示一个连接到后台的游戏服
 * */
public class GameServer {
	private static final Log log = LogFactory.getLog(GameServer.class);
	private int sid;
	private String name;
	
	private final Executor msgexecutor = Executors.newSingleThreadExecutor();
	
	private NetConnection conn = null;//for test;
	
	public GameServer(int sid){
		this.sid = sid;
	}

	public String getIp(){
		return conn == null ? "NULL CONN" : conn.getIp();
	}

	public String getName(){
		return name;
	}
	
	public void sendMessage(IMessage msg){
//			JsonMsg jsonmsg = new JsonMsg();
//			jsonmsg.setId(msg.getId());
//			jsonmsg.setMsg(msg);
			boolean success = conn.sendMessage(msg);
			if(!success){
				log.error("发送消息失败, sid = " + sid);
			}
	}

	public NetConnection getConn() {
		return conn;
	}

	public void setConn(NetConnection conn) {
		this.conn = conn;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void onMessage(JsonMsg msg) {
		try {
			MsgProcessorTask task = new MsgProcessorTask(msg);
			msgexecutor.execute(task);
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	
	 class MsgProcessorTask implements Runnable{

			private JsonMsg msg;
			public MsgProcessorTask(JsonMsg msg){
				this.msg = msg;
			}
			
			@Override
			public void run() {
//				BackendClientMsgProcessor.this.process(msg);
				GameServer.this.process(msg);
			}
			
	}
	 
	 /**处理游戏服发来的消息*/
	 private void process(JsonMsg msg){
		 switch (msg.getId()) {
			case MsgCode.TEST:
				TestMsg test = (TestMsg)msg.getMsg();
				log.error(test.getI());
				log.error(test.getName());
				
				break;
			case MsgCode.NOTICE://响应游戏公告
				ResNoticeMsg resnotice = (ResNoticeMsg)msg.getMsg();
				log.error("客户端回复公告结果  success = " + resnotice.getSuccess());
				break;
			case MsgCode.TEST_1://
				Test1Msg test1 = (Test1Msg)msg.getMsg();
				log.error(test1.getContent());
				break;
			case MsgCode.BUG_REPORT://
				ReqBugMsg bugmsg = (ReqBugMsg)msg.getMsg();
				proReport(bugmsg);
				break;
			default:
				break;
			}
	 }
	 
	 
	 private void proReport(ReqBugMsg msg){
		 //保存反馈到后台中
//		 ReportInfo r = new ReportInfo();
//		 r.setPid(msg.getPid());
//		 r.setSid(msg.getSid());
//		 r.setUid(msg.getUid());
//		 r.setName(msg.getName());
//		 r.setTitle(msg.getT());
//		 r.setContent(msg.getC());
//		 r.setType(getReportType(msg.getType()));
//		 r.setRtime(new Date());
//		 
//		 if(StringUtils.isEmpty(r.getPid())){
//			 r.setPid("1");//bug fixed
//		 }
//		 CustomerToolServiceMgr service = BusinessFactory.getInstance().getService(CustomerToolServiceMgr.SERVICE_NAME);
//		 service.insertReport(r);
	 }
	 
	 private String getReportType(String type){
		 if(type.equalsIgnoreCase("problem")){
			 return "问题";
		 }else if(type.equalsIgnoreCase("complain")){
			 return "投诉";
		 }else if(type.equalsIgnoreCase("suggest")){
			 return "建议";
		 }else if(type.equalsIgnoreCase("elsePro")){
			 return "其他";
		 }
		 return "其他";
	 }

}
