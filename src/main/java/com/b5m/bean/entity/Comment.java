package com.b5m.bean.entity;

import java.util.Date;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.NamedQueries;
import com.b5m.dao.annotation.NamedQuery;
import com.b5m.dao.annotation.Table;
import com.b5m.dao.domain.ColType;


/**
 * 用户的评论
 * 
 * @author yuxiaolong
 * 
 */
@Table("comments")
@NamedQueries({@NamedQuery(name = "queryId", sql = "select id from comments")})
public class Comment extends BaseSupplierWrap{

	@Column
	private String user;
	//0-好评  1-一般 2-很差
	@Column
	private int type;

	// 0--不通过, 1--通过
	@Column
	private int oper;
	
	@Column(type = ColType.TEXT)
	private String content;
	
	@Column
	private String avatar;

	@Column
	private int isDelete;
	
	@Column(name = "bak_int_1")
	private int bakInt1;
	
	@Column(name = "bak_int_2")
	private int bakInt2;
	
	@Column(name = "bak_int_3")
	private int bakInt3;
	
	@Column(name = "bak_int_4")
	private int bakInt4;
	
	@Column(name = "bak_str_1")
	private String bakStr1;
	
	@Column(name = "bak_str_2")
	private String bakStr2;
	
	@Column(name = "bak_str_3")
	private String bakStr3;
	
	@Column(name = "bak_str_4")
	private String bakStr4;
	
	@Column(name = "createTime")
	private Date createTime;
	
	@Column(name = "updateTime")
	private Date updateTime;
	
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOper() {
		return oper;
	}

	public void setOper(int oper) {
		this.oper = oper;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getBakInt1() {
		return bakInt1;
	}

	public void setBakInt1(int bakInt1) {
		this.bakInt1 = bakInt1;
	}

	public int getBakInt2() {
		return bakInt2;
	}

	public void setBakInt2(int bakInt2) {
		this.bakInt2 = bakInt2;
	}

	public int getBakInt3() {
		return bakInt3;
	}

	public void setBakInt3(int bakInt3) {
		this.bakInt3 = bakInt3;
	}

	public int getBakInt4() {
		return bakInt4;
	}

	public void setBakInt4(int bakInt4) {
		this.bakInt4 = bakInt4;
	}

	public String getBakStr1() {
		return bakStr1;
	}

	public void setBakStr1(String bakStr1) {
		this.bakStr1 = bakStr1;
	}

	public String getBakStr2() {
		return bakStr2;
	}

	public void setBakStr2(String bakStr2) {
		this.bakStr2 = bakStr2;
	}

	public String getBakStr3() {
		return bakStr3;
	}

	public void setBakStr3(String bakStr3) {
		this.bakStr3 = bakStr3;
	}

	public String getBakStr4() {
		return bakStr4;
	}

	public void setBakStr4(String bakStr4) {
		this.bakStr4 = bakStr4;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + bakInt1;
		result = prime * result + bakInt2;
		result = prime * result + bakInt3;
		result = prime * result + bakInt4;
		result = prime * result + ((bakStr1 == null) ? 0 : bakStr1.hashCode());
		result = prime * result + ((bakStr2 == null) ? 0 : bakStr2.hashCode());
		result = prime * result + ((bakStr3 == null) ? 0 : bakStr3.hashCode());
		result = prime * result + ((bakStr4 == null) ? 0 : bakStr4.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + isDelete;
		result = prime * result + oper;
		result = prime * result + type;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (bakInt1 != other.bakInt1)
			return false;
		if (bakInt2 != other.bakInt2)
			return false;
		if (bakInt3 != other.bakInt3)
			return false;
		if (bakInt4 != other.bakInt4)
			return false;
		if (bakStr1 == null) {
			if (other.bakStr1 != null)
				return false;
		} else if (!bakStr1.equals(other.bakStr1))
			return false;
		if (bakStr2 == null) {
			if (other.bakStr2 != null)
				return false;
		} else if (!bakStr2.equals(other.bakStr2))
			return false;
		if (bakStr3 == null) {
			if (other.bakStr3 != null)
				return false;
		} else if (!bakStr3.equals(other.bakStr3))
			return false;
		if (bakStr4 == null) {
			if (other.bakStr4 != null)
				return false;
		} else if (!bakStr4.equals(other.bakStr4))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (isDelete != other.isDelete)
			return false;
		if (oper != other.oper)
			return false;
		if (type != other.type)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}