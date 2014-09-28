package com.b5m.sf1.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.Lang;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.bean.dto.shoplist.DocResourceDto;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.dto.res.Group;
import com.b5m.sf1.dto.res.GroupTree;
import com.b5m.sf1.dto.res.SearchDTO;

/**
 * 数据处理
 * 
 * @author echo
 */
public class Sf1DataHelper {
	public static void wrap(SearchDTO searchDto, SF1SearchBean searchBean, JSONObject jsonObject) {
		setAttrs(searchDto, searchBean, jsonObject);
		setTopGroups(searchDto, jsonObject);
		setRelatedQueries(searchDto, jsonObject);
	}

	public static void setSpeedTime(SearchDTO searchDto, JSONObject resultJsonObj) {
		double result;
		try {
			JSONObject timerJson = resultJsonObj.getJSONObject("timers");
			double speedTime = timerJson.getDouble("process_time");
			// 将double 截取到小数后3位
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(3);
			result = Double.valueOf(nf.format(speedTime));
		} catch (Exception e) {
			// 如果获得时间失败,则时间为-1
			result = -1;
		}
		searchDto.setSpeedTime(result);
	}

	/**
	 * 设置group info 和 category
	 * 
	 * @param searchDto
	 * @param resultJsonObj
	 */
	public static void setCategoryAndGroupInfo(SearchDTO searchDto, JSONObject resultJsonObj) {
		Map<String, GroupTree> mapGroup = new LinkedHashMap<String, GroupTree>();
		if (searchDto == null || resultJsonObj == null)
			return;
		JSONArray arrGroup = resultJsonObj.getJSONArray("group");
		if (arrGroup == null)
			return;
		int size = arrGroup.size();
		if (size < 1)
			return;
		// 按分组进行遍历
		for (int i = 0; i < size; i++) {
			GroupTree groupTree = new GroupTree();
			JSONObject groupJson = arrGroup.getJSONObject(i);
			String groupName = groupJson.getString("property");
			int groupCount = groupJson.getInteger("document_count");
			Group group = new Group();
			group.setGroupName(groupName);
			group.setGroupCount(groupCount);
			groupTree.setGroup(group);
			JSONArray arrGroupContent = groupJson.getJSONArray("labels");
			if (arrGroupContent != null) {
				getSubGroupFromJson(groupTree, arrGroupContent, "");
			}
			if ("Category".equals(groupName)) {
				searchDto.setCategoryTree(groupTree);
				continue;
			}
			mapGroup.put(groupName, groupTree);
		}
		searchDto.setGroups(mapGroup);
	}

	// 设置resources结果
	public static void setResources(SearchDTO searchDto, SF1SearchBean searchBean, JSONObject resultJsonObj) {
		if (resultJsonObj == null)
			return;
		// 从请求结果中获取数据,并封装成List<Map>
		JSONArray resourcesArray = resultJsonObj.getJSONArray("resources");
		if (resourcesArray == null)
			return;
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		int intSize = resourcesArray.size();
		List<DocResourceDto> resDtos = new ArrayList<DocResourceDto>();
		for (int i = 0; i < intSize; i++) {
			JSONObject resource = resourcesArray.getJSONObject(i);
			resource.put("SourceCount", String.valueOf(StringTools.split(resource.getString("Source"), ",").length));
			JSONArray subDocsJson = resource.getJSONArray("SubDocs");
			int sublength = 0;
			if (subDocsJson != null)
				sublength = subDocsJson.size();
			resource.put("SubDocsCount", String.valueOf(sublength));
			resource.put("ItemCount", resource.getString("itemcount"));
			resource.put("DocId", resource.get("DOCID"));
			resource.put("price", resource.get("Price"));
			Map<String, String> map = toMap(resource);
			result.add(map);
			resDtos.add(createDocResourceDto(map, resource));
		}
		searchDto.setDocResourceDtos(resDtos);
		fillSuitablePrice(resDtos, searchBean);
		searchDto.setResourcesList(result);
	}

	public static void setRemovedKeywords(SearchDTO searchDto, SF1SearchBean searchBean, JSONObject resultJsonObj) {
		if (resultJsonObj == null)
			return;
		JSONArray removedKeywords = resultJsonObj.getJSONArray("removed_keywords");
		if (removedKeywords == null || removedKeywords.size() < 1)
			return;
		int length = removedKeywords.size();
		for (int index = 0; index < length; index++) {
			SearchDTO reSearchDto = new SearchDTO();
			JSONObject removedKeyword = removedKeywords.getJSONObject(index);
			String newQuery = removedKeyword.getString("new_query");
			reSearchDto.setKeywords(newQuery);
			setResources(reSearchDto, searchBean, removedKeyword);
			searchDto.getReSearchDTOs().add(reSearchDto);
		}
	}

