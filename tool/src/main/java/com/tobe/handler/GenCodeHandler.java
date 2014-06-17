package com.tobe.handler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import com.tobe.bean.Bean;
import com.tobe.loader.MessageXMLLoader;
import com.tobe.main.ActionContext;

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
	
	@Override
	public void action(ActionContext context, Object... paras) {
		System.out.println("handler ......");
		
		Object obj = context.getNode().get("file");
		File file = (File)obj;//消息文件
		MessageXMLLoader loader = new MessageXMLLoader();
		loader.load(file.getAbsolutePath());
		
		try {
			Template temp = cfg.getTemplate("Bean.ftl");
			Iterator iter = loader.getBeans().values().iterator();
			while (iter.hasNext()) 
			{
				Bean bean = (Bean)iter.next();
//			IPackageFragmentRoot packageFragmentRoot = javaProject.findPackageFragmentRoot(srcFolder.getFullPath());
//			IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment((new StringBuilder(String.valueOf(bean.getPackageName()))).append(".bean").toString());
//			if (!packageFragment.exists())
//				packageFragmentRoot.createPackageFragment((new StringBuilder(String.valueOf(bean.getPackageName()))).append(".bean").toString(), true, null);
//			ICompilationUnit beanFile = packageFragment.getCompilationUnit((new StringBuilder(String.valueOf(bean.getBeanName()))).append(".java").toString());
			
				createBean(temp, bean);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void createBean(Template temp, Bean bean)
	{
		try
		{
			HashMap root = new HashMap();
			root.put("package", bean.getPackageName());
			root.put("className", bean.getBeanName());
			root.put("explain", bean.getExplain());
			root.put("fields", bean.getFields());
			Writer out = new StringWriter();
			temp.process(root, out);
//			packageFragment.createCompilationUnit((new StringBuilder(String.valueOf(bean.getBeanName()))).append(".java").toString(), out.toString(), false, new NullProgressMonitor());
			System.out.println(out.toString());
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (TemplateException e)
		{
			e.printStackTrace();
		}
	}
		
}
