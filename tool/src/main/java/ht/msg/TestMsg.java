package ht.msg;

/**测试消息,测试各种数据类型*/
public class TestMsg implements IMessage {
	
	private int i = 1000;
	private String name = "你好,我是测试消息";

	@Override
	public int getId() {
		return MsgCode.TEST;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "[ name = " + name + " , i = " + i + "]";
	}
}
