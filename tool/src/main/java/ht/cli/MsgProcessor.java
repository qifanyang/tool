package ht.cli;

//import ht.HtUtils;
import ht.MessageProcessor;
import ht.msg.IMessage;
import ht.msg.JsonMsg;
import ht.msg.MsgCode;
import ht.msg.ReqItemMsg;
import ht.msg.ReqMailMsg;
import ht.msg.ReqNoticeMsg;
import ht.msg.ReqSLMsg;
import ht.msg.ResActivityMsg;
import ht.msg.ResNoticeMsg;
import ht.msg.TestMsg;
import ht.vo.ActivityVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.kueem.json.JSONException;
//import com.kueem.pgame.activity.MerchantActivityBean;
//import com.kueem.pgame.activity.manager.MerchantActivityManager;
//import com.kueem.pgame.biz.constant.ChatConstant;
//import com.kueem.pgame.biz.service.ServiceFactory;
//import com.kueem.pgame.common.enums.MailKind;
//import com.kueem.pgame.common.enums.Resource;
//import com.kueem.pgame.common.localcache.LocalcacheManager;
//import com.kueem.pgame.common.localcache.UserDataCache;
//import com.kueem.pgame.common.model.helper.Chat;
//import com.kueem.pgame.common.model.helper.RewardHelper;
//import com.kueem.pgame.common.model.helper.mail.Mail;
//import com.kueem.pgame.common.model.helper.mail.MailDescriber;
//import com.kueem.pgame.util.LoggerManager;



public class MsgProcessor implements MessageProcessor<JsonMsg> {
//	private static final Log log = LogFactory.getLog(MsgProcessor.class);
	
	private final Executor msgexecutor = Executors.newSingleThreadExecutor();
	private boolean isAuth;//是否通过验证
	
	//DefaultFirewallFilter  在建立连接的时候判断ip是否合法.防止恶意建立连接
	@Override
	public void onMessage(JsonMsg msg) {
		MsgProcessorTask task = new MsgProcessorTask(msg);
		msgexecutor.execute(task);
	}
	
	
   class MsgProcessorTask implements Runnable{

		private JsonMsg msg;
		public MsgProcessorTask(JsonMsg msg){
			this.msg = msg;
		}
		
		@Override
		public void run() {
			MsgProcessor.this.process(msg);
		}
		
	}
	
	
	private void sendMessage(IMessage msg) {
		BackendClient.getMe().sendMessage(msg);
	}
	
	
	/**来自后台的消息处理*/
	private void process(JsonMsg msg){
		int id = msg.getId();
		switch (id) {
		case MsgCode.TEST:
			TestMsg test = (TestMsg)msg.getMsg();
//			LoggerManager.Backend.error(test.getI());
//			LoggerManager.Backend.error(test.getName());
			
			
			break;
		case MsgCode.TEST_1://测试消息
//			Test1Msg testMsg = new Test1Msg();
//			JsonMsg jsonmsg = new JsonMsg();
//			jsonmsg.setId(testMsg.getId());
//			jsonmsg.setMsg(testMsg);
//			onMessage(jsonmsg);
			break;
		case MsgCode.NOTICE://游戏公告
			ReqNoticeMsg notice = (ReqNoticeMsg)msg.getMsg();
//			LoggerManager.Backend.error("收到公告消息 : --->" + notice.toString());
//			Chat chat = new Chat("", "", "", ChatConstant.SYSTEM, notice.getContent());
//    		ServiceFactory.getChatService().allChat(chat);
			break;
		case MsgCode.MAIL:
			ReqMailMsg mailmsg = (ReqMailMsg) msg.getMsg();
			sendBaseMail(mailmsg);
			break;
		case MsgCode.MAIL_ITEM:
			ReqItemMsg itemmsg = (ReqItemMsg) msg.getMsg();
//			sendItemMail(itemmsg);
			break;
		case MsgCode.ACTIVITY:
			ResActivityMsg activity = (ResActivityMsg) msg.getMsg();
//			proActivity(activity);
			break;
		case MsgCode.SAY_OR_LOGIN:
			ReqSLMsg reqSLMsg = (ReqSLMsg) msg.getMsg();
//			proSayOrLogin(reqSLMsg);
			break;
		
		default:
//			LoggerManager.Backend.error("未处理的后台消息 , code = " + id);
			break;
		}
	}
	
	
	
	private void sendBaseMail(ReqMailMsg mailmsg){
//		if(mailmsg.isAll()){//全服发送邮件,游戏服赞不支持,后面实现
//			LocalcacheManager.getInstance();
//			ConcurrentMap<String, UserDataCache> alluser = LocalcacheManager.getAllUserCacheMap();
//			Iterator<String> it = alluser.keySet().iterator();
//			while(it.hasNext()){
//				String uid = it.next();
//				Mail mail = makeBaseMail(mailmsg);
//				try {
//					ServiceFactory.getMailService().SendSystemMail(mail,uid);
//				} catch (JSONException e) {
//					LoggerManager.Backend.error("发送全服邮件异常", e);
//				}
//			}
//		}else{//给单个人或多个人发送
//			if(StringUtils.isNotEmpty(mailmsg.getUid())){
//				//uid可能是多个,用逗号隔开
//				String uids = mailmsg.getUid();
//				String[] uidList = uids.split(",|，");
//				for(String uid : uidList){
//					Mail mail = makeBaseMail(mailmsg);
//					try {
//						ServiceFactory.getMailService().SendSystemMail(mail,uid.trim());
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		}
	}
	
//	private Mail makeBaseMail(ReqMailMsg mailmsg){
//		MailDescriber describer=MailDescriber.rewardDescriber(MailKind.backendMail);
//		describer.setContent(mailmsg.getContent());
//		Mail mail = Mail.createSystemMail(describer, null);
//		mail.setTittle(mailmsg.getTitle());
//		return mail;
//	}
	
	
	
