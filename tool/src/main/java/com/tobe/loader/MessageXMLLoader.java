package com.tobe.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tobe.bean.Bean;
import com.tobe.bean.Field;
import com.tobe.bean.Message;

/**
 * 加载xml消息定义文件,DOM方式,不依赖第三方包
 */
public class MessageXMLLoader {

	private HashMap<String, Bean> beans;
	private List<Message> messages;

	public MessageXMLLoader() {
		beans = new HashMap<String, Bean>();
		messages = new ArrayList<Message>();
	}

	public void load(String file) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputStream is = new FileInputStream(new File(file));
			Document document = builder.parse(is);
			Node root = document.getElementsByTagName("messages").item(0);
			//包名
			String packageName = root.getAttributes().getNamedItem("package").getTextContent();
			//模块ID
			String packageId = root.getAttributes().getNamedItem("id").getTextContent();
			NodeList nodes = document.getElementsByTagName("bean");
			
			for (int i = 0; i < nodes.getLength(); i++) {
				//解析bean
				Node child = nodes.item(i);
				Bean bean = new Bean();
				NamedNodeMap beanAttributes = child.getAttributes();
				bean.setBeanName(beanAttributes.getNamedItem("name").getTextContent());
				bean.setPackageName(packageName);
				bean.setExplain(beanAttributes.getNamedItem("explain").getTextContent());
				bean.setFields(new ArrayList<Field>());
				for (Node node = child.getFirstChild(); node != null; node = node.getNextSibling())
					if ("field".equals(node.getNodeName())) {
						//基本字段,byge,int,String,short,long
						NamedNodeMap fieldAttributes = node.getAttributes();
						Field field = new Field();
						field.setClassName(fieldAttributes.getNamedItem("class").getTextContent());
						field.setName(fieldAttributes.getNamedItem("name").getTextContent());
						field.setExplain(fieldAttributes.getNamedItem("explain").getTextContent());
						bean.getFields().add(field);
					} else if ("list".equals(node.getNodeName())) {
						//list
						NamedNodeMap fieldAttributes = node.getAttributes();
						Field field = new Field();
						field.setClassName(fieldAttributes.getNamedItem("class").getTextContent());
						field.setName(fieldAttributes.getNamedItem("name").getTextContent());
						field.setExplain(fieldAttributes.getNamedItem("explain").getTextContent());
						field.setListType(1);
						bean.getFields().add(field);
					}

				beans.put(bean.getBeanName(), bean);
			}

			nodes = document.getElementsByTagName("message");
			for (int i = 0; i < nodes.getLength(); i++) {
				//解析message
				Node child = nodes.item(i);
				Message message = new Message();
				NamedNodeMap msgAttr = child.getAttributes();//消息属性
				//id = pacakgeId + msgId
				message.setId(Integer.parseInt((new StringBuilder(packageId)).append(msgAttr.getNamedItem("id").getTextContent()).toString()));
				
				message.setMessageName(msgAttr.getNamedItem("name").getTextContent());
				message.setType(msgAttr.getNamedItem("type").getTextContent());
				message.setPackageName(packageName);
				message.setExplain(msgAttr.getNamedItem("explain").getTextContent());
				if (msgAttr.getNamedItem("queue") != null)
					message.setQueue(msgAttr.getNamedItem("queue").getTextContent());
				if (msgAttr.getNamedItem("server") != null)
					message.setServer(msgAttr.getNamedItem("server").getTextContent());
				
				message.setFields(new ArrayList<Field>());
				for (Node node = child.getFirstChild(); node != null; node = node.getNextSibling())
					if ("field".equals(node.getNodeName())) {
						NamedNodeMap fieldAttributes = node.getAttributes();
						Field field = new Field();
						field.setClassName(fieldAttributes.getNamedItem("class").getTextContent());
						field.setName(fieldAttributes.getNamedItem("name").getTextContent());
						field.setExplain(fieldAttributes.getNamedItem("explain").getTextContent());
						message.getFields().add(field);
					} else if ("list".equals(node.getNodeName())) {
						NamedNodeMap fieldAttributes = node.getAttributes();
						Field field = new Field();
						field.setClassName(fieldAttributes.getNamedItem("class").getTextContent());
						field.setName(fieldAttributes.getNamedItem("name").getTextContent());
						field.setExplain(fieldAttributes.getNamedItem("explain").getTextContent());
						field.setListType(1);
						message.getFields().add(field);
					}

				messages.add(message);
			}

			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Bean> getBeans() {
		return beans;
	}

	public List<Message> getMessages() {
		return messages;
	}
}
