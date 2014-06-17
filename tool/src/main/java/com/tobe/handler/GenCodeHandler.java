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

import com.tobe.actions.ActionContext;
import com.tobe.bean.Bean;
import com.tobe.bean.Field;
import com.tobe.bean.Message;
import com.tobe.loader.MessageXMLLoader;
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
	
	//bean msg handler
	@Override
	public void action(ActionContext context, Object... paras) {
		System.out.println("handler ......");
		
		Object obj = context.getNode().get("file");
		File file = (File)obj;//消息文件
		MessageXMLLoader loader = new MessageXMLLoader();
		loader.load(file.getAbsolutePath());
		
		//
		JavaProject project = new JavaProject(context.getConfig().getJavaProject());
		
		
		if (((Boolean)paras[0]).booleanValue()){//勾选了生成bean
			try {
				Template temp = cfg.getTemplate("Bean.ftl");
				Iterator iter = loader.getBeans().values().iterator();
				while (iter.hasNext()) 
				{
					Bean bean = (Bean)iter.next();
					createBean(temp, project, bean);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
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
					createMessage(temp, project, message, loader.getBeans());
				}
			} catch (Exception e) {
				// TODO: handle exception
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
					createHandler(temp, project, message);
				}
			} catch (Exception e) {
				// TODO: handle exception
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
			IFolder srcfolder = project.getFolder("src");
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
			IFolder srcfolder = project.getFolder("src");
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
			IFolder srcfolder = project.getFolder("src");
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
