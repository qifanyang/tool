package ht.msg;

public class ReqMailMsg implements IMessage {

	/**玩家标识*/
	private String uid;
	/**角色名字*/
	private String name;
	/**邮件标题*/
	private String title;
	/**邮件文字内容*/
	private String content;
	/**是否给该区所有人发送*/
	private boolean all;
	
	@Override
	public int getId() {
		return MsgCode.MAIL;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	
}