	/**
	 * 解析概要
	 * 
	 * @param resultJsonObj
	 * @return
	 */
	public static List<String[]> getSummarizationFromResult(JSONObject resultJsonObj) {
		List<String[]> results = new ArrayList<String[]>();
		JSONArray arrays = resultJsonObj.getJSONArray("resources");
		if (arrays == null)
			return results;
		int size = arrays.size();
		if (size < 1)
			return results;
		JSONArray stringArr = arrays.getJSONObject(0).getJSONArray("summary");
		for (int i = 0; i < size; i++) {
			String[] result = new String[2];
			JSONObject childOfSummary = stringArr.getJSONObject(i);
			String summary = childOfSummary.getString("sentence");
			double score = childOfSummary.getDouble("score");
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(1);
			String scoreStr = nf.format(score);
			result[0] = summary;
			result[1] = scoreStr;
			results.add(result);
		}
		return results;
	}

	private static List<DocResourceDto> fillSuitablePrice(List<DocResourceDto> docResourceDtos, SF1SearchBean dto) {
		if (dto == null)
			return docResourceDtos;
		BigDecimal maxPrice = Lang.toBigDecimal(dto.getEprice());
		BigDecimal minPrice = Lang.toBigDecimal(dto.getSprice());
		for (DocResourceDto docResourceDto : docResourceDtos) {
			fillSuitablePrice(docResourceDto, maxPrice, minPrice);
		}
		return docResourceDtos;
	}

	private static void fillSuitablePrice(DocResourceDto docResourceDto, BigDecimal maxPrice, BigDecimal minPrice) {
		List<Map<String, String>> subResouces = docResourceDto.getSubResources();
		if (subResouces.isEmpty())
			return;
		int suitableIndex = 0;
		BigDecimal lowestPrice = null;
		int closePriceIndex = 0;
		BigDecimal closePrice = null;

		int length = subResouces.size();
		for (int index = 0; index < length; index++) {
			Map<String, String> subResouce = subResouces.get(index);
			if (StringUtils.isEmpty(subResouce.get("Source"))) {
				continue;
			}
			BigDecimal price = Lang.toBigDecimal(subResouce.get("Price"));
			// 显示价格最接近价格区间的商品
			if (minPrice != null && price.compareTo(minPrice) < 0) {
				BigDecimal diffPrice = minPrice.subtract(price);
				if (closePrice != null && diffPrice.compareTo(closePrice) < 0) {
					closePriceIndex = index;
				}
				closePrice = diffPrice;
				continue;
			}
			if (maxPrice != null && price.compareTo(maxPrice) > 0) {
				BigDecimal diffPrice = price.subtract(maxPrice);
				if (closePrice != null && diffPrice.compareTo(closePrice) < 0) {
					closePriceIndex = index;
				}
				closePrice = diffPrice;
				continue;
			}
			if (lowestPrice == null) {
				suitableIndex = index;
			}
			lowestPrice = price;
			if (lowestPrice != null && lowestPrice.compareTo(price) > 0) {
				suitableIndex = index;
			}
		}
		if (suitableIndex == 0) {
			suitableIndex = closePriceIndex;
		}
		docResourceDto.setSuitablePriceRes(subResouces.get(suitableIndex));
	}

	private static DocResourceDto createDocResourceDto(Map<String, String> resource, JSONObject resJson) {
		DocResourceDto resDto = new DocResourceDto();
		resDto.setRes(resource);
		JSONArray subDocsJson = null;
		if (!Lang.isEmpty(resJson.getString("SubDocs")))
			subDocsJson = resJson.getJSONArray("SubDocs");
		if (subDocsJson == null)
			return resDto;

		if (resJson.getJSONArray("SubProps") != null) {
			JSONArray subSubProps = resJson.getJSONArray("SubProps");
			int len = subSubProps.size();
			List<Map<String, String>> norms = new ArrayList<Map<String, String>>();
			for (int j = 0; j < len; j++) {
				JSONObject obj = subSubProps.getJSONObject(j);
				if (StringUtils.isEmpty(obj.getString("name")))
					continue;
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", obj.getString("name"));
				map.put("price", obj.getString("price"));
				norms.add(map);
			}
			if (norms.size() > 0) {
				resDto.setNorms(norms);
			}
		}

		int length = subDocsJson.size();
		for (int index = 0; index < length; index++) {
			Map<String, String> map = convertSubDocToMap(subDocsJson.getJSONObject(index));
			if (map == null)
				continue;
			resDto.getSubResources().add(map);
		}
		return sortAndfilterRepeatSource(resDto);
	}

