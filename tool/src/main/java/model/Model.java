package model;

/**游戏配置接口,继承该接口,并位于该包下的游戏配置类将会全部加载*/
public interface Model {
	
	public String table();
	
	public Integer getId();
	
	public String getName();
}
