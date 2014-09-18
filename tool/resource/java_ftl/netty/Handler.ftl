package ${package}.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>
import com.game.command.Handler;

/** 
 * 重复生成,注意是否覆盖添加的消息处理
 * @author With You
 * 
 */
public class ${className}Handler extends Handler{

	private final static Log log = LogFactory.getLog(${className}Handler.class);

	public void action(){
		try{
			${className}Message msg = (${className}Message)this.getMessage();
			//TODO 添加消息处理
			
		}catch(ClassCastException e){
			log.error(e);
		}
	}
}