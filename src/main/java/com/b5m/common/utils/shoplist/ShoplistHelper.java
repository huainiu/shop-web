package com.b5m.common.utils.shoplist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;

import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.UrlTools;
import com.b5m.bean.dto.shoplist.SuiSearchDto;
import com.b5m.common.utils.DataUtils;
import com.mchange.v1.util.StringTokenizerUtils;

public class ShoplistHelper {
	
	public static void setTitle(Model model, SuiSearchDto dto) {
		StringBuilder sb = new StringBuilder();
		addSource(dto, sb);
		addSort(dto, sb);
		addCFG(dto, sb);
		// addViewType(dto, sb);
		if (!StringUtils.isEmpty(dto.getPriceFrom())) {
			sb.append(dto.getPriceFrom());
		}
		if (!StringUtils.isEmpty(dto.getPriceTo())) {
			if (sb.length() > 0)
				sb.append("-");
			sb.append(dto.getPriceTo());
		}
		String attrValues = dto.getBrandValue();
		Map<String, List<String>> attrMap = getAttrs(attrValues);

		List<String> brands = null;
		for (Entry<String, List<String>> entry : attrMap.entrySet()) {
			String key = entry.getKey();
			if ("品牌".equals(key)) {
				brands = entry.getValue();
				continue;
			}
			for (String attrValue : entry.getValue()) {
				sb.append(attrValue);
			}
		}
		String keyword = dto.getKeyWord();
		if (brands != null) {
			for (String attrValue : brands) {
				sb.append(attrValue);
			}
		}
		sb.append(keyword);
		model.addAttribute("seotitle", sb.toString());
		model.addAttribute("currentPage", dto.getCurrPageNo());
		model.addAttribute("showType", dto.getShowType());
	}
	
	// 添加商家
	public static void addSource(SuiSearchDto dto, StringBuilder sb) {
		String source = dto.getSourceValue();
		if (!StringUtils.isEmpty(source)) {
			sb.append(DataUtils.replace(DataUtils.specialCharDe(source), ",", "，"));
		}
	}

	public static void addSort(SuiSearchDto dto, StringBuilder sb) {
		String sortField = dto.getSortField();
		if (!StringUtils.isEmpty(sortField)) {
			sb.append(getSortFieldName(sortField));
			sb.append(getSortTypeName(dto.getSortType()));
		}
	}
	
	public static String getSortTypeName(String sortType) {
		if ("DESC".equals(sortType)) {
			return "降序";
		}
		return "升序";
	}

	public static String getSortFieldName(String sortField) {
		if ("Date".equals(sortField)) {
			return "最新";
		}
		if ("Score".equals(sortField)) {
			return "好评";
		}
		if ("Price".equals(sortField)) {
			return "价格";
		}
		return "默认";
	}

	public static void addCFG(SuiSearchDto dto, StringBuilder sb) {
		/*if (!StringUtils.isEmpty(dto.getIsCOD())) {
			sb.append("货到付款");
		}*/
		if (!StringUtils.isEmpty(dto.getIsFreeDelivery())) {
			sb.append("免运费");
		}
		if (!StringUtils.isEmpty(dto.getIsGenuine())) {
			sb.append("正品行货");
		}
	}

	public static void addViewType(SuiSearchDto dto, StringBuilder sb) {
		if (!"image".equals(dto.getShowType())) {
			sb.append("列表模式");
		}
	}

	public static String getLastCategoryValue(String category) {
		if (StringUtils.isEmpty(category))
			return "";
		String[] categorys = StringTokenizerUtils.tokenizeToArray(category, ">");
		if (categorys.length <= 0)
			return "";
		return categorys[categorys.length - 1];
	}

	public static Map<String, List<String>> getAttrs(String attrs) {
		Map<String, List<String>> map = CollectionTools.newMap();
		if (StringUtils.isEmpty(attrs))
			return map;
		String[] attrArray = StringTokenizerUtils.tokenizeToArray(attrs, ",");
		for (String attr : attrArray) {
			String[] attrKV = StringTokenizerUtils.tokenizeToArray(attr, ":");
			List<String> values = map.get(attrKV[0]);
			if (values == null) {
				values = new ArrayList<String>();
				map.put(attrKV[0], values);
			}
			values.add(attrKV[1]);
		}
		return map;
	}

	public static void setKeywords(Model model, String keywords, SuiSearchDto dto) {
		if ("*".equals(keywords)) {
			String categoryValue = dto.getCategoryValue();
			if (!StringUtils.isEmpty(categoryValue)) {
				int startIndex = categoryValue.lastIndexOf(">") + 1;
				if (startIndex < 0)
					startIndex = 0;
				setKeywords(model, categoryValue.substring(startIndex), dto);
			}
		} else {
			model.addAttribute("keyword", keywords);
			model.addAttribute("keyword1", UrlTools.urlEncode(UrlTools.urlEncode(keywords)));
		}
	}
}
