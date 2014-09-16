package ht.msg;

/**B->G*/
public class ReqNoticeMsg implements IMessage{

	/**1:滚动公告(跑马灯), 2:聊天公告*/
	private int type;
	/**公告内容*/
	private String content;
	
	@Override
	public int getId() {
		return MsgCode.NOTICE;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "[type = " + type + " , content = " + content + "]";
	}
}
