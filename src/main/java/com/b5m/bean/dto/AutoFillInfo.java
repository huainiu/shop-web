package com.b5m.bean.dto;

import java.io.Serializable;
import java.util.List;

public class AutoFillInfo implements Serializable{
    /**
	 *
	 */
	private static final long serialVersionUID = -5655511390032281208L;

	/**
     * 返回值
     */
    private String value = "";
    
    /**
     * 高亮返回值
     */
    private String hl_value = "";
    
    private List<AutoFillInfo> subKeywords;
    
    /**
     * 大约存在多少结果
     */
    private long count = 0;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getHl_value() {
        return hl_value;
    }
    public void setHl_value(String hl_value) {
        this.hl_value = hl_value;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
	
	public List<AutoFillInfo> getSubKeywords() {
		return subKeywords;
	}
	
	public void setSubKeywords(List<AutoFillInfo> subKeywords) {
		this.subKeywords = subKeywords;
	}
    
    
}