package com.b5m.service.ontimeprice;

import java.util.HashSet;
import java.util.Set;

public class SkuProp {
	
	private String name;
	
	private Set<String> props;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addProp(String prop){
		if(props == null){
			props = new HashSet<String>();
		}
		props.add(prop);
	}

	public Set<String> getProps() {
		return props;
	}

	public void setProps(Set<String> props) {
		this.props = props;
	}
	
}
