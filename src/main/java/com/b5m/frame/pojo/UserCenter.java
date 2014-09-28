package com.b5m.frame.pojo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 用户信息表
 */
public class UserCenter implements Serializable {

	private static final long serialVersionUID = 1L;

	// 用户id
	private String userId;

	// 用户名
	private String userName;

	// 用户密码
	private String password;

	// 用户邮箱
	private String email;

	// 用户生日
	private Date birthday;

	// 个性签名
	private String signature;

	// 性别
	private String gender;

	// 用户积分
	private Long score = 0L;

	/**
	 * 用户累计积分
	 */
	private Long accumulatedScore = 0L;

	// 用户所在城市
	private String city;

	// 用户昵称
	private String nickName;

	// 用户头像
	private String avatar;

	// 帐号激活验证码
	private String activeCode;

	// 帐号激活状态（REG：表示激活）
	private String activation;

	// 用户创建时间
	private Date createTime;

	// 用户修改时间
	private Date updateTime;

	// 找回密码
	private String findPwCode;

	// 用户来源类型(0表示注册用户，1表示插件用户，2表示好用户，3表示老用户, 4表示app用户)
	private Integer userType;

	// 用户的安全级别 0：默认最低，1：级别高（邮箱验证）
	private int securityLevel;

	// 第三方登录类型
	private Integer thirdPartyLoginType;

	// 第三方登录token
	private String thirdPartyLoginToken;

	// 邀请码
	private String inviteCode;

	/**
	 * 连续签到次数
	 */
	private Integer signConsistentCount;

	/**
	 * 签到总次数
	 */
	private Integer signTotalCount;

	private String showErrorMsg;
	
	private String avatarTransient;
	
	private String birthdayStr;

	// 统一显示名
	private String showName;

	public UserCenter() {

	}

	public UserCenter(String userId, String userName, String nickName) {
		this.userId = userId;
		this.nickName = nickName;
		this.userName = userName;
	}

	public UserCenter(String userId, String nickName, String activeCode, String activation, Integer userType) {
		super();
		this.userId = userId;
		this.nickName = nickName;
		this.activeCode = activeCode;
		this.activation = activation;
		this.userType = userType;
	}

	public UserCenter(String userId, String email, String password, String nickName, String activeCode, String activation, Integer userType) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.activeCode = activeCode;
		this.activation = activation;
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getGender() {
		return gender;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFindPwCode() {
		return findPwCode;
	}

	public void setFindPwCode(String findPwCode) {
		this.findPwCode = findPwCode;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public int getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(int securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getThirdPartyLoginType() {
		return thirdPartyLoginType;
	}

	public void setThirdPartyLoginType(Integer thirdPartyLoginType) {
		this.thirdPartyLoginType = thirdPartyLoginType;
	}

	public String getThirdPartyLoginToken() {
		return thirdPartyLoginToken;
	}

	public void setThirdPartyLoginToken(String thirdPartyLoginToken) {
		this.thirdPartyLoginToken = thirdPartyLoginToken;
	}

	public String getShowErrorMsg() {
		return showErrorMsg;
	}

	public void setShowErrorMsg(String showErrorMsg) {
		this.showErrorMsg = showErrorMsg;
	}

	public String getAvatarTransient() {
		return avatarTransient;
	}

	public void setAvatarTransient(String avatarTransient) {
		this.avatarTransient = avatarTransient;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getSignConsistentCount() {
		return signConsistentCount;
	}

	public void setSignConsistentCount(Integer signConsistentCount) {
		this.signConsistentCount = signConsistentCount;
	}

	public Integer getSignTotalCount() {
		return signTotalCount;
	}

	public void setSignTotalCount(Integer signTotalCount) {
		this.signTotalCount = signTotalCount;
	}

	public Long getAccumulatedScore() {
		return accumulatedScore;
	}

	public void setAccumulatedScore(Long accumulatedScore) {
		this.accumulatedScore = accumulatedScore;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}

}