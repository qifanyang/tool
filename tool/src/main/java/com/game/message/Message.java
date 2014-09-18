package com.game.message;

import java.util.ArrayList;
import java.util.List;
import org.jboss.netty.channel.Channel;

public abstract class Message extends Bean {
	private long sendId;
	private List<Long> roleId = new ArrayList<Long>();
	private Channel channel;
	private int sendTime;

	public abstract int getId();

	public abstract String getQueue();

	public abstract String getServer();

	public long getSendId() {
		return this.sendId;
	}

	public void setSendId(long sendId) {
		this.sendId = sendId;
	}

	public List<Long> getRoleId() {
		return this.roleId;
	}

	public void setRoleId(List<Long> roleId) {
		this.roleId = roleId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public int getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(int sendTime) {
		this.sendTime = sendTime;
	}
}