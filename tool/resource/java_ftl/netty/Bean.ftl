<#import "Class.ftl" as Class>
package ${package}.bean;

<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>
<#list fields as field>
<#if field.listType==1>
import java.util.List;
import java.util.ArrayList;
<#break>
</#if>
</#list>

import com.game.message.Bean;

import org.jboss.netty.buffer.ChannelBuffer;

/** 
 * @author Auto Generated , Do Not Manually Modify
 * 
 * @version 1.0.0
 * 
 * ${explain}
 */
public class ${className} extends Bean {

	<#list fields as field>
	<#if field.listType==1>
	//${field.explain}
	private List<${.globals[field.className]!field.className}> ${field.name} = new ArrayList<${.globals[field.className]!field.className}>();
	<#else>
	//${field.explain}
	private ${field.className} ${field.name};
	
	</#if>
	</#list>
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ChannelBuffer buf){
		<#list fields as field>
		<#if field.listType==1>
		//${field.explain}
		writeShort(buf, ${field.name}.size());
		for (int i = 0; i < ${field.name}.size(); i++) {
			<#if field.className=="int">
			writeInt(buf, ${field.name}.get(i));
			<#elseif field.className=="short">
			writeShort(buf, ${field.name}.get(i));
			<#elseif field.className=="float">
			writeFloat(buf, ${field.name}.get(i));
			<#elseif field.className=="long">
			writeLong(buf, ${field.name}.get(i));
			<#elseif field.className=="byte">
			writeByte(buf, ${field.name}.get(i));
			<#elseif field.className=="String">
			writeString(buf, ${field.name}.get(i));
			<#else>
			writeBean(buf, ${field.name}.get(i));
			</#if>
		}
		<#else>
		//${field.explain}
		<#if field.className=="int">
		writeInt(buf, this.${field.name});
		<#elseif field.className=="short">
		writeShort(buf, this.${field.name});
		<#elseif field.className=="float">
		writeFloat(buf, this.${field.name});
		<#elseif field.className=="long">
		writeLong(buf, this.${field.name});
		<#elseif field.className=="byte">
		writeByte(buf, this.${field.name});
		<#elseif field.className=="String">
		writeString(buf, this.${field.name});
		<#else>
		writeBean(buf, this.${field.name});
		</#if>
		</#if>
		</#list>
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(ChannelBuffer buf){
		<#list fields as field>
		<#if field.listType==1>
		//${field.explain}
		int ${field.name}_length = readShort(buf);
		for (int i = 0; i < ${field.name}_length; i++) {
			<#if field.className=="int">
			${field.name}.add(readInt(buf));
			<#elseif field.className=="short">
			${field.name}.add(readShort(buf));
			<#elseif field.className=="float">
			${field.name}.add(readFloat(buf));
			<#elseif field.className=="long">
			${field.name}.add(readLong(buf));
			<#elseif field.className=="byte">
			${field.name}.add(readByte(buf));
			<#elseif field.className=="String">
			${field.name}.add(readString(buf));
			<#else>
			${field.name}.add((${field.className})readBean(buf, ${field.className}.class));
			</#if>
		}
		<#else>
		//${field.explain}
		<#if field.className=="int">
		this.${field.name} = readInt(buf);
		<#elseif field.className=="short">
		this.${field.name} = readShort(buf);
		<#elseif field.className=="float">
		this.${field.name} = readFloat(buf);
		<#elseif field.className=="long">
		this.${field.name} = readLong(buf);
		<#elseif field.className=="byte">
		this.${field.name} = readByte(buf);
		<#elseif field.className=="String">
		this.${field.name} = readString(buf);
		<#else>
		this.${field.name} = (${field.className})readBean(buf, ${field.className}.class);
		</#if>
		</#if>
		</#list>
		return true;
	}
	
	<#list fields as field>
	<#if field.listType==1>
	/**
	 * get ${field.explain}
	 * @return 
	 */
	public List<${.globals[field.className]!field.className}> get${field.name?cap_first}(){
		return ${field.name};
	}
	
	/**
	 * set ${field.explain}
	 */
	public void set${field.name?cap_first}(List<${.globals[field.className]!field.className}> ${field.name}){
		this.${field.name} = ${field.name};
	}
	
	<#else>
	/**
	 * get ${field.explain}
	 * @return 
	 */
	public ${field.className} get${field.name?cap_first}(){
		return ${field.name};
	}
	
	/**
	 * set ${field.explain}
	 */
	public void set${field.name?cap_first}(${field.className} ${field.name}){
		this.${field.name} = ${field.name};
	}
	
	</#if>
	</#list>
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		<#list fields as field>
		<#if field.listType==1>
		//${field.explain}
		buf.append("${field.name}:{");
		for (int i = 0; i < ${field.name}.size(); i++) {
			<#if field.className=="int">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="short">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="float">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="long">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="byte">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.className=="String">
			buf.append(${field.name}.get(i).toString() +",");
			<#else>
			buf.append(${field.name}.get(i).toString() +",");
			</#if>
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		<#else>
		//${field.explain}
		<#if field.className=="int">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="short">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="float">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="long">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="byte">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.className=="String">
		if(this.${field.name}!=null) buf.append("${field.name}:" + ${field.name}.toString() +",");
		<#else>
		if(this.${field.name}!=null) buf.append("${field.name}:" + ${field.name}.toString() +",");
		</#if>
		</#if>
		</#list>
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}