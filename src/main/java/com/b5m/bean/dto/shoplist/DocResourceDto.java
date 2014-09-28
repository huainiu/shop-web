package com.b5m.bean.dto.shoplist;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DocResourceDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -1912460314538654634L;

	private Map<String, String> res;

	private List<Map<String, String>> subResources;

	private List<Map<String, String>> norms;

	/* private Map<String, String> lowestPriceRes; */

	private Map<String, String> suitablePriceRes;

	public Map<String, String> getRes() {
		if (null == res)
			res = new HashMap<String, String>();
		return res;
	}

	public void setRes(Map<String, String> res) {
		this.res = res;
	}

	public List<Map<String, String>> getNorms() {
		return norms;
	}

	public void setNorms(List<Map<String, String>> norms) {
		this.norms = norms;
	}

	public List<Map<String, String>> getSubResources() {
		if (null == subResources)
			// 使用LinkdedList方便排序
			subResources = new LinkedList<Map<String, String>>();
		return subResources;
	}

	/*
	 * public Map<String, String> getLowestPriceRes(){ if(null ==
	 * lowestPriceRes) lowestPriceRes = new HashMap<String, String>(); return
	 * lowestPriceRes; }
	 * 
	 * public void setLowestPriceRes(Map<String, String> lowestPriceRes){
	 * this.lowestPriceRes = lowestPriceRes; }
	 */

	public void setSubResources(List<Map<String, String>> subResources) {
		this.subResources = subResources;
	}

	public Map<String, String> getSuitablePriceRes() {
		return suitablePriceRes;
	}

	public void setSuitablePriceRes(Map<String, String> suitablePriceRes) {
		if (suitablePriceRes == null) {
			suitablePriceRes = new HashMap<String, String>();
		}
		this.suitablePriceRes = suitablePriceRes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((norms == null) ? 0 : norms.hashCode());
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		result = prime * result + ((subResources == null) ? 0 : subResources.hashCode());
		result = prime * result + ((suitablePriceRes == null) ? 0 : suitablePriceRes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocResourceDto other = (DocResourceDto) obj;
		if (norms == null) {
			if (other.norms != null)
				return false;
		} else if (!norms.equals(other.norms))
			return false;
		if (res == null) {
			if (other.res != null)
				return false;
		} else if (!res.equals(other.res))
			return false;
		if (subResources == null) {
			if (other.subResources != null)
				return false;
		} else if (!subResources.equals(other.subResources))
			return false;
		if (suitablePriceRes == null) {
			if (other.suitablePriceRes != null)
				return false;
		} else if (!suitablePriceRes.equals(other.suitablePriceRes))
			return false;
		return true;
	}

}
