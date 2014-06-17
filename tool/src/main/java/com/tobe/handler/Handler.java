package com.tobe.handler;

import com.tobe.main.ActionContext;

public interface Handler {
	
	public void action(ActionContext context, Object... paras);
}
