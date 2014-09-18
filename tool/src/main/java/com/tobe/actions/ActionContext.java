package com.tobe.actions;

import java.awt.Component;

import com.tobe.config.ProjectConfig;
import com.tobe.ui.tree.MTreeNode;

public class ActionContext {
	
	private MTreeNode node;
	
	private ProjectConfig config;
	
	private Component attachComponent;

	public MTreeNode getNode() {
		return node;
	}

	public void setNode(MTreeNode node) {
		this.node = node;
	}

	public ProjectConfig getConfig() {
		return config;
	}

	public void setConfig(ProjectConfig config) {
		this.config = config;
	}

	public Component getAttachComponent() {
		return attachComponent;
	}

	public void setAttachComponent(Component attachComponent) {
		this.attachComponent = attachComponent;
	}
	

}