	private static DocResourceDto sortAndfilterRepeatSource(DocResourceDto resDto) {
		resDto.setSubResources(sortAndfilterRepeatSource(resDto.getSubResources()));
		return resDto;
	}

	/**
	 * 过滤掉重复的商家 并将最便宜的三个放在最前面 可以修改放在最后面 这样减少重排的一个过程
	 * 
	 * @param subDocs
	 * @return
	 */
	private static List<Map<String, String>> sortAndfilterRepeatSource(List<Map<String, String>> subDocs) {
		// filterRepeatSource(subDocs);// 先去掉过滤的内容
		List<Map<String, String>> newSubDocs = new ArrayList<Map<String, String>>();
		Map<String, String> cheapest = rtnCheapest(subDocs, newSubDocs);
		if (cheapest != null) {// 如果第一个为空 则不用添加第二个了
			newSubDocs.add(cheapest);
			Map<String, String> cheapest1 = rtnCheapest(subDocs, newSubDocs);

			if (cheapest1 == null)
				return newSubDocs;
			// 如果第二个为空 则不用添加第三个了
			newSubDocs.add(cheapest1);
			Map<String, String> cheapest2 = rtnCheapest(subDocs, newSubDocs);
			if (cheapest2 == null)
				return newSubDocs;

			newSubDocs.add(cheapest2);
			for (Map<String, String> subDoc : subDocs) {
				newSubDocs.add(subDoc);
			}
		}
		return newSubDocs;
	}

	private static Map<String, String> rtnCheapest(List<Map<String, String>> subDocs, List<Map<String, String>> newSubDocs) {
		int length = subDocs.size();
		Map<String, String> cheapest = null;
		Map<String, String> cheapestNotTaobao = null;
		int index = -1;
		int cheapestIndex = -1;
		for (int i = 0; i < length; i++) {
			Map<String, String> subDoc = subDocs.get(i);
			// 浮层中显示的商家不能重复
			if (exists(newSubDocs, subDoc))
				continue;

			String priceStr = subDoc.get("Price");
			if ((cheapestNotTaobao == null || StringUtils.isEmpty(priceStr)) && !isTaobao(subDoc)) {
				cheapestNotTaobao = subDoc;
				index = i;
			}
			if (cheapest == null || StringUtils.isEmpty(priceStr)) {
				cheapest = subDoc;
				cheapestIndex = i;
				continue;
			}
			String priceStr1 = cheapest.get("Price");
			if (!StringUtils.isEmpty(priceStr1) && Double.parseDouble(priceStr) < Double.parseDouble(priceStr1)) {
				cheapest = subDoc;
				cheapestIndex = i;
			}
			if (!isTaobao(subDoc)) {
				String priceTaobao = cheapestNotTaobao.get("Price");
				if (!StringUtils.isEmpty(priceTaobao) && Double.parseDouble(priceStr) < Double.parseDouble(priceTaobao)) {
					cheapestNotTaobao = subDoc;
					index = i;
				}
			}
		}
		if (index != -1) {
			subDocs.remove(index);
			return cheapestNotTaobao;
		}
		if (cheapestIndex != -1) {
			subDocs.remove(cheapestIndex);
		}
		return cheapest;
	}

	private static boolean exists(List<Map<String, String>> newSubDocs, Map<String, String> subDoc) {
		for (Map<String, String> newSubDoc : newSubDocs) {
			if (newSubDoc.get("Source").equals(subDoc.get("Source")))
				return true;
		}
		return false;
	}

	private static boolean isTaobao(Map<String, String> subDoc) {
		return false;// "淘宝网".equals(subDoc.get("Source")) ||
						// "天猫".equals(subDoc.get("Source"));
	}

	private static Map<String, String> convertSubDocToMap(JSONObject subDocJson) {
		Map<String, String> subDoc = new HashMap<String, String>();
		for (String key : subDocJson.keySet()) {
			subDoc.put(key, subDocJson.getString(key));
		}
		if (StringUtils.isEmpty(subDoc.get("Source")) || StringUtils.isEmpty(subDoc.get("Url"))) {
			return null;
		}
		return subDoc;
	}

