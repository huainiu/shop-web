package com.b5m.goods.hotkeys.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.b5m.goods.hotkeys.dto.HpSugGroupDto;
import com.b5m.goods.hotkeys.service.IHpSugGroupService;
import com.b5m.goods.hotkeys.service.mapper.HpSugGroupMapper;

@Service("hpSugGroupService")
public class HpSugGroupServiceImpl implements IHpSugGroupService {
	@Resource(name="jdbcTemplate")
	private JdbcTemplate template;
	
	public static final String GROUPNAME_HOT_KEY_HOME = "hot_keyword_home";
	
	public static final String GROUPNAME_RESULT_GOODS_LEFT = "result-goods-ad-left";
	
	public static final String GROUPNAME_RESULT_GOODS = "result-goods";
	
	private final String SQL_findAll = "select a.id, a.title, a.parentId, a.groupName, a.description, a.keywords, a.content, a.orderSeq, a.url from t_cms_hp_sug_group as a " 
			+ "where a.parentId is null order by a.orderSeq";
	
	private final String SQL_findByGroupName = "select a.id, a.title, a.parentId, a.groupName, a.description, a.keywords, a.content, a.orderSeq, a.url from t_cms_hp_sug_group as a " 
			+ "where groupName = ?";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private Map<String, HpSugGroupDto> convertToMap(List<HpSugGroupDto> groups){
		Map<String, HpSugGroupDto> map = new HashMap<String, HpSugGroupDto>();
		
		for(HpSugGroupDto group : groups){
			map.put(group.getGroupName(), group);
		}
		return map;
	}
	
	/*private Document readToDocument(String content) throws DocumentException{
		SAXReader reader = new SAXReader();
		return reader.read(new StringReader(content));
	}*/
	
	private List<String> parsingKeywordsContent(String content) throws DocumentException{
		/*Document doc = readToDocument(content);
		
		Element ul = doc.getRootElement().element("ul");
		List<Element> lis = (List<Element>)ul.elements("li");
		List<String> keywords = new ArrayList<String>();
		for(Element li : lis){
			Element a = li.element("a");
			if(null == a)
				continue;
			keywords.add(a.getText());
		}

		return keywords;*/
		String[] keywordPairs = content.split(";");
		List<String> keywords = new ArrayList<String>();
		for(String keywordPair : keywordPairs){
			keywords.add(keywordPair.split(",")[0]);
		}
		return keywords;
	}
	
	@Override
	public Map<String, HpSugGroupDto> findAll() {
		return convertToMap(template.query(SQL_findAll, new HpSugGroupMapper()));
	}

	@Override
	public List<String> getHotKeywords() {
		HpSugGroupDto dto = findByGroupName(GROUPNAME_HOT_KEY_HOME);
		if(null == dto)
			return new ArrayList<String>();
		if(null == dto.getContent() || "".equals(dto.getContent())){
			return new ArrayList<String>();
		}		
		try {
			return parsingKeywordsContent(dto.getContent());
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			return new ArrayList<String>();
		}
	}

	@Override
	public HpSugGroupDto findByGroupName(String groupName) {
		final String _groupName = groupName;
		return template.query(new PreparedStatementCreator(){

			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstat = conn.prepareStatement(SQL_findByGroupName);
				pstat.setString(1, _groupName);
				return pstat;
			}
			
		}, new ResultSetExtractor<HpSugGroupDto>(){
			final HpSugGroupMapper mapper = new HpSugGroupMapper();
			@Override
			public HpSugGroupDto extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if(!rs.next())
					return null;
				return mapper.mapRow(rs, 0);
			}
			
		});
	}

}
