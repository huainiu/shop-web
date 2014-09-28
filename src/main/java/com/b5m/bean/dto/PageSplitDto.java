/**
 * 
 */
package com.b5m.bean.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.b5m.common.utils.shoplist.PageSpliter;



/**
 * @author leo
 */
public class PageSplitDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] selectFlgArr;
	private int orderSeq;
	private Date createTime;
	private Date updateTime;
	private Long currUserId;
	private Long loginUserId;
	private String currUserName;
	private String[] currUserAuths;

	private String currFunctionName;

	private String idiotFlag;

	private boolean queryDetail = true;

	private Date afterTime;
	private Date beforeTime;
	/*
	 * ====================================== 分页信息
	 * ======================================
	 */
	// 总记录数
	private int countRecNumber;
	// 总页数
	private String countPageNumber;
	// 页面记录数
	private Integer pageSize = 10;
	// 当前页号
	private Integer currPageNo = 1;
	// 是否有上一页
	private String hasPrePage;
	// 是否有下一页
	private String hasNextPage;
	// 上一页
	private String prePageNo;
	// 下一页
	private String nextPageNo;
	// 省略前页号
	private String appPre;
	// 之前页面码列表
	private List<String> prePageNoList;
	// 省略后页号
	private String appPos;
	// 之后的页码列表
	private List<String> posPageNoList;

	public int getOffSet() {
		return PageSpliter.getOffSet(Integer.valueOf(currPageNo), pageSize);
	}

	public String getIdiotFlag() {
		return idiotFlag;
	}

	public void setIdiotFlag(String idiotFlag) {
		this.idiotFlag = idiotFlag;
	}

	public int getCountRecNumber() {
		return countRecNumber;
	}

	public void setCountRecNumber(int countRecNumber) {
		this.countRecNumber = countRecNumber;
	}

	public String getCountPageNumber() {
		return countPageNumber;
	}

	public void setCountPageNumber(String countPageNumber) {
		this.countPageNumber = countPageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrPageNo() {
		if(currPageNo == null || currPageNo < 1) currPageNo = 1;
		return Integer.valueOf(currPageNo);
	}

	public void setCurrPageNo(Integer currPageNo) {
		this.currPageNo = currPageNo;
	}

	public String getHasPrePage() {
		return hasPrePage;
	}

	public void setHasPrePage(String hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	public String getHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(String hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public String getPrePageNo() {
		return prePageNo;
	}

	public void setPrePageNo(String prePageNo) {
		this.prePageNo = prePageNo;
	}

	public String getNextPageNo() {
		return nextPageNo;
	}

	public void setNextPageNo(String nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	public String getAppPre() {
		return appPre;
	}

	public void setAppPre(String appPre) {
		this.appPre = appPre;
	}

	public List<String> getPrePageNoList() {
		return prePageNoList;
	}

	public void setPrePageNoList(List<String> prePageNoList) {
		this.prePageNoList = prePageNoList;
	}

	public String getAppPos() {
		return appPos;
	}

	public void setAppPos(String appPos) {
		this.appPos = appPos;
	}

	public List<String> getPosPageNoList() {
		return posPageNoList;
	}

	public void setPosPageNoList(List<String> posPageNoList) {
		this.posPageNoList = posPageNoList;
	}

	/**
	 * @return the orderSeq
	 */
	public int getOrderSeq() {
		return orderSeq;
	}

	/**
	 * @param orderSeq
	 *            the orderSeq to set
	 */
	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the currUserId
	 */
	public Long getCurrUserId() {
		return currUserId;
	}

	/**
	 * @param currUserId
	 *            the currUserId to set
	 */
	public void setCurrUserId(Long currUserId) {
		this.currUserId = currUserId;
	}

	/**
	 * @return the currUserName
	 */
	public String getCurrUserName() {
		return currUserName;
	}

	/**
	 * @param currUserName
	 *            the currUserName to set
	 */
	public void setCurrUserName(String currUserName) {
		this.currUserName = currUserName;
	}

	/**
	 * @return the currUserAuths
	 */
	public String[] getCurrUserAuths() {
		return currUserAuths;
	}

	/**
	 * @param currUserAuths
	 *            the currUserAuths to set
	 */
	public void setCurrUserAuths(String[] currUserAuths) {
		this.currUserAuths = currUserAuths;
	}

	/**
	 * @return the currFunctionName
	 */
	public String getCurrFunctionName() {
		return currFunctionName;
	}

	/**
	 * @param currFunctionName
	 *            the currFunctionName to set
	 */
	public void setCurrFunctionName(String currFunctionName) {
		this.currFunctionName = currFunctionName;
	}

	/**
	 * @return the selectFlgArr
	 */
	public String[] getSelectFlgArr() {
		return selectFlgArr;
	}

	/**
	 * @param selectFlgArr
	 *            the selectFlgArr to set
	 */
	public void setSelectFlgArr(String[] selectFlgArr) {
		this.selectFlgArr = selectFlgArr;
	}

	/**
	 * @return the loginUserId
	 */
	public Long getLoginUserId() {
		return loginUserId;
	}

	/**
	 * @param loginUserId
	 *            the loginUserId to set
	 */
	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public boolean isQueryDetail() {
		return queryDetail;
	}

	public void setQueryDetail(boolean queryDetail) {
		this.queryDetail = queryDetail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getAfterTime() {
		return afterTime;
	}

	public void setAfterTime(Date afterTime) {
		this.afterTime = afterTime;
	}

	public Date getBeforeTime() {
		return beforeTime;
	}

	public void setBeforeTime(Date beforeTime) {
		this.beforeTime = beforeTime;
	}

}
