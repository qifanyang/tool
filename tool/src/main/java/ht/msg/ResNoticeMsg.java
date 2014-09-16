package ht.msg;

public class ResNoticeMsg implements IMessage {

	/**1:成功, 0:失败*/
	private int success;
	
	@Override
	public int getId() {
		return MsgCode.NOTICE;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	
}
