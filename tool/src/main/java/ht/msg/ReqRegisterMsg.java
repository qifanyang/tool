package ht.msg;


public class ReqRegisterMsg implements IMessage {

	/**游戏服务器编号*/
	private int sid;
	/**会话秘钥*/
	private String key;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public int getId() {
		return MsgCode.REGISTER;
	}
	
	
}