//	private void sendItemMail(ReqItemMsg itemmsg){
//			if(itemmsg.isAll()){//全服发送邮件
//				LocalcacheManager.getInstance();
//				ConcurrentMap<String, UserDataCache> alluser = LocalcacheManager.getAllUserCacheMap();
//				Iterator<String> it = alluser.keySet().iterator();
//				while(it.hasNext()){
//					String uid = it.next();
//					Mail itemMail = makeItemMail(itemmsg);
//					try {
//						ServiceFactory.getMailService().SendSystemMail(itemMail,uid);
//					} catch (JSONException e) {
//						LoggerManager.Backend.error("发送全服邮件异常", e);
//					}
//				}
//			}else{//给单个人或多个人发送
//				if(StringUtils.isNotEmpty(itemmsg.getUid())){
//					//uid可能是多个,用逗号隔开
//					String uids = itemmsg.getUid();
//					String[] uidList = uids.split(",|，");
//					
////					String items = itemmsg.getItems();//111111,1;2222222,2  道具格式
////					String[] itemlist = items.split(";|；");
////					
////					int cash = itemmsg.getCash();
////					int coin = itemmsg.getCoin();
//					
//					for(String uid : uidList){
//						Mail mail = makeItemMail(itemmsg);
//						try {
//							ServiceFactory.getMailService().SendSystemMail(mail,uid);
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//					
//				}
//			}
//
//	}
//	
//	//构建道具邮件
//	private Mail makeItemMail(ReqItemMsg itemmsg){
//		String items = itemmsg.getItems();//111111,1;2222222,2  道具格式
//		String[] itemlist = items.split(";|；");
//		
//		int cash = itemmsg.getCash();
//		int coin = itemmsg.getCoin();
//		
//		MailDescriber describer=MailDescriber.rewardDescriber(MailKind.backendMail);
//		describer.setContent(itemmsg.getContent());
//		List<RewardHelper> rewards = new ArrayList<RewardHelper>();
//		//附件,假如是给全服发的话,不在这里频繁的拆字符串
//		for(String itemstr : itemlist){
//			String[] ii = itemstr.split(",|，");
//			if(ii.length == 2){
//				rewards.add(RewardHelper.create(Resource.item, Integer.parseInt(ii[0].trim()), Integer.parseInt(ii[1].trim())));
//			}
//		}
//		
//		if(cash > 4999){//音量暂时用 140501303 5000的
//			int cashnum = cash/5000;
//			rewards.add(RewardHelper.create(Resource.item, 140501303, cashnum));
//		}
//		
//		if(coin > 10){
//			int coinnum = coin / 10;//暂时全用10元宝的 //140401501
//			rewards.add(RewardHelper.create(Resource.item, 140401501, coinnum));
//		}
//		
//		Mail mail = Mail.createSystemMail(describer, rewards);
//		mail.setTittle(itemmsg.getTitle());
//		
//		return mail;
//	}
//
//	/**处理后台活动推送*/
//	private void proActivity(ResActivityMsg msg){
//		List<ActivityVO> aclist = msg.getAc();
//		
//		if(aclist != null && aclist.size() > 0){
//			ArrayList<MerchantActivityBean> arrayList = new ArrayList<MerchantActivityBean>();
//			for(ActivityVO vo : aclist){
//				arrayList.add(HtUtils.covent(vo));
//			}
//			MerchantActivityManager.getInstance().setActivityList(arrayList);
//			MerchantActivityManager.getInstance().init();
//			LoggerManager.Print.error("收到后台活动数据");
//		}else{
//			LoggerManager.Print.error("收到后台活动数据,活动数据有问题");
//		}
//	}
//	
//	
//	private void proSayOrLogin(ReqSLMsg msg){
//		//服务器接收数据格式:sid,uid,operate(1:禁言,2:解除禁言,3:冻结,4:解除冻结),endtime
//		String type = msg.getType();
//		Date time = null ;
//		if(StringUtils.isNotEmpty(msg.getTime())){
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			try {
//				time = sdf.parse(msg.getTime());
//			} catch (ParseException e) {
//				LoggerManager.Backend.error("禁言冻结日期出错", e);
//				return;
//			}
//		}
//		
//		if(type.equalsIgnoreCase("2")  || type.equalsIgnoreCase("4") || StringUtils.isEmpty(msg.getTime())){
//			//时间为空的话
//			Calendar calendar = Calendar.getInstance();
//			calendar.set(1970, 1, 1, 1, 1);
//			time = calendar.getTime();
//		}
//		
//		UserDataCache up = LocalcacheManager.getInstance().getUserDataCache(msg.getUid());
//		if(up == null){
//			LoggerManager.Backend.error("禁言或冻结找不到玩家数据, uid = " + msg.getUid());
//			return;
//		}
//		if(time == null){
//			LoggerManager.Backend.error("禁言或冻结时间为空");
//			return ;
//		}
//		if(type.equalsIgnoreCase("1")){
//			//禁言
//			up.getUserProfile().setMuteEndTime(time);
//		}else if(type.equalsIgnoreCase("2")){
//			//解除禁言
//			up.getUserProfile().setMuteEndTime(time);
//		}else if(type.equalsIgnoreCase("3")){
//			//冻结
//			up.getUserProfile().setFreezeAccountEndTime(time);
//		}else if(type.equalsIgnoreCase("4")){
//			//解除冻结
//			up.getUserProfile().setFreezeAccountEndTime(time);
//		}else{
//			LoggerManager.Backend.error("禁言或冻结类型错误  , type = " + type);
//		}
//	}
}
