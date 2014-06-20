package com.tobe.actions;

import java.awt.Component;

import com.tobe.config.CommCofig;
import com.tobe.ui.tree.MTreeNode;

public class ActionContext {
	
	private MTreeNode node;
	
	private CommCofig config;
	
	private Component attachComponent;

	public MTreeNode getNode() {
		return node;
	}

	public void setNode(MTreeNode node) {
		this.node = node;
	}

	public CommCofig getConfig() {
		return config;
	}

	public void setConfig(CommCofig config) {
		this.config = config;
	}

	public Component getAttachComponent() {
		return attachComponent;
	}

	public void setAttachComponent(Component attachComponent) {
		this.attachComponent = attachComponent;
	}
	

}
