package com.tobe.logdb;

import java.lang.reflect.Field;
import java.util.Date;

public class TestBean  extends AutoLog{
	
	@Property(ignore=true)
	public long uid = 1000;

	@Property(type=FieldType.LONGTEXT, size=1000)
	public String name;
	
	public int age;
	
	
	public Date date = new Date();
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		//这些对象反映此 Class 对象所表示的类或接口的所有可访问公共字段
		//如果该 Class 对象表示一个类，则此方法返回该类及其所有超类的公共字段
		Field[] fields = TestBean.class.getFields();
		
		//包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段
//		Field[] fields = TestBean.class.getDeclaredFields();
		for(Field f : fields){
			f.setAccessible(true);
			//f.getType().getSimpleName(), 这里可以提供字段类型,可以直接转换到MySQL字段
			System.out.println(f.getType().getSimpleName() + "\t" + f.getName() + "\t value = " + f.get(new TestBean()));
//			System.out.println(f.getGenericType());
		}
	}



	@Override
	public RollingPeriod rollingPeriod() {
		return RollingPeriod.DAY;
	}
	
	//老方案,是在公共的get方法上使用反射来获取字段值
	//优化:使用反射来分析字段,在新建log时,不必全部都加注解, 只有String类型需要指定, 不然默认为VARCHAR 长度50, 
	//int类型默认为Integer , 名字就是字段名字
	
	//在构建sql插入语句时,可以缓存创建表语句,但新加了字段要是老的缓存失效,可以使用java的serilize比较类结构是否变化,但是父类修改了字段.子类的seriId无法识别
	
	//强大的插件,写好字段定义后,使用插件生成代码,好处:
	//不使用反射构建创建sql和插入sql
	//Log缓存建表语句,记录建表语句是否执行过
	//插入语句使用插件生成一个方法构建,不使用反射,加快运行速度
}
