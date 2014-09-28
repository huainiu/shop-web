package com.b5m.bean.dto;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 导航需要使用到的链接DTO
 * @author jacky
 *
 */
public class NavigationLinkDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4507449746978729676L;
	
	private LinkedList<LinkDto> navigations;
	
	private LinkDto keyword;

	public LinkedList<LinkDto> getNavigations() {
		if(null == navigations)
			navigations = new LinkedList<LinkDto>();
		return navigations;
	}

	public void setNavigations(LinkedList<LinkDto> navigations) {
		this.navigations = navigations;
	}

	public LinkDto getKeyword() {
		return keyword;
	}

	public void setKeyword(LinkDto keyword) {
		this.keyword = keyword;
	}
	
	
}
