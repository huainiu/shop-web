package com.b5m.common.utils.goodsdetail;

import org.springframework.ui.ModelMap;

import com.b5m.base.common.utils.StringTools;

public class GoodsDetailHelper {
	public static void setDetailTitle(String category, String _category, ModelMap model, String brand, String brandModel, String bm, String produceTitle) {
		if (!StringTools.isEmpty(brand)) {
			model.addAttribute("_title_", bm + "报价|" + bm + "价格_" + produceTitle + "详情-" + _category + "帮5买");
			model.addAttribute("_keywords_", bm + "," + bm + "报价," + bm + "价格," + brand + "," + (StringTools.isEmpty(brandModel) ? "" : brandModel + ",") + bm + "详情,报价,价格,详情," + category + ",帮5买");
			model.addAttribute("_description_", "帮5买提供" + bm + "报价及" + bm + "价格信息，同时为您展示" + produceTitle + "详情信息，更多内容请查看帮5买" + bm + "价格详情。");
		} else {
			model.addAttribute("_title_", produceTitle + "报价_价格_详情-" + _category + "帮5买");
			model.addAttribute("_keywords_", category + ",报价,价格,详情,帮5买");
			model.addAttribute("_description_", "帮5买提供" + produceTitle + "报价及价格信息，同时为您展示" + produceTitle + "详情信息，更多内容请查看帮5买" + produceTitle + "价格详情。");
		}
	}
	
	public static void setAllShopTitle(String category, String _category, ModelMap model, String brand, String brandModel, String bm, String produceTitle) {
		if (!StringTools.isEmpty(brand)) {
			model.addAttribute("_title_", bm + "报价|" + bm + "价格_" + produceTitle + "比价-" + _category + "帮5买");
			model.addAttribute("_keywords_", bm + "," + bm + "报价," + bm + "价格," + brand + "," + (StringTools.isEmpty(brandModel) ? "" : brandModel + ",") + bm + "比价,报价,价格,比价," + category + ",帮5买");
			model.addAttribute("_description_", "帮5买提供" + bm + "报价及" + bm + "价格信息，同时为您展示" + produceTitle + "比价信息，更多内容请查看帮5买" + bm + "价格详情。");
		} else {
			model.addAttribute("_title_", produceTitle + "报价_价格_比价-" + _category + "帮5买");
			model.addAttribute("_keywords_", category + ",报价,价格,比价,帮5买");
			model.addAttribute("_description_", "帮5买提供" + produceTitle + "报价及价格信息，同时为您展示" + produceTitle + "比价信息，更多内容请查看帮5买" + produceTitle + "价格详情。");
		}
	}

	public static void setParamTitle(String category, String _category, ModelMap model, String brand, String brandModel, String bm, String produceTitle) {
		if (!StringTools.isEmpty(brand)) {
			model.addAttribute("_title_", bm + "参数|" + bm + "配置参数_" + produceTitle + "参数-" + _category + "帮5买");
			model.addAttribute("_keywords_", bm + ", " + bm + "参数," + bm + "配置参数, " + brand + ", " + (StringTools.isEmpty(brandModel) ? "" : brandModel + ",") + ",参数,配置参数," + category + ",帮5买");
			model.addAttribute("_description_", "帮5买提供" + bm + "参数及" + bm + "配置参数信息，更多内容请查看帮5买" + produceTitle + "页，了解" + bm + "参数详情。");
		} else {
			model.addAttribute("_title_", produceTitle + "参数_配置参数-" + _category + "帮5买");
			model.addAttribute("_keywords_", category + ",参数,配置参数,帮5买");
			model.addAttribute("_description_", "帮5买提供" + produceTitle + "参数及配置参数信息，更多内容请查看帮5买" + produceTitle + "参数详情。");
		}
	}

	public static void setCommentTitle(String category, String _category, ModelMap model, String brand, String brandModel, String bm, String produceTitle) {
		if (!StringTools.isEmpty(brand)) {
			model.addAttribute("_title_", bm + "怎么样|" + bm + "好吗_" + produceTitle + "怎么样-" + _category + "帮5买");
			model.addAttribute("_keywords_", bm + ", " + bm + "怎么样, " + bm + "好吗, " + brand + ", " + (StringTools.isEmpty(brandModel) ? "" : brandModel + ",") + ",怎么样,好吗," + category + ",帮5买");
			model.addAttribute("_description_", bm + "怎么样？" + bm + "好吗？帮5买" + produceTitle + "页为您解答" + bm + "怎么样等问题。");
		} else {
			model.addAttribute("_title_", produceTitle + "怎么样_好吗-" + _category + "帮5买");
			model.addAttribute("_keywords_", category + ",怎么样,好吗,帮5买");
			model.addAttribute("_description_", produceTitle + "怎么样、好吗？帮5买为您解答" + produceTitle + "怎么样等问题。");
		}
	}

	public static void setPriceTrendTitle(String category, String _category, ModelMap model, String brand, String brandModel, String bm, String produceTitle) {
		if (!StringTools.isEmpty(brand)) {
			model.addAttribute("_title_", bm + "价格|" + bm + "价格趋势_" + produceTitle + "价格趋势-" + _category + "帮5买");
			model.addAttribute("_keywords_", bm + ", " + bm + "价格," + bm + "价格趋势, " + brand + ", " + (StringTools.isEmpty(brandModel) ? "" : brandModel + ",") + ",价格,价格趋势," + category + ",帮5买");
			model.addAttribute("_description_", "帮5买提供" + bm + "价格及" + bm + "价格趋势信息，更多内容请查看帮5买" + produceTitle + "页，了解" + brand + brandModel + "价格趋势详情。");
		} else {
			model.addAttribute("_title_", produceTitle + "价格_价格趋势-" + _category + "帮5买");
			model.addAttribute("_keywords_", category + ",价格,价格趋势,帮5买");
			model.addAttribute("_description_", "帮5买提供" + produceTitle + "价格及价格趋势信息，更多内容请查看帮5买" + produceTitle + "价格趋势详情。");
		}
	}
	
	public static void setLowestPriceTitle(String category, String _category, ModelMap model, String brand, String brandModel, String bm, String produceTitle) {
		if (!StringTools.isEmpty(brand)) {
			model.addAttribute("_title_", bm + "价格|" + bm + "同款低价大全_" + produceTitle + "低价排行-" + _category + "帮5买");
			model.addAttribute("_keywords_", bm + ", " + bm + "同款低价大全," + bm + "价格趋势, " + brand + ", " + (StringTools.isEmpty(brandModel) ? "" : brandModel + ",") + ",同款低价大全,低价排行," + category + ",帮5买");
			model.addAttribute("_description_", "帮5买提供" + bm + "同款低价大全及" + bm + "低价排行信息，更多内容请查看帮5买" + produceTitle + "页，了解" + brand + brandModel + "低价排行详情。");
		} else {
			model.addAttribute("_title_", produceTitle + "同款低价大全_低价排行-" + _category + "帮5买");
			model.addAttribute("_keywords_", category + ",同款低价大全,低价排行,帮5买");
			model.addAttribute("_description_", "帮5买提供" + produceTitle + "同款低价大全及低价排行信息，更多内容请查看帮5买" + produceTitle + "低价排行详情。");
		}
	}
}
