package ${package}.handler;

import org.apache.log4j.Logger;

<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>
import com.game.command.Handler;

public class ${className}Handler extends Handler{

	private final static Logger log = Logger.getLogger(${className}Handler.class);

	public void action(){
		try{
			${className}Message msg = (${className}Message)this.getMessage();
			//TODO 添加消息处理
			
		}catch(ClassCastException e){
			log.error(e);
		}
	}
}