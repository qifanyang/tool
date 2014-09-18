package com.tobe.handler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tobe.actions.ActionContext;
import com.tobe.bean.Bean;
import com.tobe.bean.Field;
import com.tobe.bean.Message;
import com.tobe.loader.MessageXMLLoader;
import com.tobe.project.IFolder;
import com.tobe.project.IProject;
import com.tobe.project.impl.JavaProject;
import com.tobe.ui.dialog.OptionDialog;
import com.tobe.util.UI;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CodeBuilderHandler implements Handler {

	private static final Log log = LogFactory.getLog(CodeBuilderHandler.class);
	
	private Configuration cfg;

	public CodeBuilderHandler() {
		
	}

	// paras参数的顺序为----->bean msg handler
	@Override
	public void action(ActionContext context, Object... paras) {

		try {
			cfg = new Configuration();
			cfg.setDefaultEncoding("UTF-8");
			// System.out.println(System.getProperty("user.dir"));
//			File path = new File(System.getProperty("user.dir") + "/resource/java_ftl/mina");
			File path = new File(System.getProperty("user.dir") + "/resource/java_ftl/" + context.getConfig().getNio().toLowerCase());
			// File path = new File(System.getProperty("user.dir") +
			// "/resource/as_ftl/");
			
			// 在插件中寻找资源文件位置
			// org.osgi.framework.Bundle bundle =
			// Platform.getBundle("myplugin");
			// URL url = FileLocator.find(bundle, new
			// Path("./resource/java_ftl/"), null);
			// url = FileLocator.toFileURL(url);
			// path = new File(url.getPath());

			cfg.setDirectoryForTemplateLoading(path);
			cfg.setObjectWrapper(new DefaultObjectWrapper());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Object obj = context.getNode().get("file");
		File file = (File) obj;// 消息文件
		MessageXMLLoader loader = new MessageXMLLoader();
		loader.load(file.getAbsolutePath());

		// commConfig里面有项目信息
		// 这里取取项目信息
		JavaProject project = new JavaProject(context.getConfig().getJavaProject(), false);
		IFolder srcfolder = project.getSourceCode();
		if (!srcfolder.exists()) {
			srcfolder.create();
		}
		// 如果文件已经存在,提示对话框,选项{是(Y), 否(N), 全部覆盖, 取消}
		// 因java没有支持四个选项的对话框,所以需自己实现一个
		// Object[] options = {"覆盖","不覆盖"};
		String[] options = { UI.translate("ok"), UI.translate("no"), UI.translate("yestoalloverride"), UI.translate("cancel") };
		if (((Boolean) paras[0]).booleanValue()) {// 勾选了生成bean
			boolean overwrite = false;// 覆盖全部
			boolean cancel = false;// 取消
			try {
				Template temp = cfg.getTemplate("Bean.ftl");
				Iterator<?> iter = loader.getBeans().values().iterator();
				while (iter.hasNext() && !cancel) {
					Bean bean = (Bean) iter.next();
					File srcfile = project.getFile((bean.getPackageName() + ".bean." + bean.getBeanName()).replace(".", "/") + ".java");
					if (srcfile.exists() && !overwrite) {
						// int result =
						// JOptionPane.showConfirmDialog(null,bean.getBeanName()
						// + "已经存在,是否覆盖?","警告[bean]",
						// JOptionPane.YES_NO_CANCEL_OPTION,
						// JOptionPane.QUESTION_MESSAGE);
						int result = OptionDialog.showOptionDialog(context.getAttachComponent(), OptionDialog.QUESTION,
								bean.getBeanName() + UI.translate("overridewarn"), UI.translate("warn") + "[bean]", options, 0);
						System.out.println(result);
						if (result == 0) {
							// ok
							createBean(temp, project, bean);
						}
						if (result == 2) {
							overwrite = true;// yes to all
						}
						if (result == 3) {
							cancel = true;// cancel
						}
					} else {
						createBean(temp, project, bean);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (((Boolean) paras[1]).booleanValue()) {// 勾选了生成message
			try {
				Template temp = cfg.getTemplate("Message.ftl");
				Iterator<?> iter = loader.getMessages().iterator();
				boolean overwrite = false;// 覆盖全部
				boolean cancel = false;// 取消
				while (iter.hasNext() && !cancel) {
					Message message = (Message) iter.next();
					// if (!contains(containsMessage, message.getType()))
					// continue;
					File srcfile = project.getFile((message.getPackageName() + ".message." + message.getMessageName()).replace(".", "/")
							+ "Message.java");
					if (srcfile.exists() && !overwrite) {
						int result = OptionDialog.showOptionDialog(context.getAttachComponent(), OptionDialog.QUESTION,
								message.getMessageName() + UI.translate("overridewarn"), UI.translate("warn") + "[Message]", options, 0);
						System.out.println(result);
						if (result == 0) {
							// ok
							createMessage(temp, project, message, loader.getBeans());
						}
						if (result == 2) {
							overwrite = true;// yes to all
						}
						if (result == 3) {
							cancel = true;// cancel
						}
					} else {
						createMessage(temp, project, message, loader.getBeans());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (((Boolean) paras[2]).booleanValue()) {// 勾选了生成handler
			try {
				Template temp = cfg.getTemplate("Handler.ftl");
				Iterator<?> iter = loader.getMessages().iterator();
				boolean overwrite = false;// 覆盖全部
				boolean cancel = false;// 取消
				while (iter.hasNext() && !cancel) {
					Message message = (Message) iter.next();
					// if (!contains(containsHandler, message.getType()))
					// continue;
					File srcfile = project.getFile((message.getPackageName() + ".handler." + message.getMessageName()).replace(".", "/")
							+ "Handler.java");
					if (srcfile.exists() && !overwrite) {
						int result = OptionDialog.showOptionDialog(context.getAttachComponent(), OptionDialog.QUESTION,
								message.getMessageName() + UI.translate("overridewarn"), UI.translate("warn") + "[Handler]", options, 0);
						System.out.println(result);
						if (result == 0) {
							// ok
							createHandler(temp, project, message);
						}
						if (result == 2) {
							overwrite = true;// yes to all
						}
						if (result == 3) {
							cancel = true;// cancel
						}
					} else {
						createHandler(temp, project, message);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private void createBean(Template temp, IProject project, Bean bean) {
		log.info("创建Bean...........");
		try {
			HashMap<String, Object> root = new HashMap<String, Object>();
			root.put("package", bean.getPackageName());
			root.put("className", bean.getBeanName());
			root.put("explain", bean.getExplain());
			root.put("fields", bean.getFields());
			Writer out = new StringWriter();
			temp.process(root, out);
			IFolder srcfolder = project.getSourceCode();
			IFolder folder = srcfolder.createSubFolder(bean.getPackageName() + ".bean");
			folder.createSubFile(bean.getBeanName() + ".java", out.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		log.info("创建Bean完成...........");
	}

	private void createMessage(Template temp, IProject project, Message message, HashMap<String, Bean> beans) {
		log.info("创建Message..........." + message.getMessageName());
		if(message.getMessageName().equalsIgnoreCase("ResBuffs")){
			System.out.println("");
		}
		try {
			HashMap<String, Object> root = new HashMap<String, Object>();
			root.put("messageId", Integer.valueOf(message.getId()));
			root.put("package", message.getPackageName());
			root.put("className", message.getMessageName());
			root.put("explain", message.getExplain());
			root.put("fields", message.getFields());
			if (message.getQueue() != null)
				root.put("messageQueue", (new StringBuilder("\"")).append(message.getQueue()).append("\"").toString());
			if (message.getServer() != null)
				root.put("messageServer", (new StringBuilder("\"")).append(message.getServer()).append("\"").toString());

			Iterator<Field> iter = message.getFields().iterator();
			HashSet<String> imports = new HashSet<String>();
			while (iter.hasNext()) {
				//遍历字段,确定import
				Field field = iter.next();
				if (beans.containsKey(field.getClassName())) {
					//如果消息的字段中有Bean,因为bean不在同一目录下,需导入需要加bean
					Bean bean = beans.get(field.getClassName());
					imports.add((new StringBuilder(String.valueOf(bean.getPackageName()))).append(".bean.").append(bean.getBeanName()).toString());
				}
			}
			root.put("imports", imports.toArray(new String[0]));
			Writer out = new StringWriter();
			temp.process(root, out);

			// packageFragment.createCompilationUnit((new
			// StringBuilder(String.valueOf(message.getMessageName()))).append("Message.java").toString(),
			// out.toString(), false, new NullProgressMonitor());
			IFolder srcfolder = project.getSourceCode();
			IFolder folder = srcfolder.createSubFolder(message.getPackageName() + ".message");
			folder.createSubFile(message.getMessageName() + "Message.java", out.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		log.info("创建Message完成...........");
	}

	/**
	 * 一个消息对应一个handler
	 * @param temp
	 * @param project
	 * @param message
	 */
	private void createHandler(Template temp, IProject project, Message message) {
		log.info("创建Handler...........");
		try {
			HashMap<String, Object> root = new HashMap<String, Object>();
			root.put("package", message.getPackageName());
			root.put("className", message.getMessageName());
			root.put("explain", message.getExplain());
			List<String> imports = new ArrayList<String>();
			imports.add((new StringBuilder(message.getPackageName())).append(".message.").append(message.getMessageName()).append("Message").toString());
			root.put("imports", imports);
			Writer out = new StringWriter();
			temp.process(root, out);
			// packageFragment.createCompilationUnit((new
			// StringBuilder(String.valueOf(message.getMessageName()))).append("Handler.java").toString(),
			// out.toString(), false, new NullProgressMonitor());
			IFolder srcfolder = project.getSourceCode();
			IFolder folder = srcfolder.createSubFolder(message.getPackageName() + ".handler");
			folder.createSubFile(message.getMessageName() + "Handler.java", out.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		log.info("创建Handler完成...........");
	}

}
