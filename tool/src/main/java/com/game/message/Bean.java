package com.game.message;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;

public abstract class Bean {

	protected Log LOGGER = LogFactory.getLog(Bean.class);

	public abstract boolean write(ChannelBuffer buf);

	public abstract boolean read(ChannelBuffer buf);

	protected void writeInt(ChannelBuffer buf, int value) {
		buf.writeInt(value);
	}

	protected void writeString(ChannelBuffer buf, String value) {
		if (value == null) {
			buf.writeInt(0);
			return;
		}
		try {
			byte[] bytes = value.getBytes("UTF-8");
			buf.writeInt(bytes.length);
			buf.writeBytes(bytes);
		} catch (UnsupportedEncodingException e) {
			this.LOGGER.error("Encode String Error:" + e.getMessage());
		}
	}

	protected void writeLong(ChannelBuffer buf, long value) {
		buf.writeLong(value);
	}

	protected void writeBean(ChannelBuffer buf, Bean value) {
		value.write(buf);
	}

	protected void writeShort(ChannelBuffer buf, int value) {
		buf.writeShort((short) value);
	}

	protected void writeShort(ChannelBuffer buf, short value) {
		buf.writeShort(value);
	}

	protected void writeByte(ChannelBuffer buf, byte value) {
		buf.writeByte(value);
	}

	protected int readInt(ChannelBuffer buf) {
		return buf.readInt();
	}

	protected String readString(ChannelBuffer buf) {
		int length = buf.readInt();
		if (length <= 0)
			return null;

		// if (buf.remaining() < length)
		if (buf.readableBytes() < length)
			return null;
		byte[] bytes = new byte[length];
		// buf.get(bytes);
		buf.readBytes(bytes);
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.LOGGER.error("Decode String Error:" + e.getMessage());
		}
		return null;
	}

	protected long readLong(ChannelBuffer buf) {
		return buf.readLong();
	}

	protected Bean readBean(ChannelBuffer buf, Class<? extends Bean> clazz) {
		try {
			Bean bean = (Bean) clazz.newInstance();
			bean.read(buf);
			return bean;
		} catch (IllegalAccessException e) {
			this.LOGGER.error("Decode Bean Error:" + e.getMessage());
		} catch (InstantiationException e) {
			this.LOGGER.error("Decode Bean Error:" + e.getMessage());
		}
		return null;
	}

	protected short readShort(ChannelBuffer buf) {
		return buf.readShort();
	}

	protected byte readByte(ChannelBuffer buf) {
		return buf.readByte();
	}
}