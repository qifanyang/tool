package com.tobe.excelutils.test;


public class ActivityVO {
	private int id;
	/**
	 * 所属组编号
	 */
	private int groupId;
	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 活动描述
	 */
	private String groupDesc;
	/**
	 * 单个活动描述
	 */
	private String singleDesc;
	/**
	 * 起始时间
	 */
	private String start;
	/**
	 * 结束时间
	 */
	private String end;
	/**
	 * 目标类型
	 */
	private int targetType;
	/**
	 * 目标数量
	 */
	private String targetNum;
	/**
	 * 奖励
	 */
	private String rewards;
	/**
	 * 检测类型(0静态，1动态)
	 */
	private int checkType;
	/**
	 * 重置标志位(0否1是)
	 */
	private int reset;
	/**
	 * 是否自动提交(0否1是)
	 */
	private int autoCommit;
	/**
	 * 邮件标题
	 */
	private String mailTopic;
	/**
	 * 邮件内容
	 */
	private String mailContent;
	/**
	 * 参与条件类型(1 等级段，2 VIP等级段)
	 */
	private int partakeType;
	/**
	 * 参与条件值
	 */
	private String partakeValue;
	/**
	 * 显示级别(1.NEW,2.HOT)
	 */
	private int showRank;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getSingleDesc() {
		return singleDesc;
	}

	public void setSingleDesc(String singleDesc) {
		this.singleDesc = singleDesc;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getTargetType() {
		return targetType;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public String getTargetNum() {
		return targetNum;
	}

	public void setTargetNum(String targetNum) {
		this.targetNum = targetNum;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public int getCheckType() {
		return checkType;
	}

	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}

	public int getReset() {
		return reset;
	}

	public void setReset(int reset) {
		this.reset = reset;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(int autoCommit) {
		this.autoCommit = autoCommit;
	}

	public String getMailTopic() {
		return mailTopic;
	}

	public void setMailTopic(String mailTopic) {
		this.mailTopic = mailTopic;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public int getPartakeType() {
		return partakeType;
	}

	public void setPartakeType(int partakeType) {
		this.partakeType = partakeType;
	}

	public String getPartakeValue() {
		return partakeValue;
	}

	public void setPartakeValue(String partakeValue) {
		this.partakeValue = partakeValue;
	}

	public int getShowRank() {
		return showRank;
	}

	public void setShowRank(int showRank) {
		this.showRank = showRank;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
//	@Override
//	protected String table() {
//		return "merchant_activity";
//	}
}
