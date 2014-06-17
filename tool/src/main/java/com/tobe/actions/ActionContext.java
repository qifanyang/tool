package com.tobe.actions;

import com.tobe.config.CommCofig;
import com.tobe.ui.tree.MTreeNode;

public class ActionContext {
	
	private MTreeNode node;
	
	private CommCofig config;

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
	

}
