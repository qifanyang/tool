package com.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于跟踪玩家操作,返回给前端,提升友好度
 * 
 */
public class ActionReport {

	private List<String> msgs = new ArrayList<String>();
	private State state = State.SUCCESS;

	/**增加一条提示消息*/
	public ActionReport appendAlert(String msg){
		msgs.add(msg);
		return this;
	}

//	public State getState() {
//		return state;
//	}
//
//	public void setState(State state) {
//		this.state = state;
//	}
	
	public boolean isState(State s){
		return s == this.state ? true : false;
	}
	
	public void success(){
		this.state = State.SUCCESS;
	}
	
	public void failed(){
		this.state = State.FAILED;
	}
	
	public void error(){
		this.state = State.ERROR;
	}
	
	public String alert(){
		StringBuilder buider = new StringBuilder();
		buider.append(state.toString()).append(" : <br/>");
		
		for(String msg : msgs){
			buider.append(msg).append(" <br/>");
		}
		return buider.toString();
	}

	public static enum State {
		SUCCESS("操作成功"), // 完全成功
		FAILED("操作失败"), //
		ERROR("操作未全部完成");

		private State(String s) {
			this.s = s;
		}

		private String s;

		@Override
		public String toString() {
			return s;
		}

	}

	public static void main(String[] args) {
		System.out.println(State.ERROR.toString());
	}
}
