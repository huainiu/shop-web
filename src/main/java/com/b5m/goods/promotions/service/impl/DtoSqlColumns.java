package com.b5m.goods.promotions.service.impl;

/**
 * 有关Dto对象和Sql字段的引用
 */
public class DtoSqlColumns {

	public static final String COL_CMS_SUPPLISER = "s.id, s.groupId, s.name, s.code, s.remark, s.logo, s.url, s.clientService, s.specialService, s.suppliserDesc, s.validState, s.serviceRate, s.transRate, s.sumRate, s.hotRate, s.commentSum, s.carriage, s.includeState, s.isMall, s.mallId, s.icon as suppliserIcon, s.country, s.broadbuy, s.payType, s.language, s.mall";

	public static final String COL_CMS_SUPPLISER_GROUP = "g.id as groupId, g.name as groupName";
	
	public static final String COL_CMS_COUPONS = "c.id as couponId, c.name as couponName, c.image, c.offsetType, c.offsetAmt, c.amtForOffset, c.brandId, c.type, c.validTimeFlg, c.startValidTime, c.endValidTime, c.isRabate, c.useRange, c.receiveLimit, c.integral, c.remark, c.release, c.totalCount, c.receivedCount";
	
	public static final String COL_CPS_INFO = "i.id as cpsId, i.commisionRatio, i.actionId, i.link as cpsLink, i.customLink, i.description, i.state";
	
	public static final String COL_CPS_CHANNEL = "c.id as channelId, c.name as channelName, c.link as channelLink";
	
	public static final String COL_PROMP_INFO = "p.id as promId, p.groupId, p.suppliser, p.brandId, p.name as promName, p.image, p.link, p.startTime, p.endTime, p.feature, p.tag, p.topTime, p.top, p.clickCount, p.remark";
}
