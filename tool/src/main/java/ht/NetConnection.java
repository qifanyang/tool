package ht;

import ht.msg.IMessage;
import ht.msg.JsonMsg;
import ht.msg.MsgCode;
import ht.msg.ReqRegisterMsg;
import ht.msg.TestMsg;
import ht.ser.GameServer;
import ht.ser.GameServerManager;

import java.util.concurrent.locks.ReentrantLock;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.Channel;

public class NetConnection {
	private static final Log log = LogFactory.getLog(NetConnection.class);
	
	private Channel channel;
	private boolean hasAuth;//是否验证过
	private GameServer gs;
	
	public NetConnection(Channel channel){
		this.channel = channel;
	}
	private ReentrantLock lock = new ReentrantLock();

	public boolean sendMessage(IMessage msg) {
		try {
			lock.lock();
			log.error("发送 消息  , id = " + msg.getId() + " , toString = " + msg.toString());
			Channel c = channel;
			if (c != null) {
				c.write(msg);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return false;
	}
	
	public String getIp(){
		return channel.getRemoteAddress().toString();
	}
	
	public int getSid(){
		if(gs == null){
			return -1;
		}
		return gs.getSid();
	}
	
	/**后面加个消息处理器,不然这里代码增多会很丑*/
	public void messageReceived(JsonMsg msg){
		int id = msg.getId();
		if(!hasAuth){
			if(id == MsgCode.REGISTER){
				//TODO 执行连接验证
				ReqRegisterMsg registermsg = (ReqRegisterMsg)msg.getMsg();
				int sid = registermsg.getSid();
				if(sid <= 0){
					log.error("sid 错误 , sid = " + sid);
					return;
				}
				
				if(GameServerManager.getMe().getGameServer(sid) != null){
					log.error("已经注册 , sid = " );
					return;
				}
				
				
				String key = registermsg.getKey();
				gs = new GameServer(sid);
				gs.setConn(this);
				
				GameServerManager.getMe().register(sid, gs);
				hasAuth = true;
				TestMsg testMsg = new TestMsg();
				testMsg.setName("登陆后台成功,后台返回消息");
				sendMessage(testMsg);
				
				//游戏服务器连接都后台,后台推送消息
//				GameServerManager.getMe().sendSingleActivity(sid);
			}else{
				channel.close();//没认证发消息来的直接关闭
				log.error("还没有验证,就发逻辑消息来了,不处理!", new RuntimeException());
			}
			return;
		}
		
		//已经进过了登陆验证,接下来是逻辑处理
		gs.onMessage(msg);
	}
	

}
