package com.b5m.goods.promotions.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import com.b5m.goods.promotions.dto.PromInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.IPromotionService;
import com.b5m.goods.promotions.service.mapper.PromInfoMapper;

@Service
public class PromotionServiceImpl implements IPromotionService{

	@Resource(name="jdbcTemplate")
	private JdbcTemplate template;
	
	private final String SQL_findBySuppliserId = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
				.append(",").append(DtoSqlColumns.COL_PROMP_INFO)
				.append(" from t_cms_suppliser as s left join t_promo_info as p on s.id = p.suppliser")
				.append(" where s.id=?  and p.startTime<=? and p.endTime>=? order by p.updateTime desc").toString();
	
	private final String SQL_findBySuppliserName = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
			.append(",").append(DtoSqlColumns.COL_PROMP_INFO)
			.append(" from t_cms_suppliser as s left join t_promo_info as p on s.id = p.suppliser")
			.append(" where s.name=?  and p.startTime<=? and p.endTime>=? order by p.updateTime desc").toString();

	private final String SQL_findAllValidProm = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
			.append(",").append(DtoSqlColumns.COL_PROMP_INFO)
			.append(" from t_cms_suppliser as s left join t_promo_info as p on s.id = p.suppliser")
			.append(" where p.startTime<=? and p.endTime>=? order by s.name").toString();
	
	@Override
	public List<PromInfoDto> findBySuppliser(SuppliserDto suppliser) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		final String nowDateStr = format.format(new Date());
		if(suppliser.getId() != null){
			final int suppliserId = suppliser.getId();
			return template.query(new PreparedStatementCreator(){

				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement pstat = conn.prepareStatement(SQL_findBySuppliserId);
					pstat.setInt(1, suppliserId);
					pstat.setString(2, nowDateStr);
					pstat.setString(3, nowDateStr);
					return pstat;
				}
				
			}, new PromInfoMapper());
		}else if(suppliser.getName() != null){
			final String name = suppliser.getName();
			return template.query(new PreparedStatementCreator(){

				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement pstat = conn.prepareStatement(SQL_findBySuppliserName);
					pstat.setString(1, name);
					pstat.setString(2, nowDateStr);
					pstat.setString(3, nowDateStr);
					return pstat;
				}
				
			}, new PromInfoMapper());
		}
		return new ArrayList<PromInfoDto>();
	}

	private Map<String, List<PromInfoDto>> convertToMap(List<PromInfoDto> promInfos){
		Map<String, List<PromInfoDto>> mapPromp = new HashMap<String, List<PromInfoDto>>(promInfos.size());
		List<PromInfoDto> suppliserProms = null;
		String lastSuppliser = "";
		for(PromInfoDto promInfo : promInfos){
			if(!lastSuppliser.equals(promInfo.getSuppliser().getName())){
				if(null != suppliserProms){
					mapPromp.put(lastSuppliser, suppliserProms);
				}
				lastSuppliser = promInfo.getSuppliser().getName();
				suppliserProms = new ArrayList<PromInfoDto>();
			}
			suppliserProms.add(promInfo);
		}
		if(null != suppliserProms){
			mapPromp.put(suppliserProms.get(0).getSuppliser().getName(), suppliserProms);
		}
		return mapPromp;
	}

	@Override
	public Map<String, List<PromInfoDto>> findAllValidPromInfo() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		final String nowDateStr = format.format(new Date());
		//final String nowDateStr = "2012-11-10 12:00:00.0";
		return convertToMap(
				template.query(new PreparedStatementCreator(){

					@Override
					public PreparedStatement createPreparedStatement(Connection conn)
							throws SQLException {
						PreparedStatement pstat = conn.prepareStatement(SQL_findAllValidProm);
						pstat.setString(1, nowDateStr);
						pstat.setString(2, nowDateStr);
						return pstat;
					}
					
				}, new PromInfoMapper()
				));
	}
}
