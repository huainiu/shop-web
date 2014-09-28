package com.b5m.common.utils.shoplist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.b5m.base.common.Lang;
import com.b5m.base.common.utils.StringTools;
import com.b5m.base.common.utils.UrlTools;
import com.b5m.bean.dto.FilterLinkDto;
import com.b5m.bean.dto.LinkDto;
import com.b5m.bean.dto.LinkTreeDto;
import com.b5m.bean.dto.NavigationLinkDto;
import com.b5m.bean.dto.shoplist.ShowType;
import com.b5m.bean.dto.shoplist.SortType;
import com.b5m.common.env.Constants;
import com.b5m.common.utils.DataUtils;
import com.b5m.dao.domain.page.PageView;
import com.b5m.sf1.dto.res.Group;
import com.b5m.sf1.dto.res.GroupTree;
import com.mchange.v1.util.StringTokenizerUtils;

/**
 * 创建LinkDto的集合对象
 * @author jacky
 *
 */
public class LinkDtoHelper {
	/**
	 * 这个是静态请求参数的名称，序号的规则是由{@link /WebRoot/WEB-INF/urlrewrite.xml}中定义的
	 */
	public static final String[] REQUEST_PARAMS = new String[]{
		"categoryValue", "sortField", "sortType", "showType",
		"sourceValue", "brandValue", "attrNames", "attrValue", 
		"log_group_label", "priceFrom", "priceTo", "splServiceValue",
		"searchType", "currPageNo", "filterField", "kaviFilter",
		"isFreeDelivery","isCOD","isGenuine","keyWord"
	};
	
	public static final String URLREWRITE_OUTBOUND_SHOPLIST = "search/s/$1_$2_$3_$4_$5_$6_$7_$8_$9_$10_$11_$12_$13_$14_$15_$16_$17_$18_$19_$20.html";
	
	public static final Map<String, Integer> PARAMS_REQUEST_INDEX = new HashMap<String, Integer>();
	
	static{
		int index = 1;
		for(String params : REQUEST_PARAMS){
			PARAMS_REQUEST_INDEX.put(params, index++);
		}
	}
	
	/**
	 * 价格区间的集合
	 */
	public static final String[] PRICE_RANGES = new String[]{"1-2000", "2000-5000", "5000"};
	
	/*private static String generateToString(String[] values){
		return StringUtils.arrayToDelimitedString(values, ComConstants.REQUEST_PARAM_DELIMER);
	}*/
	
	public static Map<String, String> initParameters(Map<String, String> parameters){
		parameters.put(REQUEST_PARAMS[8], "");
		parameters.put(REQUEST_PARAMS[13], "");
		return parameters;
	}
	
	private static Map<String, String> initParameters(Map<String, String> parameters, String p5){
		parameters.put(REQUEST_PARAMS[8], "");
		parameters.put(REQUEST_PARAMS[13], "");
		parameters.put(REQUEST_PARAMS[5], p5);
		return parameters;
	}
	
	/**
	 * 将请求中的请求参数clone到一个新的Map容器里。
	 * @param requestParams
	 * @return
	 */
	public static Map<String, String> cloneParameters(Map<String, String[]> requestParams){
		Map<String, String> newParams = new HashMap<String, String>();
		Set<String> keySet = requestParams.keySet();
		for(String key : keySet){
			if(requestParams.get(key).length > 0)
				newParams.put(key, requestParams.get(key)[0]);
		}
		return newParams;
	}
	
	public static String generateContextPathBase(HttpServletRequest request){
		StringBuilder sb = new StringBuilder(request.getContextPath());
		sb.append(request.getRequestURI());
		String path = sb.toString();
		if(path.indexOf("/") == 0){
			return path.substring(1);
		}
		return path;
	}
	
	private static Map<String, String> clearParametersValue(Map<String, String> parameters){
		Set<String> keys = parameters.keySet();
		for(String key : keys){
			parameters.put(key, "");
		}
		return parameters;
	}
	
