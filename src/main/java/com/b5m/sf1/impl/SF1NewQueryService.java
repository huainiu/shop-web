package com.b5m.sf1.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.base.common.utils.ThreadTools;
import com.b5m.bean.dto.AutoFillInfo;
import com.b5m.bean.dto.shoplist.SuiSearchDto;
import com.b5m.client.autofill.SF1AutoFillClient;
import com.b5m.common.spring.aop.Cache;
import com.b5m.goods.promotions.service.ISuppliserService;
import com.b5m.sf1.Sf1Query;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.dto.res.SearchDTO;
import com.b5m.sf1.helper.SearchResultHelper;
import com.b5m.sf1.helper.Sf1Helper;

/**
 * @Company B5M.com
 * @description sf1 查询
 * @author echo
 * @since 2013-8-22
 * @email wuming@b5m.com
 */
public class SF1NewQueryService {
	public static final String COUNTRY_KAVI = "韩国";
	private ISuppliserService service;
	private ExecutorService threadPool;
	private SF1AutoFillClient sf1AutoFillClient;
	private static final Log LOG = LogFactory.getLog(SF1NewQueryService.class);
	
	@Autowired
	private Sf1Query sf1Query;
	
	public SF1NewQueryService() {}

	public SF1NewQueryService(ISuppliserService service, ExecutorService threadPool, SF1AutoFillClient sf1AutoFillClient) {
		this.service = service;
		this.threadPool = threadPool;
		this.sf1AutoFillClient = sf1AutoFillClient;
	}
	
	@Cache
	public SearchDTO search(SuiSearchDto dto) {
		if(StringUtils.isEmpty(dto.getCollectionName())){
			dto.setCollectionName(Sf1Helper.CONTEXT_COLLECTION);
		}
		dto.setRequireRelated(true);
		SF1SearchBean searchBean = SearchResultHelper.convertTo4Search(dto);
		SearchDTO searchDTO = sf1Query.doSearch(searchBean);
		return searchDTO;
	}
	
	public SearchDTO search(SuiSearchDto dto, String collectionName) {
		dto.setCollectionName(collectionName);
		dto.setRequireRelated(true);
		SF1SearchBean searchBean = SearchResultHelper.convertTo4Search(dto);
//		searchBean.getGroupBeans().clear();
		searchBean.getSearchIn().clear();
//		searchBean.setGetAttr(null);
		SearchDTO searchDTO = sf1Query.doSearch(searchBean);
		return searchDTO;
	}
	
	@Cache
	public SearchDTO[] multiSearch(SuiSearchDto dto) {
		// SearchDTO searchDTO = query(dto, "b5mo", 0, 0);
		// sf1 返回的 分词关键词
		String newKeyword = dto.getKeyWord();// searchDTO.getAnalyzerResult();
		if (StringUtils.isEmpty(newKeyword)) {
			return new SearchDTO[0];
		}
		if (newKeyword.indexOf(" ") < 0 && newKeyword.equals(dto.getKeyWord()))
			return new SearchDTO[0];
		String[] keywords = newKeyword.split(" ");
		SearchDTO[] searchDTOs = reQuery(keywords);
		List<SearchDTO> searchDTOList = CollectionTools.newListWithSize(3);
		for (SearchDTO searchDtoWrap : searchDTOs) {
			if (searchDtoWrap == null || searchDtoWrap.getDocResourceDtos().isEmpty())
				continue;
			searchDTOList.add(searchDtoWrap);
		}
		return searchDTOList.toArray(new SearchDTO[] {});
	}


	@Cache
	public String queryCorrect(String keywords) {
		List<String> refinedQuery = sf1Query.getRefined_query(Sf1Helper.CONTEXT_COLLECTION, keywords);
		if (refinedQuery.isEmpty())
			return keywords;
		String refine = refinedQuery.get(0);
		if (StringUtils.isEmpty(refine))
			return keywords;
		return refine;
	}

