package com.tobe.util;

public class SomeBean implements BeanValidate{

	public void set() {

	}
	
	public boolean validate(int t) {
		//该方法可以修改,生成bean.如过该bean存在,保留该方法
		System.out.println("}bean valiate run.....");
		if(true){
			System.out.println("ok");
		}
		return false;
	}

	@Override
	public boolean validate  (  ) {
		//该方法可以修改,生成bean.如过该bean存在,保留该方法
		System.out.println("}bean valiate run.....");
		if(true){
			System.out.println("ok");
		}
		//dfgdfg
		return false;
	}
	

	private int testone(){
		return 0;
	}

}
