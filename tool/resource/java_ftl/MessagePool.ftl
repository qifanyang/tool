package ${package};
	
import java.util.HashMap;<#if catalogs??>
<#list catalogs as catalog>
import ${catalog.message};
<#if catalog.handler??>
import ${catalog.handler};
</#if>
</#list>
</#if>
import com.game.command.Handler;
import com.game.message.Message;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 引用类列表
 */
public class MessagePool {
	//消息类字典
	HashMap<Integer, Class<?>> messages = new HashMap<Integer, Class<?>>();
	//处理类字典
	HashMap<Integer, Class<?>> handlers = new HashMap<Integer, Class<?>>();
	
	public MessagePool(){
		<#if catalogs??>
		<#list catalogs as catalog>
		<#if catalog.handler??>
		register(${catalog.id?c}, ${catalog.name}Message.class, ${catalog.name}Handler.class);
		<#else>
		register(${catalog.id?c}, ${catalog.name}Message.class, null);
		</#if>
		</#list>
		</#if>
	}
	
	private void register(int id, Class<?> messageClass, Class<?> handlerClass){
		messages.put(id, messageClass);
		if(handlerClass!=null) handlers.put(id, handlerClass);
	}
	
	/**
	 * 获取消息体
	 * @param id
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Message getMessage(int id) throws InstantiationException, IllegalAccessException{
		if(!messages.containsKey(id)){
			return null;
		}else{
			return (Message)messages.get(id).newInstance();
		}
	}
	
	/**
	 * 获取处理函数
	 * @param id
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Handler getHandler(int id) throws InstantiationException, IllegalAccessException{
		if(!handlers.containsKey(id)){
			return null;
		}else{
			return (Handler)handlers.get(id).newInstance();
		}
	}
}