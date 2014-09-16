package ht.msg;

import java.util.List;

import ht.vo.ActivityVO;


/**发送活动信息给游戏服*/
public class ResActivityMsg implements IMessage{

	private List<ActivityVO> ac ;
	
	public List<ActivityVO> getAc() {
		return ac;
	}

	public void setAc(List<ActivityVO> ac) {
		this.ac = ac;
	}


	@Override
	public int getId() {
		return MsgCode.ACTIVITY;
	}

}
