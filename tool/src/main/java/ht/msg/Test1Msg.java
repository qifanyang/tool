package ht.msg;

public class Test1Msg implements IMessage {

	private String content = "测试消息111111111";
	
	@Override
	public int getId() {
		return MsgCode.TEST_1;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
