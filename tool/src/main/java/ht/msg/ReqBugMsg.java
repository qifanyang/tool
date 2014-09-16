package ht.msg;

/** 玩家问题反馈 */
public class ReqBugMsg implements IMessage {

	private String pid;
	private String sid;
	private String uid;// 反馈玩家的uid
	private String name;
	private String type;//0:其他,1:问题,2:投诉,3:建议
	/** 标题 */
	private String t;
	/** 内容 */
	private String c;

	@Override
	public int getId() {
		return MsgCode.BUG_REPORT;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
