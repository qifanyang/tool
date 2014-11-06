package ant.test;

import java.io.IOException;

public class JaveCallExe {
	public static void call(String cmd) throws IOException{
		Process exec = Runtime.getRuntime().exec(cmd);
		
//		System.out.println(exec.exitValue());
		
	}
	
	public static void main(String[] args) throws IOException {
		call("cmd /C mysqldump.exe -uroot -p123456 pgame_user>E:/sourcetreerepo/tool/tool/antdemo/self/sqldump/test.sql");
		
		//导出数据库里的所有表和数据
		//mysqldump -uroot -p123456 pgame_user>E:/sourcetreerepo/tool/tool/antdemo/self/sqldump/test.sql
		
		//导出数据库里的所有表和不包含数据 参数-d
		//mysqldump -uroot -p123456 -d pgame_user>E:/sourcetreerepo/tool/tool/antdemo/self/sqldump/test.sql
		
		//导出数据不导出结构 -t
		
//		导出特定表的结构
//		mysqldump -uroot -p -B数据库名 --table 表名 > xxx.sql
	}

}