	protected SearchDTO[] reQuery(String[] keywords) {
		List<String> keywordList = needSearchKeywords(keywords);
		List<Callable<SearchDTO>> queryTasks = new ArrayList<Callable<SearchDTO>>();
		for (final String keyword : keywordList) {
			queryTasks.add(new Callable<SearchDTO>() {

				@Override
				public SearchDTO call() throws Exception {
					SuiSearchDto dto = new SuiSearchDto();
					dto.setKeyWord(keyword);
					dto.setPageSize(5);
					dto.setCurrPageNo(1);
					SearchDTO searchDto = search(dto);
					searchDto.setKeywords(keyword);
					return searchDto;
				}

			});
		}
		return ThreadTools.executor(queryTasks, SearchDTO.class, threadPool);
	}

	public boolean needReSearch(SuiSearchDto dto) {
		return StringUtils.isEmpty(dto.getCategoryValue())
				&& StringUtils.isEmpty(dto.getBrandValue())
				&& StringUtils.isEmpty(dto.getIsCOD())
				&& StringUtils.isEmpty(dto.getIsFreeDelivery())
				&& StringUtils.isEmpty(dto.getIsGenuine())
				&& (dto.getCurrPageNo() == null || (dto.getCurrPageNo() != null && dto
						.getCurrPageNo() == 1))
				&& StringUtils.isEmpty(dto.getPriceFrom())
				&& StringUtils.isEmpty(dto.getPriceTo())
				&& StringUtils.isEmpty(dto.getSourceValue());
	}

	/**
	 * @description 最多用三个 keywords 进行查询
	 * @param keywords
	 * @return
	 * @author echo
	 * @since 2013-9-6
	 * @email echo.weng@b5m.com
	 */
	protected List<String> needSearchKeywords(String[] keywords) {
		List<String> keywordList = new ArrayList<String>(3);
		if (keywords.length < 3) {
			for (String word : keywords) {
				keywordList.add(word);
			}
		} else {
			for (int i = 0; i <= 2; i++) {
				keywordList.add(keywords[i]);
			}
		}
		return keywordList;
	}

	@Cache
	public Map<String, List<AutoFillInfo>> allAutoFillSearch(String prefix,
			int limit) throws Exception {
		Map<String, List<AutoFillInfo>> map = sf1AutoFillClient.allAutoFillSearch(prefix, limit);
		return map;
	}

	public Map<String, List<AutoFillInfo>> allAutoFillSearch(
			final String prefix, final int limit, final String city) {
		final Map<String, List<AutoFillInfo>> listMap = new HashMap<String, List<AutoFillInfo>>();
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		tasks.add(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				String k = prefix;
				if (!StringUtils.isEmpty(city)) {
					k = prefix + "|" + city;
				}
				Map<String, List<AutoFillInfo>> map = allAutoFillSearch(k, limit);
				listMap.putAll(map);
				return null;
			}

		});
		/*tasks.add(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				long start = System.currentTimeMillis();
				try {
					List<AutoFillInfo> list = SF1NewQueryService.this.autoFillClient.autoFill(prefix);
					listMap.put("tejia", CollectionTools.subList(list, 0, 10));
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("auto full query time from tejia:[" + (System.currentTimeMillis() - start) + "], for keyword:[" + prefix + "]");
				return null;
			}

		});*/
		ThreadTools.executor(tasks, Void.class, threadPool, 300l);
		return listMap;
	}
	
	public static void main(String[] args) {
//		457e9cc915f99abf0d2294033ec5f953
		System.out.println("\u7b7e\u540d\u9519\u8bef");
		System.out.println(StringTools.MD5("['3f1f7aaa17f1f0cc0f9ec362eb425fd3']123456"));
	}

	public ISuppliserService getSuppliserService() {
		return this.service;
	}

	public void destory() {
		threadPool.shutdown();
	}
}
