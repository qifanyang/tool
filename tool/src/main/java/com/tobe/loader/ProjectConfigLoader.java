package com.tobe.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import com.tobe.config.ProjectConfig;

/**
 * 项目配置文件加载
 *@author TOBE
 *
 */
public class ProjectConfigLoader {

	public ProjectConfigLoader() {
	}

	public ProjectConfig load(File file) {
		try {
			ProjectConfig config = new ProjectConfig();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputStream is = new FileInputStream(file);
			Document document = builder.parse(is);
			Node root = document.getElementsByTagName("configs").item(0);
			for (Node node = root.getFirstChild(); node != null; node = node.getNextSibling()) {
				if ("java-project".equals(node.getNodeName())) {
					config.setJavaProject(node.getTextContent());
				} else if ("java-message-pool".equals(node.getNodeName())) {
					config.setJavaMessagePool(node.getTextContent());
				} else if ("java-gate-project".equals(node.getNodeName())) {
					config.setJavaGateProject(node.getTextContent());
				} else if ("java-gate-message-pool".equals(node.getNodeName())) {
					config.setJavaGateMessagePool(node.getTextContent());
				} else if ("java-world-project".equals(node.getNodeName())) {
					config.setJavaWorldProject(node.getTextContent());
				} else if ("java-world-message-pool".equals(node.getNodeName())) {
					config.setJavaWorldMessagePool(node.getTextContent());
				} else if ("java-client-project".equals(node.getNodeName())) {
					config.setJavaClientProject(node.getTextContent());
				} else if ("java-client-message-pool".equals(node.getNodeName())) {
					config.setJavaClientMessagePool(node.getTextContent());
				} else if ("as-project".equals(node.getNodeName())) {
					config.setAsProject(node.getTextContent());
				} else if ("as-message-pool".equals(node.getNodeName())) {
					config.setAsMessagePool(node.getTextContent());
				}
			}

			is.close();
			return config;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
