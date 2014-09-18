package com.game.message;

import java.util.ArrayList;
import java.util.List;

public class TransfersMessage {
	private int id;
	private long sendId;
	private List<Long> roleIds = new ArrayList<Long>();
	private byte[] bytes;
	private int sendTime;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getSendId() {
		return this.sendId;
	}

	public void setSendId(long sendId) {
		this.sendId = sendId;
	}

	public List<Long> getRoleIds() {
		return this.roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(int sendTime) {
		this.sendTime = sendTime;
	}

	public int getLength() {
		return this.bytes.length + 4;
	}

	public int getLengthWithRole() {
		return this.bytes.length + 4 + 8 + 4 + this.roleIds.size() * 64 / 8 + 4;
	}
}