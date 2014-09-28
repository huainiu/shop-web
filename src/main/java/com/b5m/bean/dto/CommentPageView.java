package com.b5m.bean.dto;

import com.b5m.dao.domain.page.PageView;

public class CommentPageView<T> extends PageView<T> {
	private static final long serialVersionUID = 7777142116853909715L;

	public CommentPageView(int pageSize, int currentPage) {
		super(pageSize, currentPage);
	}

	private long goodNum;
	private long midNum;
	private long badNum;

	public long getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(long goodNum) {
		this.goodNum = goodNum;
	}

	public long getMidNum() {
		return midNum;
	}

	public void setMidNum(long midNum) {
		this.midNum = midNum;
	}

	public long getBadNum() {
		return badNum;
	}

	public void setBadNum(long badNum) {
		this.badNum = badNum;
	}

}
