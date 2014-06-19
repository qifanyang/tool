package com.tobe.ui.dialog;


import junit.framework.TestCase;

public class DialogTest extends TestCase{
	
	public void testShowOptionDialog(){
		System.out.println("test optiondialog....");
		String[] options = {"ok","no","yes to all","cancel"};
		int index = OptionDialog.showOptionDialog(null,OptionDialog.QUESTION, "content", "title", options, 2);
		System.out.println(index);
	}

}
