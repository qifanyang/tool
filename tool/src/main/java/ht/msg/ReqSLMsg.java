package ht.msg;

/**
 * 禁言冻结消息
 * 
 * @author TOBE
 * 
 */
public class ReqSLMsg implements IMessage {

	// 服务器接收数据格式:sid,uid,operate(1:禁言,2:解除禁言,3:冻结,4:解除冻结),endtime

	private String uid;
	// 1:禁言,2:解除禁言,3:冻结,4:解除冻结 , 解除操作时间字段为空
	private String type;
	// 禁言或冻结结束时间,格式:2014-09-10 14:18:33
	private String time;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public int getId() {
		return MsgCode.SAY_OR_LOGIN;
	}

}