	// 通过topGroup 对 类目进行排序
	public static void sortCategoryTree(List<GroupTree> topGroups, GroupTree category) {
		List<GroupTree> toTop = new ArrayList<GroupTree>();
		for (GroupTree topGroup : topGroups) {
			GroupTree groupTree = contain(category.getGroupTree(), topGroup);
			if (groupTree != null) {
				sortCategoryTree(topGroup.getGroupTree(), groupTree);
				toTop.add(groupTree);
			}
		}
		category.getGroupTree().removeAll(toTop);
		toTop.addAll(category.getGroupTree());
		category.setGroupTree(toTop);
	}

	// 对类目进行排序 通过类目的数量进行排序
	public static SearchDTO sortCategoryTreesByDesc(SearchDTO dto) {
		if (dto.getCategoryTree() == null)
			return dto;
		GroupTree tree = dto.getCategoryTree();
		dto.setCategoryTree(sortGroupTreeByDesc(tree));
		return dto;
	}

	// 获取商品数量
	public static long getGoodsCounter(SearchDTO searchDto) {
		if (searchDto == null)
			return 0L;
		return getCounter(searchDto);
	}

	// 获取最相关商品的类目
	public static String getCategoryGroubLabel(GroupTree groupTree) {
		if (groupTree == null || CollectionTools.isEmpty(groupTree.getGroupTree()))
			return null;
		GroupTree firstGroupTree = CollectionTools.first(groupTree.getGroupTree());
		return firstGroupTree.getGroup().getGroupName();
	}

	protected static long getCounter(SearchDTO dto) {
		if (dto.getResourcesList() == null)
			return 0;
		return (dto.getResourcesList().size() == 0) ? 0 : dto.getTotalCount();
	}

	protected static Map<String, String> toMap(JSONObject resource) {
		Map<String, String> map = new HashMap<String, String>();
		for (String key : resource.keySet()) {
			map.put(key, StringTools.toStrNotNull(resource.get(key)));
		}
		return map;
	}

	/**
	 * 属性设置
	 * 
	 * @param searchDto
	 * @param dto
	 * @param jsonObject
	 */
	protected static void setAttrs(SearchDTO searchDto, SF1SearchBean searchBean, JSONObject jsonObject) {
		List<GroupTree> attributeTrees = new ArrayList<GroupTree>();
		if (searchDto == null || jsonObject == null)
			return;
		JSONArray jsonArray = jsonObject.getJSONArray("attr");
		if (jsonArray == null)
			return;
		int length = jsonArray.size();
		if (length < 1)
			return;
		for (int index = 0; index < length; index++) {
			JSONObject attr = jsonArray.getJSONObject(index);
			String attrName = attr.getString("attr_name");
			// sf1这边反馈 型号不作为过滤条件
			if (StringUtils.isEmpty(attrName) || "型号".equals(attrName) || attrName.startsWith(","))
				continue;
			String category = searchBean.getCategory();
			if (StringUtils.isEmpty(category) || category.indexOf("图书音像") >= 0) {
				if ("Author".equals(attrName) || "Format".equals(attrName)) {
					continue;
				}
			}
			GroupTree attributeTree = getAttr(attr);
			attributeTree.getGroup().setGroupName(attrName);
			attributeTrees.add(attributeTree);
		}
		searchDto.setAttributeTrees(attributeTrees);
	}

	/**
	 * 置顶 分类设置
	 * 
	 * @param searchDto
	 * @param jsonObject
	 */
	protected static void setTopGroups(SearchDTO searchDto, JSONObject jsonObject) {
		List<GroupTree> topGroups = new ArrayList<GroupTree>();
		if (searchDto == null || jsonObject == null)
			return;
		JSONArray jsonArray = jsonObject.getJSONArray("top_group_label");
		if (jsonArray == null)
			return;
		int length = jsonArray.size();
		if (length < 1)
			return;
		for (int index = 0; index < length; index++) {
			JSONObject group = jsonArray.getJSONObject(index);
			if ("Category".equals(group.getString("group_property"))) {
				JSONArray labels = group.getJSONArray("group_label");
				if (labels == null)
					continue;
				int labelslength = labels.size();
				if (labelslength < 1)
					continue;
				for (int labelsindex = 0; labelsindex < labelslength; labelsindex++) {
					addTopGroups(topGroups, labels.getJSONArray(labelsindex));
				}
				break;
			}
		}
		searchDto.setTopGroups(topGroups);
	}

