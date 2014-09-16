package ht.msg;

public class ReqItemMsg extends ReqMailMsg implements IMessage {

	/**道具最多发三个,格式:id,num;id,num;id,num*/
	private String items;
	// 现金, 游戏币
	private int cash;
	// 支付币 ++ 人民币充值所得
	private int coin;
	/**是否归属于vip*/
	private boolean addvip;
	
	@Override
	public int getId() {
		return MsgCode.MAIL_ITEM;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public boolean isAddvip() {
		return addvip;
	}

	public void setAddvip(boolean addvip) {
		this.addvip = addvip;
	}

	
}
