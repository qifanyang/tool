package ht.msg;

/**
 * 后台和游戏服采用json通信 格式:msgId+json
 * */
public class JsonMsg {

	/** 协议id,消息数量不多,简单处理不分模块 */
	private int id;

	private IMessage msg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IMessage getMsg() {
		return msg;
	}

	public void setMsg(IMessage msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "[id = " + id + ", json = " + msg.toString() +"]";
	}
}
