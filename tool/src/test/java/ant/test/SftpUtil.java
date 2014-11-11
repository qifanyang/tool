package ant.test;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.atp.AnalysisToolPak;

import ant.test.Sftp.MyProgressMonitor;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.jcraft.jsch.SftpStatVFS;
import com.jcraft.jsch.UserInfo;

/**
 * 使用jsch上传文件
 * 
 * @author TOBE
 * 
 */
public class SftpUtil {

	private static PrintStream out = System.out;

	/**
	 * 已追加的方式上传,文件为
	 */
	public static void uploadAppend(AuthBean auth, String src, String dest) {

	}

	/**
	 * 解析cmdStr
	 * @param auth
	 * @param cmdStr
	 * @throws JSchException
	 */
	public static void upload(AuthBean auth, String cmdStr) throws JSchException {
		JSch jsch = new JSch();

		if (auth.getPrivateKeyPath() != null && auth.getPrivateKeyPath().length() != 0) {
			jsch.addIdentity(auth.getPrivateKeyPath());
		}

		Session session = jsch.getSession(auth.getUser(), auth.getHost(), auth.getPort());

		session.setPassword(auth.getPasswd());

		// 代理设置不为空
		if (auth.getProxy_host() != null && auth.getProxy_user() != null) {
			// http代理服务器设置
			ProxyHTTP proxyHTTP = new ProxyHTTP(auth.getProxy_host(), auth.getProxy_port());
			proxyHTTP.setUserPasswd(auth.getProxy_user(), auth.getProxy_passwd());
			session.setProxy(proxyHTTP);
		}

		session.setUserInfo(new UserInfo() {

			@Override
			public void showMessage(String message) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean promptYesNo(String message) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean promptPassword(String message) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean promptPassphrase(String message) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getPassword() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getPassphrase() {
				return null;
			}
		});

		session.connect();

		// 创建一个sftp的channel
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp c = (ChannelSftp) channel;

		// 解析命令直接上传文件
		java.util.List<String> cmds = analysisCmd(cmdStr);
		try {
			// byte[] buf = new byte[1024];
			int i;
			String str;
			int level = 0;

			if (cmds.size() == 0)
				throw new IllegalArgumentException("cmd analysis error !");

			// 命令解析完毕
			String cmd = cmds.get(0);
			if (cmd.equals("compression")) {
				if (cmds.size() >= 2) {

					try {
						level = Integer.parseInt(cmds.get(1));
						if (level == 0) {
							session.setConfig("compression.s2c", "none");
							session.setConfig("compression.c2s", "none");
						} else {
							session.setConfig("compression.s2c", "zlib@openssh.com,zlib,none");
							session.setConfig("compression.c2s", "zlib@openssh.com,zlib,none");
						}
					} catch (Exception e) {
					}
					session.rekey();
				}
			}

			if (cmd.equals("rm") || cmd.equals("rmdir") || cmd.equals("mkdir")) {
				if (cmds.size() >= 2) {
					String path = cmds.get(1);
					try {
						if (cmd.equals("rm"))
							c.rm(path);
						else if (cmd.equals("rmdir"))
							c.rmdir(path);
						else
							c.mkdir(path);
					} catch (SftpException e) {
						System.out.println(e.toString());
					}
				}
			}
			if (cmd.equals("chgrp") || cmd.equals("chown") || cmd.equals("chmod")) {
				if (cmds.size() == 3) {
					String path = cmds.get(2);
					int foo = 0;
					if (cmd.equals("chmod")) {
						byte[] bar = cmds.get(1).getBytes();
						int k;
						for (int j = 0; j < bar.length; j++) {
							k = bar[j];
							if (k < '0' || k > '7') {
								foo = -1;
								break;
							}
							foo <<= 3;
							foo |= (k - '0');
						}
//						if (foo == -1)
//							continue;
					} else {
						try {
							foo = Integer.parseInt(cmds.get(1));
						} catch (Exception e) {
//							continue;
						}
					}
					try {
						if (cmd.equals("chgrp")) {
							c.chgrp(foo, path);
						} else if (cmd.equals("chown")) {
							c.chown(foo, path);
						} else if (cmd.equals("chmod")) {
							c.chmod(foo, path);
						}
					} catch (SftpException e) {
						System.out.println(e.toString());
					}
//					continue;
				}
			}

			if (cmd.equals("pwd") || cmd.equals("lpwd")) {
				str = (cmd.equals("pwd") ? "Remote" : "Local");
				str += " working directory: ";
				if (cmd.equals("pwd"))
					str += c.pwd();
				else
					str += c.lpwd();
				out.println(str);
			}

			if (cmd.equals("ls") || cmd.equals("dir")) {
				String path = ".";
				if (cmds.size() == 2)
					path = cmds.get(1);
				try {
					java.util.Vector vv = c.ls(path);
					if (vv != null) {
						for (int ii = 0; ii < vv.size(); ii++) {
							// out.println(vv.get(ii).toString());

							Object obj = vv.get(ii);
							if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
								out.println(((com.jcraft.jsch.ChannelSftp.LsEntry) obj).getLongname());
							}

						}
					}
				} catch (SftpException e) {
					System.out.println(e.toString());
				}
//				continue;
			}

			if (cmd.equals("lls") || cmd.equals("ldir")) {
				String path = ".";
				if (cmds.size() == 2)
					path = cmds.get(1);
				try {
					java.io.File file = new java.io.File(path);
					if (!file.exists()) {
						out.println(path + ": No such file or directory");
//						continue;
					}
					if (file.isDirectory()) {
						String[] list = file.list();
						for (int ii = 0; ii < list.length; ii++) {
							out.println(list[ii]);
						}
//						continue;
					}
					out.println(path);
				} catch (Exception e) {
					System.out.println(e);
				}
//				continue;
			}

			//文件上传和下载
			if (cmd.equals("get") || cmd.equals("get-resume") || cmd.equals("get-append") || cmd.equals("put") || cmd.equals("put-resume")
					|| cmd.equals("put-append")) {
//				if (cmds.size() != 2 && cmds.size() != 3)
//					continue;
				String p1 = cmds.get(1);
				// String p2=p1;
				String p2 = ".";
				if (cmds.size() == 3)
					p2 = cmds.get(2);
				try {
					SftpProgressMonitor monitor = new MyProgressMonitor();
					if (cmd.startsWith("get")) {
						int mode = ChannelSftp.OVERWRITE;
						if (cmd.equals("get-resume")) {
							mode = ChannelSftp.RESUME;
						} else if (cmd.equals("get-append")) {
							mode = ChannelSftp.APPEND;
						}
						c.get(p1, p2, monitor, mode);
					} else {
						int mode = ChannelSftp.OVERWRITE;
						if (cmd.equals("put-resume")) {
							mode = ChannelSftp.RESUME;
						} else if (cmd.equals("put-append")) {
							mode = ChannelSftp.APPEND;
						}
						c.put(p1, p2, monitor, mode);
						// sftp> put-append pom.xml myfile.txt 追加文件到myfile.后面
						// sftp> put pom.xml a 直接将文件放入文件夹a中
					}
				} catch (SftpException e) {
					System.out.println(e.toString());
				}
//				continue;
			}

			//
			if (cmd.equals("ln") || cmd.equals("symlink") || cmd.equals("rename") || cmd.equals("hardlink")) {
//				if (cmds.size() != 3)
//					continue;
				String p1 = cmds.get(1);
				String p2 = cmds.get(2);
				try {
					if (cmd.equals("hardlink")) {
						c.hardlink(p1, p2);
					} else if (cmd.equals("rename"))
						c.rename(p1, p2);
					else
						c.symlink(p1, p2);
				} catch (SftpException e) {
					System.out.println(e.toString());
				}
//				continue;
			}

			//
			if (cmd.equals("df")) {
//				if (cmds.size() > 2)
//					continue;
				String p1 = cmds.size() == 1 ? "." : cmds.get(1);
				SftpStatVFS stat = c.statVFS(p1);

				long size = stat.getSize();
				long used = stat.getUsed();
				long avail = stat.getAvailForNonRoot();
				long root_avail = stat.getAvail();
				long capacity = stat.getCapacity();

				System.out.println("Size: " + size);
				System.out.println("Used: " + used);
				System.out.println("Avail: " + avail);
				System.out.println("(root): " + root_avail);
				System.out.println("%Capacity: " + capacity);

//				continue;
			}

			//
			if (cmd.equals("stat") || cmd.equals("lstat")) {
//				if (cmds.size() != 2)
//					continue;
				String p1 = cmds.get(1);
				SftpATTRS attrs = null;
				try {
					if (cmd.equals("stat"))
						attrs = c.stat(p1);
					else
						attrs = c.lstat(p1);
				} catch (SftpException e) {
					System.out.println(e.toString());
				}
				if (attrs != null) {
					out.println(attrs);
				} else {
				}
//				continue;
			}

			//
			if (cmd.equals("readlink")) {
//				if (cmds.size() != 2)
//					continue;
				String p1 = cmds.get(1);
				String filename = null;
				try {
					filename = c.readlink(p1);
					out.println(filename);
				} catch (SftpException e) {
					System.out.println(e.toString());
				}
//				continue;
			}

			if (cmd.equals("realpath")) {
//				if (cmds.size() != 2)
//					continue;
				String p1 = cmds.get(1);
				String filename = null;
				try {
					filename = c.realpath(p1);
					out.println(filename);
				} catch (SftpException e) {
					System.out.println(e.toString());
				}
//				continue;
			}

			if (cmd.equals("version")) {
//				out.println("SFTP protocol version " + c.version());
//				continue;
			}
			// if (cmd.equals("help") || cmd.equals("?")) {
			// log(help);
			// continue;
			// }
			log("unimplemented command: " + cmd);
			session.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static List<String> analysisCmd(String cmdStr) {
		List<String> cmds = new ArrayList<String>();
		byte[] buf = cmdStr.getBytes(Charset.forName("utf-8"));
		int i = cmdStr.length();
		int s = 0;
		for (int ii = 0; ii < i; ii++) {
			//put aaa bbb
			if (buf[ii] == ' ') {//遇到空格开始读取前面的字符串
				if (ii - s > 0) {
					cmds.add(new String(buf, s, ii - s));
				}
				// 多个空格,这里直接跳过
				while (ii < i) {
					if (buf[ii] != ' ')
						break;
					ii++;
				}
				s = ii;
			}
		}
		if (s < i) {
			cmds.add(new String(buf, s, i - s));
		}

		return cmds;
	}

	public static void log(String log) {
		System.out.println(log);
	}

	public void echo(String msg) {

	}
	
	public static void main(String[] args) throws JSchException {
		AuthBean auth = new AuthBean();
		auth.setUser("server");
		auth.setHost("192.168.1.212");
		
		String cmdStr = "put pom.xml a";
		System.out.println(analysisCmd(cmdStr));
		
		upload(auth, cmdStr);
	}

}
