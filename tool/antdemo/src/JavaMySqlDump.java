public class JavaMySqlDump {

	public static void main(String[] args) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("cmd /C mysqldump.exe -u").append(args[9]).append("-p").append(args[1])append(" ").append(args[2]).append(">").append(args[3]);
//		call("cmd /C mysqldump.exe -uroot -p123456 pgame_user>E:/sourcetreerepo/tool/tool/antdemo/self/sqldump/test.sql");
		Runtime.getRuntime().exec(sb.toString());
	}

}
