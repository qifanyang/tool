package com.tobe.handler;

import com.tobe.actions.ActionContext;

public interface Handler {
	
	public void action(ActionContext context, Object... paras);
}
