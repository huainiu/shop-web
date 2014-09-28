package com.b5m.sf1.dto.res;

import java.io.Serializable;

public class Group implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1155769782611386035L;
	private int groupCount;
	private boolean isLeaf = false;
	private String groupName;
	private String wholeGroupName;
	private String disPlayName;
	
	public Group(){}
	public Group(String groupName, int groupCount, boolean isLeaf, String wholeGroupName) {
        this.groupName = groupName;
        this.groupCount = groupCount;
        this.isLeaf = isLeaf;
        this.wholeGroupName = wholeGroupName;
    }
	
	public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getWholeGroupName() {
		return wholeGroupName;
	}

	public void setWholeGroupName(String wholeGroupName) {
		this.wholeGroupName = wholeGroupName;
	}
	public String getDisPlayName() {
		return disPlayName;
	}
	public void setDisPlayName(String disPlayName) {
		this.disPlayName = disPlayName;
	}

}
