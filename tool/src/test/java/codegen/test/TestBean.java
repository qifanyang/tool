package codegen.test;

import java.util.Map;

public class TestBean {
	private String name;
	private int age;
	private int num;
	private Map<String, String> map;
	
	public boolean endsWith(String suffix) {
		return name.endsWith(suffix);
	}

	//Alt+Shift+S 快速生成带成员字段的构造方法
	public TestBean(String name) {
		super();
		this.name = name;
	}
	
	public TestBean(String name, int age, int num) {
		super();
		this.name = name;
		this.age = age;
		this.num = num;
	}


	@Override
	public String toString() {
		return "TestBean [name=" + name + ", age=" + age + ", num=" + num + ", map=" + map + "]";
	}
	
	

}
