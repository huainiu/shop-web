package com.b5m.service.hbase.bean;

import java.util.Date;
/**
 * @author echo
 */
public class HComment {
	private String url;
	
	private Date createTime;
	
	private String author;
	
	private String content;
	
	private int type;//1:好评 0 中评 -1 差评
	
	private String commentAttr;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCommentAttr() {
		return commentAttr;
	}

	public void setCommentAttr(String commentAttr) {
		this.commentAttr = commentAttr;
	}
	
}
