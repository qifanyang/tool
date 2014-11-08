import java.nio.charset.Charset;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class SayTask extends Task {

	private String msg;

	@Override
	public void execute() throws BuildException {
		System.out.println(msg);

		String message = getProject().getProperty("ant.project.name");

		// Task's log method
		System.out.println("Here is project '" + message + "'.");

		// where this task is used?
		System.out.println("I am used in: " + getLocation());
	}

	public void setMsg(String msg) {
//		byte[] bytes = msg.getBytes(Charset.forName("utf-8"));
//		this.msg = new String(bytes,Charset.forName("utf-8"));
		this.msg = msg;
	}
}