	/**
	 * 生成静态路径
	 * @param parameters 请求的参数
	 * @param contextPathBase 这个请求地址以及路径，但不包含最后的静态文件的名称
	 * @param response 这个必须是UrlRewriter包装后的Response类型
	 * @return
	 */
	public static String generateUrl(Map<String, String> parameters, String contextPathBase, HttpServletRequest request, HttpServletResponse response){
		String category = parameters.get(REQUEST_PARAMS[0]);
		String keyword = parameters.get(REQUEST_PARAMS[19]);
		if(Lang.isEmpty(category)){
			category = Lang.toStrNotNull(request.getAttribute((REQUEST_PARAMS[0])));
			if(!StringTools.isEmpty(parameters.get("ALLCategory"))){
				category = "";
			}
		}
		if(Lang.isEmpty(keyword)){
			keyword = Lang.toStrNotNull(request.getAttribute((REQUEST_PARAMS[19])));
		}
		StringBuilder sb = new StringBuilder(100);
		if (null == request.getAttribute("path"))
			sb.append("/search/s/");
		else 
			sb.append(request.getAttribute("path").toString());
		int length = REQUEST_PARAMS.length;
		sb.append(category);
		sb.append("_");
		boolean isUrlOptimize = true;
		for(int i = 1; i < length - 1; i++){
			if(notUrlOptimize(i, parameters)){
				isUrlOptimize = false;
			}
			if(parameters.containsKey(REQUEST_PARAMS[i])){
				String param = parameters.get(REQUEST_PARAMS[i]);
				if(!StringTools.isEmpty(param) && param.contains("%"))
					param = UrlTools.urlEncode(param);
				if(!Lang.isEmpty(param) || "null".equals(param)){
					sb.append(param);
				}
			}
			sb.append("_");
		}
		sb.append(keyword);
		sb.append(".html");
		if (request.getParameter("isLowPrice") != null)
			sb.append("?isLowPrice=" + request.getParameter("isLowPrice"));
		String path = sb.toString();
		if(isUrlOptimize){
			if(!StringTools.isEmpty(category)){
				category = StringTools.replace(category, "/", "^");
			}
			if (request.getAttribute("path") != null) {
				return  request.getAttribute("path").toString() + (StringTools.isEmpty(category) ? "" :  category + "/") + keyword + ".html";
			}
			else {
				path = "/" + (StringTools.isEmpty(category) ? "" :  category + "/") + keyword + ".html";
				if (request.getParameter("isLowPrice") != null)
					path += "?isLowPrice=" + request.getParameter("isLowPrice");
				return path;
			}
		}
		//利用绝对路径
		return path;
	}
	
	protected static boolean notUrlOptimize(int index, Map<String, String> parameters){
		if(index != 3 && index != 6 && index != 7 && index != 8 && index != 11 && index != 14 && index != 15 && index != 19){
			if(!StringTools.isEmpty(parameters.get(REQUEST_PARAMS[index]))){
				return true;
			}
		}
		return false;
	}
	
