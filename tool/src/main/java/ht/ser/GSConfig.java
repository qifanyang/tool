package ht.ser;

public class GSConfig {
//<server ip="192.168.1.122" logpasswd="123456" userpasswd="123456"/>
	private String ip;
	/**数据库用户名*/
	private String user;
	/**数据库密码*/
	private String passwd;
	/**游戏服httpServer端口*/
	private String http;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getHttp() {
		return http;
	}
	public void setHttp(String http) {
		this.http = http;
	}
	
	
}