	/**
	 * 相关搜索设置
	 * 
	 * @param searchDto
	 * @return
	 */
	protected static void setRelatedQueries(SearchDTO searchDto, JSONObject jsonObject) {
		List<String> relatedQueries = new ArrayList<String>();
		if (jsonObject == null)
			return;
		JSONArray array = jsonObject.getJSONArray("related_queries");
		if (array == null)
			return;
		int length = array.size();
		if (length < 1)
			return;
		for (int index = 0; index < length; index++) {
			String relatedQuerie = array.getString(index);
			relatedQueries.add(relatedQuerie);
		}
		searchDto.setRelatedQueries(relatedQueries);
	}

	protected static GroupTree getAttr(JSONObject attr) {
		GroupTree groupTree = new GroupTree();
		JSONArray attrs = attr.getJSONArray("labels");
		if (attrs == null) {// 为空返回
			return groupTree;
		}
		groupTree.getGroup().setGroupCount(attr.getInteger("document_count"));
		int attrsLength = attrs.size();
		if (attrsLength < 1) {// 长度小于1 返回
			return groupTree;
		}
		for (int i = 0; i < attrsLength; i++) {
			GroupTree subTree = new GroupTree();
			JSONObject attrObj = attrs.getJSONObject(i);
			String label = attrObj.getString("label");// document_count
			if (label == null)
				continue;
			int count = attrObj.getInteger("document_count");
			subTree.setGroup(new Group(label, count, true, ""));
			groupTree.addSub(subTree);
		}
		return groupTree;
	}

	protected static void addTopGroups(List<GroupTree> groupTrees, JSONArray jsonArray) {
		int length = jsonArray.size();
		List<GroupTree> groups = groupTrees;
		for (int index = 0; index < length; index++) {
			String label = jsonArray.getString(index);
			GroupTree groupTree = groupTree(label);
			GroupTree contain = addTopGroup(groups, groupTree);
			groups = contain.getGroupTree();
		}
	}

	protected static GroupTree addTopGroup(List<GroupTree> groupTrees, GroupTree groupTree) {
		GroupTree contain = contain(groupTrees, groupTree);
		if (contain == null) {
			groupTrees.add(groupTree);
			return groupTree;
		}
		return contain;
	}

	protected static GroupTree groupTree(String name) {
		GroupTree groupTree = new GroupTree();
		groupTree.setGroup(new Group(name, 0, false, ""));
		return groupTree;
	}

	protected static GroupTree contain(List<GroupTree> groupTrees, GroupTree groupTree) {
		for (GroupTree tree : groupTrees) {
			if (tree == null)
				continue;
			String treeGroupName = tree.getGroup().getGroupName();
			if (StringUtils.isEmpty(treeGroupName)) {
				continue;
			}
			if (treeGroupName.equals(groupTree.getGroup().getGroupName())) {
				return tree;
			}
		}
		return null;
	}

	protected static void getSubGroupFromJson(GroupTree fatherGroup, JSONArray groupArray, String fatherName) {
		int size = groupArray.size();
		// 获得每个分组的名称和数量
		for (int j = 0; j < size; j++) {
			GroupTree groupTree = new GroupTree();
			JSONObject groupOfArray = groupArray.getJSONObject(j);
			String name = groupOfArray.getString("label");
			int count = groupOfArray.getInteger("document_count");
			Group group = new Group();
			group.setGroupName(name);
			group.setGroupCount(count);
			String wholeName = name;
			if (!StringUtils.isBlank(fatherName)) {
				wholeName = fatherName + ">" + name;
			}
			group.setWholeGroupName(wholeName);
			// 如果存在子分组，递归调用本函数
			JSONArray subJsonArray = groupOfArray.getJSONArray("sub_labels");
			group.setLeaf(true);
			if (subJsonArray != null && subJsonArray.size() >= 1) {
				group.setLeaf(false);
				getSubGroupFromJson(groupTree, groupOfArray.getJSONArray("sub_labels"), wholeName);
			}
			groupTree.setGroup(group);
			fatherGroup.addSub(groupTree);
		}
	}

	protected static void sortGroupTreesByDesc(List<GroupTree> trees) {
		Collections.sort(trees, new Comparator<GroupTree>() {

			@Override
			public int compare(GroupTree o1, GroupTree o2) {
				if (o1.getGroup().getGroupCount() > o2.getGroup().getGroupCount())
					return -1;
				if (o1.getGroup().getGroupCount() == o2.getGroup().getGroupCount())
					return 0;
				return 1;
			}

		});
	}

	protected static GroupTree sortGroupTreeByDesc(GroupTree tree) {
		if (tree == null)
			return tree;
		List<GroupTree> children = tree.getGroupTree();
		sortGroupTreesByDesc(children);

		for (GroupTree child : children) {
			sortGroupTreeByDesc(child);
		}
		return tree;
	}

}
