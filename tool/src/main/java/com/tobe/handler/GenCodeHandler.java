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

import javax.swing.JOptionPane;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import com.tobe.actions.ActionContext;
import com.tobe.bean.Bean;
import com.tobe.bean.Field;
import com.tobe.bean.Message;
import com.tobe.loader.MessageXMLLoader;
import com.tobe.project.IFile;
import com.tobe.project.IFolder;
import com.tobe.project.IProject;
import com.tobe.project.impl.JavaProject;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GenCodeHandler implements Handler {

	private Configuration cfg;
	
	public GenCodeHandler() {
		try {
			cfg = new Configuration();
			cfg.setDefaultEncoding("UTF-8");
//			System.out.println(System.getProperty("user.dir"));
			File path = new File(System.getProperty("user.dir") + "/resource/java_ftl/");
			cfg.setDirectoryForTemplateLoading(path);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//paras参数的顺序为----->bean msg handler
	@Override
	public void action(ActionContext context, Object... paras) {
		System.out.println("handler ......");
		
		Object obj = context.getNode().get("file");
		File file = (File)obj;//消息文件
		MessageXMLLoader loader = new MessageXMLLoader();
		loader.load(file.getAbsolutePath());
		
		//
		JavaProject project = new JavaProject(context.getConfig().getJavaProject());
		IFolder srcfolder = project.getSrc();
		if(!srcfolder.exists()){
			srcfolder.create();
		}
		boolean overwrite = false;//覆盖全部
		boolean cancel = false;//取消
		//如果文件已经存在,提示对话框,选项{是(Y), 否(N), 全部覆盖, 取消}
		//因java没有支持四个选项的对话框,所以需自己实现一个
		Object[] options = {"覆盖","不覆盖"};
		if (((Boolean)paras[0]).booleanValue()){//勾选了生成bean
			try {
				Template temp = cfg.getTemplate("Bean.ftl");
				Iterator iter = loader.getBeans().values().iterator();
				while (iter.hasNext()) 
				{
					Bean bean = (Bean)iter.next();
					IFile srcfile = project.getFile((bean.getPackageName()+".bean."+bean.getBeanName()).replace(".", "/")+".java");
					if(srcfile.exists()){
						int result = JOptionPane.showConfirmDialog(null,bean.getBeanName() + "已经存在,是否覆盖?","警告[bean]", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(result == JOptionPane.OK_OPTION){
							//
							createBean(temp, project, bean);
						}
					}else{
						IFolder folder = srcfolder.getFolder(bean.getPackageName()+".bean");
						if(!folder.exists()){
							folder.create();
						}
						createBean(temp, project, bean);
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		if (((Boolean)paras[1]).booleanValue()){//勾选了生成message
			try {
				Template temp = cfg.getTemplate("Message.ftl");
				Iterator iter = loader.getMessages().iterator();
				while (iter.hasNext()) 
				{
					Message message = (Message)iter.next();
	//				if (!contains(containsMessage, message.getType()))
	//					continue;
					IFile srcfile = project.getFile((message.getPackageName()+".message."+message.getMessageName()).replace(".", "/")+"Message.java");
					if(srcfile.exists()){
						int result = JOptionPane.showConfirmDialog(null,message.getMessageName() + "已经存在,是否覆盖?","警告[message]", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if(result == JOptionPane.OK_OPTION){
							//
							createMessage(temp, project, message, loader.getBeans());
						}
					}else{
						IFolder folder = srcfolder.getFolder(message.getPackageName()+".message");
						if(!folder.exists()){
							folder.create();
						}
						createMessage(temp, project, message, loader.getBeans());
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				
		}
		
		if (((Boolean)paras[2]).booleanValue()){//勾选了生成handler
			try {
				Template temp = cfg.getTemplate("Handler.ftl");
				Iterator iter = loader.getMessages().iterator();
				while (iter.hasNext()) 
				{
					Message message = (Message)iter.next();
//					if (!contains(containsHandler, message.getType()))
//						continue;
					IFile srcfile = project.getFile((message.getPackageName()+".handler."+message.getMessageName()).replace(".", "/")+"Handler.java");
					if(srcfile.exists()){
						int result = JOptionPane.showConfirmDialog(null,message.getMessageName() + "已经存在,是否覆盖?","警告[handler]", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if(result == JOptionPane.OK_OPTION){
							createHandler(temp, project, message);
						}
					}else{
						IFolder folder = srcfolder.getFolder(message.getPackageName()+".handler");
						if(!folder.exists()){
							folder.create();
						}
						createHandler(temp, project, message);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				
		}
		
	}
	
	private void createBean(Template temp, IProject project, Bean bean) {
		try {
			HashMap<String, Object> root = new HashMap<String, Object>();
			root.put("package", bean.getPackageName());
			root.put("className", bean.getBeanName());
			root.put("explain", bean.getExplain());
			root.put("fields", bean.getFields());
			Writer out = new StringWriter();
			temp.process(root, out);
			IFolder srcfolder = project.getSrc();
			IFolder folder = srcfolder.createFolder(bean.getPackageName()+".bean");
			folder.createFile(bean.getBeanName() + ".java", out.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	private void createMessage(Template temp, IProject project, Message message, HashMap beans){
		try{
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
			Iterator iter = message.getFields().iterator();
			HashSet imports = new HashSet();
			while (iter.hasNext()) 
			{
				Field field = (Field)iter.next();
				if (beans.containsKey(field.getClassName()))
				{
					Bean bean = (Bean)beans.get(field.getClassName());
					imports.add((new StringBuilder(String.valueOf(bean.getPackageName()))).append(".bean.").append(bean.getBeanName()).toString());
				}
			}
			root.put("imports", ((Object) (imports.toArray(new String[0]))));
			Writer out = new StringWriter();
			temp.process(root, out);
			
//			packageFragment.createCompilationUnit((new StringBuilder(String.valueOf(message.getMessageName()))).append("Message.java").toString(), out.toString(), false, new NullProgressMonitor());
			IFolder srcfolder = project.getSrc();
			IFolder folder = srcfolder.createFolder(message.getPackageName()+".message");
			folder.createFile(message.getMessageName()+"Message.java", out.toString());
			out.close();
		}catch (IOException e){
			e.printStackTrace();
		}catch (TemplateException e){
			e.printStackTrace();
		}
	}

	private void createHandler(Template temp, IProject project, Message message){
		try{
			HashMap<String, Object> root = new HashMap<String, Object>();
			root.put("package", message.getPackageName());
			root.put("className", message.getMessageName());
			root.put("explain", message.getExplain());
			List imports = new ArrayList();
			imports.add((new StringBuilder(String.valueOf(message.getPackageName()))).append(".message.").append(message.getMessageName()).append("Message").toString());
			root.put("imports", imports);
			Writer out = new StringWriter();
			temp.process(root, out);
//			packageFragment.createCompilationUnit((new StringBuilder(String.valueOf(message.getMessageName()))).append("Handler.java").toString(), out.toString(), false, new NullProgressMonitor());
			IFolder srcfolder = project.getSrc();
			IFolder folder = srcfolder.createFolder(message.getPackageName()+".handler");
			folder.createFile(message.getMessageName()+"Handler.java", out.toString());
			out.close();
		}catch (IOException e){
			e.printStackTrace();
		}catch (TemplateException e){
			e.printStackTrace();
		}
	}

		
}
