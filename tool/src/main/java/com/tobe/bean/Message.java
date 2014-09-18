package com.tobe.bean;

import java.util.List;
import java.util.Set;

/**
 * 消息描述
 * 
 */
public class Message {

	private int id;
	private String type;
	private String messageName;
	private String packageName;
	private String explain;
	private String queue;
	private String server;
	private List<Field> fields;
	private Set imports;

	public Message() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Set getImports() {
		return imports;
	}

	public void setImports(Set imports) {
		this.imports = imports;
	}
}
