package ant.test;

/**
 * 认证信息
 *@author TOBE
 *
 */
public class AuthBean {
	/** 用户名 */
	private String user;
	/** 用户密码 */
	private String passwd;
	/** 主机IP */
	private String host;
	private int port = 22;
	/** 私钥文件位置 */
	private String privateKeyPath;

	private String proxy_host;
	private int proxy_port;
	private String proxy_user;
	private String proxy_passwd;

	public String getUser() {
		return user;
	}

	public AuthBean setUser(String user) {
		this.user = user;
		return this;
	}

	public String getPasswd() {
		return passwd;
	}

	public AuthBean setPasswd(String passwd) {
		this.passwd = passwd;
		return this;
	}

	public String getHost() {
		return host;
	}

	public AuthBean setHost(String host) {
		this.host = host;
		return this;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public AuthBean setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
		return this;
	}

	public String getProxy_host() {
		return proxy_host;
	}

	public AuthBean setProxy_host(String proxy_host) {
		this.proxy_host = proxy_host;
		return this;
	}

	public int getProxy_port() {
		return proxy_port;
	}

	public AuthBean setProxy_port(int proxy_port) {
		this.proxy_port = proxy_port;
		return this;
	}

	public String getProxy_user() {
		return proxy_user;
	}

	public AuthBean setProxy_user(String proxy_user) {
		this.proxy_user = proxy_user;
		return this;
	}

	public String getProxy_passwd() {
		return proxy_passwd;
	}

	public AuthBean setProxy_passwd(String proxy_passwd) {
		this.proxy_passwd = proxy_passwd;
		return this;
	}

	public int getPort() {
		return port;
	}

	public AuthBean setPort(int port) {
		this.port = port;
		return this;
	}

}