	public static String getBasePath(HttpServletRequest request){
		String server = request.getServerName();
		//首页需要全局的资源引入, by holin
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme()).append("://").append(server).append(":").append(request.getServerPort()).append(request.getContextPath()).append("/");
		return sb.toString();
	}
	
	private static String mergeParameterValue(String originalValue, String appendValue){
		if(null == originalValue || "".equals(originalValue)){
			return appendValue;
		}
		return new StringBuilder(originalValue).append(Constants.REQUEST_PARAM_DELIMER).append(appendValue).toString();
	}
	
	/**
	 * 去掉已选中的链接，一般{@link #generateBrandLinks(HttpServletRequest, HttpServletResponse, List)}和 {@link #generateSourceLinks(HttpServletRequest, HttpServletResponse, List)}
	 * 会使用到这个函数
	 * @param links
	 * @param selected
	 * @return
	 */
	private static List<LinkDto> filterSelectedLinks(List<LinkDto> links, String[] selectedValues){
		if(selectedValues == null) return links;
		int length = links.size();
		for(String selected : selectedValues){
			if(StringUtils.isEmpty(selected)) continue;
			for(int i = 0; i < length; i++){
				if(!links.isEmpty()){
					LinkDto link = links.get(i);
					if(link.getText().equals(selected)){
						length--;
						links.remove(link);
						break;
					}
				}
			}
		}
		return links;
	}
	
	private static String[] getParameterValues(HttpServletRequest request, int paramIndex){
		String[] values = (String[])request.getParameterValues(REQUEST_PARAMS[paramIndex]);
		if(values == null || values.length == 0)
			return values;
		//如果 手机屏幕:3.5英寸这种形式的，则先将 冒号前面的去掉 (手机屏幕:)
		String[] parameterValues = StringTokenizerUtils.tokenizeToArray(values[0], Constants.REQUEST_PARAM_DELIMER);
		for(int index = 0; index < parameterValues.length; index++){
			String parameterValue = parameterValues[index];
			if(parameterValue.indexOf(":") > 0){
				String value = parameterValue.split(":")[1];
				parameterValues[index] = value.replace("@^^@", ":");
			}
		}
		return parameterValues;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkDto generateSourceLink(HttpServletRequest request, HttpServletResponse response, GroupTree tree, String basePath){
		LinkDto link = new LinkDto();
		link.setText(tree.getGroup().getGroupName());
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		parameters.put(REQUEST_PARAMS[4], mergeParameterValue(parameters.get(REQUEST_PARAMS[4]), DataUtils.specialCharEn(tree.getGroup().getGroupName())));
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	/**
	 * description
	 * 创建 免运费 正品行货 货到付款 链接
	 * @param request
	 * @param response
	 * @return
	 * @author echo weng
	 * @since 2013-6-8
	 * @mail echo.weng@b5m.com
	 */
	@SuppressWarnings("unchecked")
	public static LinkDto[] generateCFGLink(HttpServletRequest request, HttpServletResponse response, String basePath){
	    LinkDto[] links = new LinkDto[3];
        Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
        String $16 = parameters.get(REQUEST_PARAMS[16]);
        String $17 = parameters.get(REQUEST_PARAMS[17]);
        links[0] = generateCFGCommmonLink(request, response, parameters, REQUEST_PARAMS[16], basePath);
        parameters.put(REQUEST_PARAMS[16], $16);
        links[1] = generateCFGCommmonLink(request, response, parameters, REQUEST_PARAMS[17], basePath);
        parameters.put(REQUEST_PARAMS[17], $17);
        links[2] = generateCFGCommmonLink(request, response, parameters, REQUEST_PARAMS[18], basePath);
        return links;
	}
	
	public static LinkDto generateCFGCommmonLink(HttpServletRequest request, HttpServletResponse response, Map<String, String> parameters, String key, String basePath){
	    LinkDto link = new LinkDto();
	    String value = parameters.get(key);
        if("1".equals(value)){
            parameters.put(key, "");
        }else{
            parameters.put(key, "1");
        }
        link.setUrl(generateUrl(parameters, basePath, request, response));
        return link;
	}
	
	public static List<LinkDto> generateSourceLinks(HttpServletRequest request, HttpServletResponse response, List<GroupTree> groupTrees, String basePath){
		List<LinkDto> linksAll = new LinkedList<LinkDto>();
		List<LinkDto> links = new LinkedList<LinkDto>();
		for(GroupTree groupTree : groupTrees){
			links.add(generateSourceLink(request,response, groupTree, basePath));
		}
		if(!"".equals(request.getParameter(REQUEST_PARAMS[4]))){
			LinkDto[] subLinks = generateFilterLink(request, response, 4, basePath);
			for(LinkDto subLink : subLinks){
				subLink.setText(DataUtils.specialCharDe(subLink.getText()));
				subLink.setClicked(true);
				linksAll.add(subLink);
			}
		}
		String[] sources = getParameterValues(request, 4);
		if(sources != null){
			for(int i = 0; i < sources.length; i++){
				sources[i] = DataUtils.specialCharDe(sources[i]);
			}
		}
		linksAll.addAll(filterSelectedLinks(links, sources));
		//现在不过滤
		return linksAll;	
	}
	
	@SuppressWarnings("unchecked")
	/*public static List<LinkDto> generateBrandLinks(HttpServletRequest request, HttpServletResponse response, List<GroupTree> groupTrees, String basePath){
		List<LinkDto> links = new LinkedList<LinkDto>();
		Map<String, String> parameters = cloneParameters(request.getParameterMap());
		String p5 = parameters.get(REQUEST_PARAMS[5]);
		LinkDto link = null;
		for(GroupTree groupTree : groupTrees){
			String groupName = groupTree.getGroup().getGroupName();
			link = new LinkDto();
			link.setText(groupName);
			initParameters(parameters, p5);
			parameters.put(REQUEST_PARAMS[5], mergeParameterValue(p5,  groupName));
			link.setUrl(generateUrl(parameters, basePath, request, response));
			links.add(link);
		}
		return filterSelectedLinks(links, getParameterValues(request, 5));
	}*/
	
	public static List<LinkDto> generateAttrLinks(HttpServletRequest request, HttpServletResponse response, GroupTree attrGroupTree, String basePath){
		List<LinkDto> links = new LinkedList<LinkDto>();
		Map<String, String> parameters = cloneParameters(request.getParameterMap());
		String p5 = parameters.get(REQUEST_PARAMS[5]);
		LinkDto link = null;
		List<GroupTree> groups = attrGroupTree.getGroupTree();
		String attrGroupTreeName = attrGroupTree.getGroup().getGroupName();
		if(StringUtils.isEmpty(attrGroupTreeName)) return links;
		
		for(GroupTree groupTree : groups){
			String groupName = groupTree.getGroup().getGroupName();
			if(StringUtils.isEmpty(groupName)) continue;
			link = new LinkDto();
			initParameters(parameters, p5);
			link.setText(groupName);
			groupName = DataUtils.replace(groupName, ":", "@^^@");
			String param = attrGroupTreeName + ":" + groupName;
			param = DataUtils.specialCharEn(param);
			parameters.put(REQUEST_PARAMS[5], mergeParameterValue(p5,  param));
			link.setUrl(generateUrl(parameters, basePath, request, response));
			links.add(link);
		}
		return filterSelectedLinks(links, getParameterValues(request, 5));
    }
	
	@SuppressWarnings("unchecked")
	private static LinkDto generateCategoryLink(HttpServletRequest request, HttpServletResponse response, GroupTree groupTree, String basePath){
		LinkDto link = new LinkDto();
		/*link.setText(groupTree.getGroup().getGroupName() + "(" + groupTree.getGroup().getGroupCount() + ")");*/
		link.setText(groupTree.getGroup().getGroupName());
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		String category = null;
		if(!StringTools.isEmpty(parameters.get(REQUEST_PARAMS[0]))){
			category = parameters.get(REQUEST_PARAMS[0]);
		}
		if(!StringTools.isEmpty((String) (request.getAttribute("categoryValue")))){
			category = (String) (request.getAttribute("categoryValue"));
		}
		if(!StringTools.isEmpty(category) && category.indexOf(">") > 0){
			if(category.equals(groupTree.getGroup().getWholeGroupName()) || groupTree.getGroup().getWholeGroupName().equals(category.substring(0, category.lastIndexOf(">")))){
				link.setClicked(true);
			}
		}
		parameters.put("ALLCategory", "true");
		parameters.put(REQUEST_PARAMS[0], groupTree.getGroup().getWholeGroupName());
		parameters.put(REQUEST_PARAMS[8], "true");
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	private static LinkTreeDto _generateCategoryTreeLinks(HttpServletRequest request, HttpServletResponse response, GroupTree groupTree, int layer, String basePath){
		LinkTreeDto tree = new LinkTreeDto();
		tree.setLink(generateCategoryLink(request, response, groupTree, basePath));
		if(layer <= 0)
			return tree;
		for(GroupTree childtree : groupTree.getGroupTree()){
			tree.getLinkTree().add(_generateCategoryTreeLinks(request, response, childtree, layer - 1, basePath));
		}
		return tree;
	}
	
	private static GroupTree findGroupTreeByGroupName(String groupName, List<GroupTree> trees){
		for(GroupTree tree : trees){
			if(groupName.equals(tree.getGroup().getGroupName()))
				return tree;
		}
		return null;
	}
	
	/**
	 * 只复制当前groupTree这一层的数据
	 * @param groupTree
	 * @return
	 */
	private static GroupTree cloneCurrentLevelGroupTree(GroupTree groupTree){
		GroupTree tree = new GroupTree();
		Group group = new Group();
		tree.setGroup(group);
		if(groupTree == null)
			return tree;
		if(null == groupTree.getGroup())
			return tree;
		group.setGroupCount(groupTree.getGroup().getGroupCount());
		group.setGroupName(groupTree.getGroup().getGroupName());
		group.setLeaf(groupTree.getGroup().isLeaf());
		group.setWholeGroupName(groupTree.getGroup().getWholeGroupName());
		return tree;
	}
	
	
	private static GroupTree findThirdLayer(String groupWholeName, GroupTree root){
		String[] hierarchy = StringTools.split(groupWholeName, ">");
		String firstAndSecLayer = hierarchy[0] + ">" + hierarchy[1];
		GroupTree result = root.deepClone();
		for(GroupTree subTree : result.getGroupTree()){
			for(GroupTree ssubTree : subTree.getGroupTree()){
				if(ssubTree.getGroup().getWholeGroupName().indexOf(firstAndSecLayer) >= 0){
					continue;
				}
				ssubTree.getGroupTree().clear();
			}
		}
		return result;
	}
	
	
	/**
	 * 从根级别找到最底层的树，并返回根以下级别到最底层的整个链。
	 * @param groupWholeName 这个参数不能为空或者空字符串
	 * @param root
	 * @return
	 */
	private static GroupTree findBottomLayer(String groupWholeName, GroupTree root){
		String[] hierarchy = groupWholeName.split(">");
		GroupTree result = cloneCurrentLevelGroupTree(root);
		GroupTree sub = findGroupTreeByGroupName(hierarchy[0], root.getGroupTree());
		if(sub == null)
			return root;
		// 只遍历下一级
		GroupTree secondL = cloneCurrentLevelGroupTree(sub);
		result.getGroupTree().add(secondL);
		for(GroupTree groupTree : root.getGroupTree()){
            if(secondL.getGroup().getGroupName().equals(groupTree.getGroup().getGroupName())){
                continue;
            }
            //如果进行了分类筛选，则不输出去他一级分类
            //result.addSub(groupTree);
        }
		if(hierarchy.length == 1){
			for(GroupTree ssub : sub.getGroupTree()){
				secondL.getGroupTree().add(cloneCurrentLevelGroupTree(ssub));
			}
			return result;
		}		
		// 遍历下下一级
		GroupTree ssub = findGroupTreeByGroupName(hierarchy[1], sub.getGroupTree());
		GroupTree thirdL = cloneCurrentLevelGroupTree(ssub);
		secondL.getGroupTree().add(thirdL);
		for(GroupTree sssub : ssub.getGroupTree()){
			thirdL.getGroupTree().add(cloneCurrentLevelGroupTree(sssub));
		}
		return result;
	}
	
	/**
	 * 生成分类链接集合，根表示的是所有分类
	 * @param request
	 * @param response
	 * @param groupTrees
	 * @return
	 */
	public static LinkTreeDto generateCategoryTreeLinks(HttpServletRequest request, HttpServletResponse response, GroupTree groupTree, String basePath){
		groupTree.getGroup().setGroupName("ALL");
		groupTree.getGroup().setWholeGroupName("ALL");
		LinkTreeDto tree = new LinkTreeDto();
		tree.setLink(generateCategoryLink(request, response, groupTree, basePath));
		String category = request.getParameter(REQUEST_PARAMS[0]);
		if(StringTools.isEmpty(category))
			category = (String)request.getAttribute("categoryValue");
		int layer = 0;
		// 如果有层级关系
		// 只取根目录下的第一个
		if(!"".equals(category) && null != category){
			layer = StringTools.split(category, ">").length;
			request.setAttribute("categoryLayer", layer);
		}
		request.setAttribute("categoryLayer", layer);
		return _generateCategoryTreeLinks(request, response,  groupTree, 3, basePath);
	}
	
	public static void main(String[] args) {
		System.out.println(StringTools.split("ahs", "h").length);
	}
	
	@SuppressWarnings("unchecked")
	private static LinkDto generatePageLinkDto(HttpServletRequest request, HttpServletResponse response, String pageNo, String basePath){
		LinkDto link = new LinkDto();
		link.setText(pageNo);
		Map<String, String> parameters = cloneParameters(request.getParameterMap());
		parameters.put(REQUEST_PARAMS[13], link.getText());
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	@SuppressWarnings("rawtypes")
	public static PageView getPageView(int currentPage, int pageSize, long totalRecord, int pageCode){
		PageView pageView = new PageView(pageSize, currentPage);
		pageView.setTotalRecord(totalRecord);
		if(pageView.getTotalPage() > 1000){//页面最大显示1000页
			pageView.setTotalPage(1000);
		}
		pageView.setPageCode(pageCode);
		return pageView;
	}
	
	public static String generatePageCodeLink(HttpServletRequest request, HttpServletResponse response, String basePath){
		LinkDto link = generatePageLinkDto(request, response, "{pageCode}", basePath);
		return link.getUrl();
	}
	
	@SuppressWarnings("unchecked")
	public static LinkDto generatePriceRangeLink(HttpServletRequest request, HttpServletResponse response, String priceRange, String basePath){
		LinkDto link = new LinkDto();
		String[] prices = priceRange.split("-");
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		String priceFrom = prices[0];
		String priceTo = "";
		if(prices.length == 2){
			priceTo = prices[1];
		}
		parameters.put(REQUEST_PARAMS[9], priceFrom);
		parameters.put(REQUEST_PARAMS[10], priceTo);
		link.setUrl(generateUrl(parameters, basePath, request, response));
		link.setText(priceRange);
		return link;
	}
	
	/**
	 * 创建价格区间的链接DTO
	 * @param request
	 * @param response
	 * @return
	 */
	public static List<LinkDto> generatePriceRangeLinks(HttpServletRequest request, HttpServletResponse response, String basePath, String[] priceRanges){
		List<LinkDto> links = new ArrayList<LinkDto>();
		for(String priceRange : priceRanges){
			links.add(generatePriceRangeLink(request, response, priceRange, basePath));
		}
		return links;
	}
	
	private static String generateExcludeValueFromArray(List<String> list, String excluded){
		List<String> temp = new ArrayList<String>();
		for(String str : list){
		    if(!excluded.equals(str)){
		        temp.add(str);
		    }
		}
		String str = StringUtils.join(temp.iterator(), ",");
		return str;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkDto[] generateFilterLink(HttpServletRequest request, HttpServletResponse response, int paramIndex, String basePath){
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		String value = Lang.toStrNotNull(parameters.get(REQUEST_PARAMS[paramIndex]));
		String[] array = value.split(Constants.REQUEST_PARAM_DELIMER);
		List<String> tempList = new ArrayList<String>();
		for(int i = 0; i < array.length; i ++){
		    String str = array[i].trim();
		    if(!"".equals(str)){
		        tempList.add(str);
		    }
		}
		LinkDto[] links = new LinkDto[tempList.size()];
		int index = 0;
		for(String excluded : tempList){
			String excludedValue = generateExcludeValueFromArray(tempList, excluded);
			parameters.put(REQUEST_PARAMS[paramIndex], excludedValue);
			LinkDto link = new LinkDto();
			link.setText(excluded);
			link.setUrl(generateUrl(parameters, basePath, request, response));
			links[index++] = link;
		}
		return links;
	}
	
	@SuppressWarnings("unchecked")
	private static LinkDto generatePriceFilterLink(HttpServletRequest request, HttpServletResponse response, String basePath){
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		String priceFrom = parameters.get(REQUEST_PARAMS[9]);
		String priceTo = parameters.get(REQUEST_PARAMS[10]);
		String text = "";
		if(!Lang.isEmpty(priceFrom) && !Lang.isEmpty(priceTo)){
			text = new StringBuilder(priceFrom).append("-").append(priceTo).toString();
		}else if(!Lang.isEmpty(priceFrom)){
			text = new StringBuilder(priceFrom).append("以上").toString();
		}else{
			text = new StringBuilder(priceTo).append("以下").toString();
		}
		// 设置priceFrom为""
		parameters.put(REQUEST_PARAMS[9], "");
		// 设置priceTo为""
		parameters.put(REQUEST_PARAMS[10], "");
		LinkDto link = new LinkDto();
		link.setText(text);
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	/**
	 * 通过请求参数获取过滤条件
	 * @param parameterName
	 * @return
	 */
	private static String findFilterType(String parameterName){
		if(parameterName.equals(REQUEST_PARAMS[4])){
			return "商家";
		}
		if(parameterName.equals(REQUEST_PARAMS[5])){
			return "品牌";
		}
		if(parameterName.equals(REQUEST_PARAMS[9]) || parameterName.equals(REQUEST_PARAMS[10]))
			return "价格";
		return "";
	}
	
	/**
	 * 生成过滤区域的链接DTO
	 * @param request
	 * @param response
	 * @return
	 */
	public static List<FilterLinkDto> generateFilterLinks(HttpServletRequest request, HttpServletResponse response, String basePath){
		List<FilterLinkDto> links = new ArrayList<FilterLinkDto>();
		//商家不在过滤列表中了
		if(!Lang.isEmpty(request.getParameter(REQUEST_PARAMS[5]))){
			LinkDto[] subLinks = generateFilterLink(request, response, 5, basePath);
			for(LinkDto subLink : subLinks){
				FilterLinkDto filterLink = new FilterLinkDto();
				String text = subLink.getText();
				text = DataUtils.specialCharDe(text);
				//属性中出现@@的 则用_进行替换
				String[] attrFilterStrs = StringTokenizerUtils.tokenizeToArray(text, ":");
				subLink.setText(DataUtils.replace(attrFilterStrs[1], "@^^@", ":"));
				filterLink.setFilterType(attrFilterStrs[0]);
				filterLink.setLink(subLink);
				links.add(filterLink);
			}
		}
		if(!Lang.isEmpty(request.getParameter(REQUEST_PARAMS[9])) || !Lang.isEmpty(request.getParameter(REQUEST_PARAMS[10]))){
			FilterLinkDto filterLink = new FilterLinkDto();
			filterLink.setLink(generatePriceFilterLink(request, response, basePath));
			filterLink.setFilterType(findFilterType(REQUEST_PARAMS[9]));
			links.add(filterLink);
		}
		return links;
	}
	
	/**
	 * 获得排序的Text字符
	 * @param type
	 * @return
	 */
	private static String getSortTypeText(SortType type){
		if(type == SortType.CommentCount || type == SortType.SalesAmount)
			return "销量";
		if(type == SortType.Price)
			return "价格";
		if(type == SortType.Score)
			return "好评";
		if(type == SortType.Hot)
			return "热门";
		return "默认";
	}
	
	@SuppressWarnings("unchecked")
	private static LinkDto generateSortLink(HttpServletRequest request, HttpServletResponse response, SortType sortType, String basePath){
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		LinkDto link = new LinkDto();
		link.setText(getSortTypeText(sortType));
		parameters.put(REQUEST_PARAMS[1], sortType.getValue());
		String sort = parameters.get(REQUEST_PARAMS[2]);//.put(REQUEST_PARAMS[2], "");
		if(sortType == SortType.Default){
			parameters.put(REQUEST_PARAMS[2], "");
		}else{
			parameters.put(REQUEST_PARAMS[2], "DESC");
		}
		if(sortType == SortType.Price){
			sort= Lang.toStrNotNull(sort).equalsIgnoreCase("ASC")?"DESC":"ASC";
			parameters.put(REQUEST_PARAMS[2], sort);
		}
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	public static List<LinkDto> generateSortLinks(HttpServletRequest request, HttpServletResponse response, String basePath){
		List<LinkDto> links = new ArrayList<LinkDto>();
		links.add(generateSortLink(request, response, SortType.Default, basePath));
		links.add(generateSortLink(request, response, SortType.SalesAmount, basePath));
		//links.add(generateSortLink(request, response, SortType.Date, basePath));
//		links.add(generateSortLink(request, response, SortType.Score, basePath));
		links.add(generateSortLink(request, response, SortType.Price, basePath));
		return links;
	}
	
	public static List<LinkDto> generateSortLinks(HttpServletRequest request, HttpServletResponse response, String basePath, SortType[] types){
		List<LinkDto> links = new ArrayList<LinkDto>();
		if (types != null & types.length > 0) {
			for (int i = 0; i < types.length; i++) {
				SortType type = types[i];
				links.add(generateSortLink(request, response, type, basePath));
			}
		}
		return links;
	}
	
	private static LinkDto createNavigationSubLink(Map<String, String> parameters, String contextPathBase, HttpServletRequest request, HttpServletResponse response, String wholeCategoryName, String categoryName){
		LinkDto link = new LinkDto();
		parameters.put(REQUEST_PARAMS[0], wholeCategoryName);
		link.setText(categoryName);
		link.setUrl(generateUrl(parameters, contextPathBase, request, response));
		return link;
	}
	
	private static LinkDto createNavigationKeywordLink(Map<String, String> parameters,String contextPathBase, HttpServletRequest request, HttpServletResponse response){
		String keyword = parameters.get(REQUEST_PARAMS[16]);
		clearParametersValue(parameters);
		parameters.put(REQUEST_PARAMS[16], keyword);
		LinkDto link = new LinkDto();
		link.setText(keyword);
		link.setUrl(generateUrl(parameters, contextPathBase, request, response));
		return link;
	}
	
	/**
	 * 创建导航链接
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static NavigationLinkDto generateNavigationLink(HttpServletRequest request, HttpServletResponse response, String basePath){
		NavigationLinkDto link = new NavigationLinkDto();
		Map<String, String> parameters = cloneParameters(request.getParameterMap());
		parameters.put(REQUEST_PARAMS[8], "");
		String categoryWholeName = Lang.toStrNotNull(parameters.get(REQUEST_PARAMS[0]));
		String[] categories = categoryWholeName.split(">");
		link.setKeyword(createNavigationKeywordLink(parameters, basePath, request, response));
		if(categories.length == 0 || "".equals(categories[0]))
			return link;
		StringBuilder sb = new StringBuilder();
		parameters = cloneParameters(request.getParameterMap());
		for(int i = 0;i < categories.length;i++){
			if(i == 0){
				sb.append(categories[i]);
			}else{
				sb.append(">").append(categories[i]);
			}
			link.getNavigations().add(createNavigationSubLink(parameters, basePath, request, response, sb.toString(), categories[i]));
		}
		return link;
	}
	
	@SuppressWarnings("unchecked")
	private static LinkDto generateShowTypeLinkDto(HttpServletRequest request, HttpServletResponse response, ShowType type, String basePath){
		LinkDto link = new LinkDto();
		link.setText(type.getValue());
		Map<String, String> parameters = cloneParameters(request.getParameterMap());
		parameters.put(REQUEST_PARAMS[8], "");
		if(type == ShowType.List){
		    if(ShowType.List.getValue().equals(parameters.get(REQUEST_PARAMS[3]))){
		        link.setClicked(true);
		    }
			parameters.put(REQUEST_PARAMS[3], type.getValue());
		}else{
			if(Lang.isEmpty(parameters.get(REQUEST_PARAMS[3])) || ShowType.Image.getValue().equals(parameters.get(REQUEST_PARAMS[3]))){
				link.setClicked(true);
			}
			parameters.put(REQUEST_PARAMS[3], type.getValue());
		}
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	public static List<LinkDto> generateShowTypeLinkDtos(HttpServletRequest request, HttpServletResponse response, String basePath){
	    List<LinkDto> showTypes = new ArrayList<LinkDto>();
		showTypes.add(generateShowTypeLinkDto(request, response, ShowType.List, basePath));
		showTypes.add(generateShowTypeLinkDto(request, response, ShowType.Image, basePath));
		return showTypes;
	}
	
	/**
	 * 生成价格的格式，这个格式将由前段去处理自定义的价格查询请求
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String generatePriceFormat(HttpServletRequest request, HttpServletResponse response, String basePath){
		Map<String, String> parameters = cloneParameters(request.getParameterMap());
		initParameters(parameters);
		parameters.put(REQUEST_PARAMS[9], "@priceFrom@");
		parameters.put(REQUEST_PARAMS[10], "@priceTo@");
		return generateUrl(parameters, basePath, request, response);
	}
	
	public static LinkDto generateRelatedQueryLinkDto(HttpServletRequest request, String relatedQuery, HttpServletResponse response, String basePath){
	    LinkDto link = new LinkDto();
	    if(relatedQuery.indexOf("/") > 0){
	    	relatedQuery = relatedQuery.substring(0, relatedQuery.indexOf("/"));
	    }
	    link.setText(relatedQuery);
	    /*Map<String, String> parameters = new HashMap<String, String>();
	    parameters.put(REQUEST_PARAMS[3], request.getParameter(REQUEST_PARAMS[3]));
	    parameters.put(REQUEST_PARAMS[19], relatedQuery);
		link.setUrl(generateUrl(parameters, basePath, request, response));*/
		return link;
	}
	
	public static String generateOrgUrl(HttpServletRequest request, HttpServletResponse response, String basePath){
		 Map<String, String> parameterMap = LinkDtoHelper.cloneParameters(request.getParameterMap());
		 return LinkDtoHelper.generateUrl(parameterMap, basePath, request, response);
	}
}
