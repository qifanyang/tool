//
//package com.tobe.loader;
//
//import java.io.IOException;
//import java.io.InputStream;
//import javax.xml.parsers.*;
//import org.w3c.dom.*;
//import org.xml.sax.SAXException;
//
//public class ConfigXMLLoader
//{
//
//	public ConfigXMLLoader()
//	{
//	}
//
//	public CommuicationConfig load(IFile file)
//	{
//		CommuicationConfig config;
//		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		InputStream is = file.getContents();
//		Document document = builder.parse(is);
//		Node root = document.getElementsByTagName("configs").item(0);
//		config = new CommuicationConfig();
//		for (Node node = root.getFirstChild(); node != null; node = node.getNextSibling())
//			if ("java-project".equals(node.getNodeName()))
//				config.setJavaProject(node.getTextContent());
//			else
//			if ("java-message-pool".equals(node.getNodeName()))
//				config.setJavaMessagePool(node.getTextContent());
//			else
//			if ("java-gate-project".equals(node.getNodeName()))
//				config.setJavaGateProject(node.getTextContent());
//			else
//			if ("java-gate-message-pool".equals(node.getNodeName()))
//				config.setJavaGateMessagePool(node.getTextContent());
//			else
//			if ("java-world-project".equals(node.getNodeName()))
//				config.setJavaWorldProject(node.getTextContent());
//			else
//			if ("java-world-message-pool".equals(node.getNodeName()))
//				config.setJavaWorldMessagePool(node.getTextContent());
//			else
//			if ("java-client-project".equals(node.getNodeName()))
//				config.setJavaClientProject(node.getTextContent());
//			else
//			if ("java-client-message-pool".equals(node.getNodeName()))
//				config.setJavaClientMessagePool(node.getTextContent());
//			else
//			if ("as-project".equals(node.getNodeName()))
//				config.setAsProject(node.getTextContent());
//			else
//			if ("as-message-pool".equals(node.getNodeName()))
//				config.setAsMessagePool(node.getTextContent());
//
//		is.close();
//		return config;
//		CoreException e;
//		e;
//		e.printStackTrace();
//		break MISSING_BLOCK_LABEL_406;
//		e;
//		e.printStackTrace();
//		break MISSING_BLOCK_LABEL_406;
//		e;
//		e.printStackTrace();
//		break MISSING_BLOCK_LABEL_406;
//		e;
//		e.printStackTrace();
//		return null;
//	}
//}
